package com.robosoft.elearning.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperUtil {

    private final ObjectMapper objectMapper;

    public ObjectMapperUtil() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
    }


    public <T> T convert(Object source, Class<T> targetClass) {
        try {
            return objectMapper.convertValue(source, targetClass);
        } catch (Exception e) {
            throw new RuntimeException("Error while converting object", e);
        }
    }
}
