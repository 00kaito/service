package com.bednarz.usmobile.api;

import com.bednarz.usmobile.MongoBaseTest;
import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
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

import java.util.List;

import static com.bednarz.usmobile.LoadDataUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class MdnControllerTest extends MongoBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;


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
    public void transferMdnTest() throws Exception {
        String mdn = "1234567890";
        String fromUserId = "test-user-id-1";
        String toUserId = "test-user-id-2";

        ResultActions resultActions = mockMvc.perform(post("/api/v1/mdn/transfer")
                        .param("mdn", mdn)
                        .param("fromUserId", fromUserId)
                        .param("toUserId", toUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mongoTemplate.findAll(DBObject.class, "cycle");
    }
}