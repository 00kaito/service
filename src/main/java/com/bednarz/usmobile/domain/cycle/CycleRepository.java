package com.bednarz.usmobile.domain.cycle;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleRepository extends MongoRepository<Cycle, String> {

    List<Cycle> findByMdnAndUserId(String mdn, String userId);

    List<Cycle> findByMdn(String mdn);
}
