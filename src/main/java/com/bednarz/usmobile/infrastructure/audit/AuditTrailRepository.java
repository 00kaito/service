package com.bednarz.usmobile.infrastructure.audit;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTrailRepository extends MongoRepository<AuditTrail, String> {
}