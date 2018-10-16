package cz.muni.fi.pa165.currency;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "cz.muni.fi.pa165.currency")
public class ConfigClass {
}

