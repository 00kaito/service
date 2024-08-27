package com.bednarz.usmobile.infrastructure.persistence;

import com.bednarz.usmobile.domain.user.User;
import com.bednarz.usmobile.domain.user.UserRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<User, String>, UserRepository {
}
