package com.bednarz.usmobile.api;

import com.bednarz.usmobile.MongoBaseTest;
import com.bednarz.usmobile.application.dto.DailyUsageRequest;
import com.bednarz.usmobile.application.dto.DailyUsageResponse;
import com.bednarz.usmobile.application.service.UsageApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DailyUsageControllerTest extends MongoBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsageApplicationService dailyUsageApplicationService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String mdn;
    private String userId;
    private Date startDate;
    private Date endDate;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        mdn = "1234567890";
        userId = "user123";
        startDate = dateFormat.parse("2023-08-01");
        endDate = dateFormat.parse("2023-08-31");
    }

    @Test
    public void testGetCurrentCycleUsage() throws Exception {
        // Given
        DailyUsageResponse usageResponse = DailyUsageResponse.builder().
                mdn(mdn)
                .userId(userId)
                .usageDate(startDate)
                .usedInMb(500.0)
                .build();
        List<DailyUsageResponse> usageResponses = Arrays.asList(usageResponse);

        Mockito.when(dailyUsageApplicationService.getCurrentCycleUsage(mdn, userId, startDate, endDate))
                .thenReturn(usageResponses);

        // When & Then
        mockMvc.perform(get("/api/v1/daily-usage/current")
                        .param("mdn", mdn)
                        .param("userId", userId)
                        .param("startDate", dateFormat.format(startDate))
                        .param("endDate", dateFormat.format(endDate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].mdn").value(mdn))
                .andExpect(jsonPath("$.data[0].userId").value(userId))
                .andExpect(jsonPath("$.data[0].usedInMb").value(500.0));
    }

    @Test
    public void testCreateDailyUsage() throws Exception {
        // Given
        DailyUsageRequest dailyUsageRequest = DailyUsageRequest.builder().
                mdn(mdn)
                .userId(userId)
                .usageDate(new Date())
                .usedInMb(300.0)
                .build();
        DailyUsageResponse createdUsageResponse = DailyUsageResponse.builder().
                mdn(mdn)
                .userId(userId)
                .usageDate(new Date())
                .usedInMb(300.0)
                .build();

        Mockito.when(dailyUsageApplicationService.createDailyUsage(dailyUsageRequest))
                .thenReturn(createdUsageResponse);

        mockMvc.perform(post("/api/v1/daily-usage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dailyUsageRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.mdn").value(mdn))
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.usedInMb").value(300.0));
    }
}
