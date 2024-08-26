package com.bednarz.usmobile.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing
public class MongoDbConfig {
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory);
    }

}
