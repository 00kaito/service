package com.bednarz.usmobile.domain.dto.model;

public record UserDto(
        String id,
        String firstName,
        String lastName,
        String email
) {
}