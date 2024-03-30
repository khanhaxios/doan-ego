package com.ego.doan_ego.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {
    @Data
    static class Response {
        Object data;
        List<String> messages = new ArrayList<>();

        public Response(Object data, List<String> messages) {
            this.data = data;
            this.messages = messages;
        }

        public Response() {
        }

        public Response(Object data) {
            this(data, new ArrayList<>());
        }

        public Response(List<String> messages) {
            this(null, messages);
        }
    }

    public static ResponseEntity<?> resp(HttpStatus status, Object data, List<String> messages) {
        return new ResponseEntity<>(new ApiResponse.Response(data, messages), status);
    }

    public static ResponseEntity<?> success() {
        return new ResponseEntity<>(new ApiResponse.Response(null, List.of("Call api success")), HttpStatus.OK);
    }

    public static ResponseEntity<?> success(Object data) {
        return new ResponseEntity<>(new ApiResponse.Response(data, List.of("Call api success")), HttpStatus.OK);
    }

    public static ResponseEntity<?> accessDenied() {
        return new ResponseEntity<>(new ApiResponse.Response(null, List.of("You dont allow to access this resource")), HttpStatus.OK);
    }

    public static ResponseEntity<?> notFound(String requestUri) {
        return new ResponseEntity<>(new ApiResponse.Response(null, List.of("Handler of : " + requestUri + " not found")), HttpStatus.OK);
    }

    public static ResponseEntity<?> fallback(String msg) {
        return new ResponseEntity<>(new ApiResponse.Response(null, List.of("Call api failed because error : " + msg)), HttpStatus.BAD_REQUEST);
    }
}
