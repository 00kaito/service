package com.bednarz.usmobile.infrastructure.util;

import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadDataUtil {

    private final ObjectMapper objectMapper;

    public List<User> loadUsers(String filePath) throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        return objectMapper.readValue(inputStream, new TypeReference<List<User>>() {
        });
    }

    public List<Cycle> loadCycles(String filePath) throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        return objectMapper.readValue(inputStream, new TypeReference<List<Cycle>>() {
        });
    }

    public List<DailyUsage> loadDailyUsage(String filePath) throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        return objectMapper.readValue(inputStream, new TypeReference<List<DailyUsage>>() {
        });
    }

    public static <T> void insertData(List<T> data, MongoTemplate mongoTemplate) {
        mongoTemplate.insertAll(data);
    }
}
