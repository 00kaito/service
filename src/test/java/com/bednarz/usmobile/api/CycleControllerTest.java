package com.bednarz.usmobile.api;

import com.bednarz.usmobile.BaseTest;
import com.bednarz.usmobile.MongodbContainer;
import com.bednarz.usmobile.domain.cycle.CycleRepository;
import com.bednarz.usmobile.domain.dto.CreateUserRequest;
import com.bednarz.usmobile.domain.dto.CycleDataResponse;
import com.bednarz.usmobile.domain.dto.UpdateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class CycleControllerTest extends MongodbContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CycleRepository cycleRepository;

    @BeforeEach
    public void cleanUp(){
//        cycleRepository.deleteAll();
    }

    @Test
    public void createCycleTest() throws Exception {
        CycleDataResponse request = new CycleDataResponse();
        request.setMdn("9999999999");
        request.setStartDate(new Date());
        request.setEndDate(new Date());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.mdn").value("0000000000"));
    }

    @Test
    public void createCycleTest_ReturnsValidationErrors() throws Exception {
        CycleDataResponse request = new CycleDataResponse();
        request.setMdn("");  // Invalid MDN
        request.setStartDate(new Date());
        request.setEndDate(new Date());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("MDN must not be blank")));
    }

    @Test
    public void getCycleHistoryTest() throws Exception {
        String mdn = "9999999999";

        MongoTemplate template = MongoTemplateZ

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/history")
                        .param("mdn", mdn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());


    }

    @Test
    public void getCycleHistoryTest_ReturnsValidationErrors() throws Exception {
        String mdn = "";  // Invalid MDN

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/history")
                        .param("mdn", mdn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("MDN must not be blank")));
    }

    @Test
    public void getCurrentCycleUsageTest() throws Exception {
        String mdn = "1234567890";
        String userId = "user123";

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/usage/current")
                        .param("mdn", mdn)
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void getCurrentCycleUsageTest_ReturnsValidationErrors() throws Exception {
        String mdn = "";  // Invalid MDN
        String userId = "user123";

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/usage/current")
                        .param("mdn", mdn)
                        .param("userId", userId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("MDN must not be blank")));
    }
}
