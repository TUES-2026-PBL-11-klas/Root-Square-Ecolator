package com.rootsquare.ecolator;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.rootsquare.ecolator.config.SeederProperties;

@SpringBootApplication
@EnableConfigurationProperties(SeederProperties.class)
public class EcolatorApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(EcolatorApplication.class, args);
    }
}