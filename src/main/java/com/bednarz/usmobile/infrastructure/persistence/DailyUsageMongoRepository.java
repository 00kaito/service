package com.bednarz.usmobile.infrastructure.persistence;

import com.bednarz.usmobile.domain.usage.DailyUsage;
import com.bednarz.usmobile.domain.usage.DailyUsageRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyUsageMongoRepository extends MongoRepository<DailyUsage, String>, DailyUsageRepository {
}
