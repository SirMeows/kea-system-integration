package com.meows.sir.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;


import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new InstantToDateConverter(),
                new DateToInstantConverter()
        ));
    }

    static class InstantToDateConverter implements Converter<Instant, Date> {
        @Override
        public Date convert(Instant instant) {
            return Date.from(instant);
        }
    }

    static class DateToInstantConverter implements Converter<Date, Instant> {
        @Override
        public Instant convert(Date date) {
            return date.toInstant();
        }
    }
}