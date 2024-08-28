package com.bednarz.usmobile;

import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.user.User;
import com.bednarz.usmobile.infrastructure.util.LoadDataUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.List;

import static com.bednarz.usmobile.infrastructure.util.LoadDataUtil.insertData;

@SpringBootApplication
public class UsmobileApplication {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	LoadDataUtil initDataUtil;

	public static void main(String[] args) {
		SpringApplication.run(UsmobileApplication.class, args);
	}

	@PostConstruct
	private void cleanUP() throws IOException {
		mongoTemplate.dropCollection("cycle");
		mongoTemplate.dropCollection("user");
		mongoTemplate.dropCollection("daily_usage");
		List<User> users = initDataUtil.loadUsers("initdata/users.json");
		List<Cycle> cycles = initDataUtil.loadCycles("initdata/cycles.json");
		List<DailyUsage> dataUsage = initDataUtil.loadDailyUsage("initdata/dailyUsage.json");

		insertData(users, mongoTemplate);
		insertData(cycles, mongoTemplate);
		insertData(dataUsage, mongoTemplate);


	}
}
