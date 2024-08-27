package com.bednarz.usmobile.infrastructure.util;

import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LoadDataUtil {

    public static List<User> loadUsers(ObjectMapper objectMapper, String filePath) throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        return objectMapper.readValue(inputStream, new TypeReference<List<User>>() {
        });
    }

    public static List<Cycle> loadCycles(ObjectMapper objectMapper, String filePath) throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        return objectMapper.readValue(inputStream, new TypeReference<List<Cycle>>() {
        });
    }

    public static List<DailyUsage> loadDailyUsage(ObjectMapper objectMapper, String filePath) throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        return objectMapper.readValue(inputStream, new TypeReference<List<DailyUsage>>() {
        });
    }

    public static <T> void insertData(List<T> data, MongoTemplate mongoTemplate) {
        mongoTemplate.insertAll(data);
    }
}
