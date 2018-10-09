package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyConvertorImplTest {

    private ExchangeRateTable rateTable;
    private CurrencyConvertor currencyConvertor;
    private Currency czk, gb;


    @Before
    public void init() {
        rateTable = mock(ExchangeRateTable.class);
        currencyConvertor = new CurrencyConvertorImpl(rateTable);
        czk = Currency.getInstance("CZK");
        gb = Currency.getInstance("GBP");
    }

    @Test
    public void testConvert() throws Exception{
        when(rateTable.getExchangeRate(any(Currency.class), any(Currency.class))).thenReturn(new BigDecimal("0.56"));
        BigDecimal converted = currencyConvertor.convert(czk, gb, new BigDecimal("1000"));
        assertEquals(new BigDecimal("560.00"), converted);
        // Don't forget to test border values and proper rounding.
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        assertThatThrownBy(() -> currencyConvertor.convert(null, gb, new BigDecimal("1000")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Any of argument cannot be null.");
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        assertThatThrownBy(() -> currencyConvertor.convert(czk,null, new BigDecimal("1000")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Any of argument cannot be null.");
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        assertThatThrownBy(() -> currencyConvertor.convert(czk,gb,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Any of argument cannot be null.");
    }

    @Test
    public void testConvertWithUnknownCurrency() throws  Exception {
        when(rateTable.getExchangeRate(czk, gb)).thenReturn(null);
        assertThatThrownBy(() -> currencyConvertor.convert(czk,gb, new BigDecimal("1000")))
                .isInstanceOf(UnknownExchangeRateException.class)
                .hasMessage("Exchange rate for given pair is not known.");
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws Exception {
        doThrow(new ExternalServiceFailureException("Something happend")).when(rateTable).getExchangeRate(czk, gb);
        assertThatThrownBy(() -> currencyConvertor.convert(czk,gb, new BigDecimal("1000")))
                .isInstanceOf(UnknownExchangeRateException.class)
                .hasMessage("Something happend");
    }

}
