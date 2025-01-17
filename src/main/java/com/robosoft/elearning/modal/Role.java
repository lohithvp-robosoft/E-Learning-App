package com.robosoft.elearning.modal;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER,
    ADMIN;
//    @JsonValue
//    public String toJson() {
//        return name(); // Ensures the enum is serialized as a string (e.g., "USER")
//    }
}
