package com.vitja.study.degree.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJpaConfig {
    @Autowired
    private Environment env;

    public PersistenceJpaConfig() {
        super();
    }


}
