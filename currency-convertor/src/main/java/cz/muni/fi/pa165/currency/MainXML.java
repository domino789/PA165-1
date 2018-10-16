package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXML {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigClass.class);

        CurrencyConvertor currencyConvertor = context.getBean(CurrencyConvertor.class);
        System.out.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.valueOf(1)));
    }
}
