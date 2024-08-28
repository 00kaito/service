package com.bednarz.usmobile.domain.usage;

import com.bednarz.usmobile.MongoBaseTest;
import com.bednarz.usmobile.application.dto.CurrentCycleUsageResponse;
import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.user.User;
import com.bednarz.usmobile.infrastructure.util.LoadDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.bednarz.usmobile.infrastructure.util.LoadDataUtil.insertData;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DailyUsageDomainServiceTest extends MongoBaseTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    DailyUsageDomainService dailyUsageDomainService;


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
    @DisplayName("Get Current Cycle Usage: Valid date range, expecting 15 usage records")
    void getCurrentCycleUsageTest() {
        String userId = "test-user-id-2";
        String mdn = "2345678901";
        LocalDate startDate = convertStringToLocalDate("2024-08-01");
        LocalDate endDate = convertStringToLocalDate("2024-08-31");
        assert startDate != null;
        assert endDate != null;
        List<CurrentCycleUsageResponse> currentCycleUsage = dailyUsageDomainService.getCurrentCycleUsage(mdn, userId, startDate, endDate);
        assertEquals(15, currentCycleUsage.size(), "this user should have 15 dailyUsage elements in the date range");
    }


    public static LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + dateString);
            return null;
        }
    }


}