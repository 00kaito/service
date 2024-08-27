package com.bednarz.usmobile.infrastructure.persistence;

import com.bednarz.usmobile.domain.billing.Cycle;
import com.bednarz.usmobile.domain.billing.CycleRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleMongoRepository extends MongoRepository<Cycle, String>, CycleRepository {
}
