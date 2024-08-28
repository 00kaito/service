package com.bednarz.usmobile.api;

import com.bednarz.usmobile.MongoBaseTest;
import com.bednarz.usmobile.application.dto.CreateCycleRequest;
import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.bednarz.usmobile.infrastructure.persistence.CycleMongoRepository;
import com.bednarz.usmobile.infrastructure.util.LoadDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static com.bednarz.usmobile.infrastructure.util.LoadDataUtil.insertData;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class CycleControllerTest extends MongoBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CycleMongoRepository cycleRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    LoadDataUtil loadDataUtil;

    @BeforeEach
    void setUp() throws Exception {
        cleanDb();
        List<User> users = loadDataUtil.loadUsers("test-data/cycle-controller-test/users.json");
        List<Cycle> cycles = loadDataUtil.loadCycles("test-data/cycle-controller-test/cycles.json");
        List<DailyUsage> dataUsage = loadDataUtil.loadDailyUsage("test-data/cycle-controller-test/dailyUsage.json");

        insertData(users, mongoTemplate);
        insertData(cycles, mongoTemplate);
        insertData(dataUsage, mongoTemplate);
    }

    void cleanDb() {
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.dropCollection(DailyUsage.class);
        mongoTemplate.dropCollection(Cycle.class);
    }

    @Test
    @DisplayName("Create Cycle: Valid input, expecting successful creation")
    public void createCycleTest() throws Exception {
        CreateCycleRequest request = new CreateCycleRequest();
        request.setMdn("1234567890");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now());
        request.setUserId("some_user_id");

        ResultActions resultActions = mockMvc.perform(post("/api/v1/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.mdn").value("1234567890"));
    }

    @Test
    @DisplayName("Create Cycle: Invalid input, expecting validation errors")
    public void createCycleTest_ReturnsValidationErrors() throws Exception {
        CreateCycleRequest request = new CreateCycleRequest();
        request.setMdn("invalidMdn");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("MDN must be 10 digits long")));
    }

    @Test
    @DisplayName("Get Cycle History: Valid MDN, expecting 1 history record")
    public void getCycleHistoryTest() throws Exception {
        String mdn = "2345678901";
        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/history")
                        .param("mdn", mdn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));


    }

    @Test
    @DisplayName("Get Cycle History: Invalid MDN, expecting validation errors")
    public void getCycleHistoryTest_ReturnsValidationErrors() throws Exception {
        String invalidMdn = "";

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/history")
                        .param("mdn", invalidMdn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$['getCycleHistory.mdn']", containsString("MDN must be 10 digits long")));
    }

}
