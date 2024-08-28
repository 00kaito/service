package com.bednarz.usmobile.infrastructure.shared.audit;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTrailRepository extends MongoRepository<AuditTrail, String> {
}