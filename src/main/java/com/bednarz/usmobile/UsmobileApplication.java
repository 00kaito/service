package com.bednarz.usmobile;

import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.List;

import static com.bednarz.usmobile.infrastructure.util.LoadDataUtil.*;

@SpringBootApplication
public class UsmobileApplication {

	@Autowired
	MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(UsmobileApplication.class, args);
	}

	@PostConstruct
	private void cleanUP() throws IOException {
		mongoTemplate.dropCollection("cycle");
		mongoTemplate.dropCollection("user");
		mongoTemplate.dropCollection("daily_usage");
		ObjectMapper objectMapper = new ObjectMapper();
		List<User> users = loadUsers(objectMapper, "initdata/users.json");
		List<Cycle> cycles = loadCycles(objectMapper, "initdata/cycles.json");
		List<DailyUsage> dataUsage = loadDailyUsage(objectMapper, "initdata/dailyUsage.json");

		insertData(users, mongoTemplate);
		insertData(cycles, mongoTemplate);
		insertData(dataUsage, mongoTemplate);


	}
}
