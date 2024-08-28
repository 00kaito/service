package com.bednarz.usmobile.api;

import com.bednarz.usmobile.MongoBaseTest;
import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.bednarz.usmobile.infrastructure.util.LoadDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.bednarz.usmobile.infrastructure.util.LoadDataUtil.insertData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Autowired
    LoadDataUtil loadDataUtil;


    @BeforeEach
    void setUp() throws Exception {
        cleanUp();
        List<User> users = loadDataUtil.loadUsers("test-data/cycle-controller-test/users.json");
        List<Cycle> cycles = loadDataUtil.loadCycles("test-data/cycle-controller-test/cycles.json");
        List<DailyUsage> dataUsage = loadDataUtil.loadDailyUsage("test-data/cycle-controller-test/dailyUsage.json");

        insertData(users, mongoTemplate);
        insertData(cycles, mongoTemplate);
        insertData(dataUsage, mongoTemplate);
    }

    void cleanUp() {
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.dropCollection(DailyUsage.class);
        mongoTemplate.dropCollection(Cycle.class);
    }

    @Test
    public void transferMdnTest() throws Exception {
        //given
        final String mdn = "1234567890";
        final String fromUserId = "test-user-id-1";
        final String toUserId = "new-usmobile-user";

        List<Cycle> cycles = getAllCycles();
        List<DailyUsage> dailyUsageList = getAllDailyUsage();

        List<DailyUsage> dailyUsagesForFromUser = filterDailyUsageByUserId(dailyUsageList, fromUserId);
        Cycle newUserCycle = findCycleByUserId(cycles, toUserId);

        assertEquals(15, dailyUsagesForFromUser.size(), "Checking if the available dailyUsages for the user are equal to 15");
        assertNull(newUserCycle, "Before the MDN transfer, there should not be any cycle associated with new-usmobile-user");

        //when
        mockMvc.perform(post("/api/v1/mdn/transfer")
                        .param("mdn", mdn)
                        .param("fromUserId", fromUserId)
                        .param("toUserId", toUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        cycles = getAllCycles();
        dailyUsageList = getAllDailyUsage();

        newUserCycle = findCycleByUserId(cycles, toUserId);
        dailyUsagesForFromUser = filterDailyUsageByUserId(dailyUsageList, fromUserId);

        assertEquals(toUserId, newUserCycle.getUserId(), "A new billing cycle has started for the person to whom we transferred the number");
        assertEquals(0, dailyUsagesForFromUser.size(), "Old cycle data associated with the old number owner should be deleted");
    }

    private List<Cycle> getAllCycles() {
        return mongoTemplate.findAll(Cycle.class, "cycle");
    }

    private List<DailyUsage> getAllDailyUsage() {
        return mongoTemplate.findAll(DailyUsage.class, "daily_usage");
    }

    private List<DailyUsage> filterDailyUsageByUserId(List<DailyUsage> dailyUsageList, String userId) {
        return dailyUsageList.stream()
                .filter(u -> u.getUserId().equals(userId))
                .toList();
    }

    private Cycle findCycleByUserId(List<Cycle> cycles, String userId) {
        return cycles.stream()
                .filter(c -> c.getUserId().equals(userId))
                .findFirst()
                .orElseGet(() -> null);
    }

}