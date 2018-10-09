package cz.muni.fi.pa165.currency;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace("Convert amount "+ sourceAmount + " from currency " + sourceCurrency + " to " + targetCurrency);
        if (sourceCurrency == null || targetCurrency == null || sourceAmount == null) {
            throw new IllegalArgumentException("Any of argument cannot be null.");
        }
        try {
            BigDecimal amount = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (amount == null) {
                logger.warn("There is no exchange rate.");
                throw new UnknownExchangeRateException("Exchange rate for given pair is not known.");
            }
            return sourceAmount.multiply(amount);
        } catch (ExternalServiceFailureException ex) {
            logger.error("Some internal error happen.");
            throw new UnknownExchangeRateException(ex.getMessage());
        }
    }

}
