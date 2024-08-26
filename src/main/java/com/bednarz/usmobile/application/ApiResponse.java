package com.bednarz.usmobile.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private String error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().data(data).build();
    }

    public static <T> ApiResponse<T> error(String error) {
        return ApiResponse.<T>builder().error(error).build();
    }

    public static <T> ApiResponse<T> error(T error) {
        return ApiResponse.<T>builder().error(error.toString()).build();
    }
}