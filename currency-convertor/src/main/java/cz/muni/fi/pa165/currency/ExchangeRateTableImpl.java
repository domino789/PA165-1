package cz.muni.fi.pa165.currency;

import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;
@Component
public class ExchangeRateTableImpl implements ExchangeRateTable {
    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency == Currency.getInstance("EUR") && targetCurrency == Currency.getInstance("CZK")) {
            return BigDecimal.valueOf(27);
        } else {
            return null;
        }
    }
}
