package com.bednarz.usmobile.api;

import com.bednarz.usmobile.MongoBaseTest;
import com.bednarz.usmobile.application.dto.DailyUsageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class DailyUsageControllerTest extends MongoBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Create Daily Usage: Valid input, expecting successful creation with correct data")
    public void testCreateDailyUsage() throws Exception {
        // Given
        String mdn = "9291231234";
        String userId = "userId-99";
        Double usedDataValue = 300.0;
        DailyUsageRequest dailyUsageRequest = DailyUsageRequest.builder().
                mdn(mdn)
                .userId(userId)
                .usageDate(LocalDate.now())
                .usedInMb(usedDataValue)
                .build();

        mockMvc.perform(post("/api/v1/daily-usage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dailyUsageRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.mdn").value(mdn))
                .andExpect(jsonPath("$.data.usedInMb").value(usedDataValue));
    }

}
