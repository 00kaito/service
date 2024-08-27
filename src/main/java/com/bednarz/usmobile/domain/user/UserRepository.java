package com.bednarz.usmobile.domain.user;

public interface UserRepository {
    User findByEmail(String email);

}
