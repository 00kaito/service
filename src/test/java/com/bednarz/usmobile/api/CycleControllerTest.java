package com.bednarz.usmobile.api;

import com.bednarz.usmobile.MongoBaseTest;
import com.bednarz.usmobile.application.dto.CycleDataResponse;
import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.bednarz.usmobile.infrastructure.persistence.CycleMongoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.List;

import static com.bednarz.usmobile.infrastructure.util.LoadDataUtil.*;
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


    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = loadUsers(objectMapper, "test-data/cycle-controller-test/users.json");
        List<Cycle> cycles = loadCycles(objectMapper, "test-data/cycle-controller-test/cycles.json");
        List<DailyUsage> dataUsage = loadDailyUsage(objectMapper, "test-data/cycle-controller-test/dailyUsage.json");

        insertData(users, mongoTemplate);
        insertData(cycles, mongoTemplate);
        insertData(dataUsage, mongoTemplate);

        /*        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();
        mongoTemplate.findAll(DBObject.class, "collection");
*/

    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.dropCollection(DailyUsage.class);
        mongoTemplate.dropCollection(Cycle.class);
    }

    @Test
    public void createCycleTest() throws Exception {
        CycleDataResponse request = new CycleDataResponse();
        request.setMdn("1234567890");
        request.setStartDate(new Date());
        request.setEndDate(new Date());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.mdn").value("1234567890"));
    }

    @Test
    public void createCycleTest_ReturnsValidationErrors() throws Exception {
        CycleDataResponse request = new CycleDataResponse();
        request.setMdn("invalidMdn");
        request.setStartDate(new Date());
        request.setEndDate(new Date());

        ResultActions resultActions = mockMvc.perform(post("/api/v1/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("MDN must be 10 digits long")));
    }

    @Test
    public void getCycleHistoryTest() throws Exception {
        String mdn = "2345678901";

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/history")
                        .param("mdn", mdn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());


    }

    @Test
    public void getCycleHistoryTest_ReturnsValidationErrors() throws Exception {
        String invalidMdn = "";

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/history")
                        .param("mdn", invalidMdn))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("MDN must be 10 digits long")));
    }

    @Test
    public void getCurrentCycleUsageTest() throws Exception {
        String mdn = "3456789012";
        String userId = "test-user-id-3";

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/usage/current")
                        .param("mdn", mdn)
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void getCurrentCycleUsageTest_ReturnsValidationErrors() throws Exception {
        String invalidMdn = "";
        String userId = "test-user-id-3";

        ResultActions resultActions = mockMvc.perform(get("/api/v1/cycles/usage/current")
                        .param("mdn", invalidMdn)
                        .param("userId", userId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("MDN must be 10 digits long")));
    }
}
