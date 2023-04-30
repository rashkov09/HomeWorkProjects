package com.scalefocus.midterm.trippyapp.util;

import com.scalefocus.midterm.trippyapp.exception.MissingRequestFieldsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class ObjectChecker<T> {
    private static final Logger log = LoggerFactory.getLogger(ObjectChecker.class);

    public Boolean checkForMissingFields(T request) {
        for (Field field : request.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(request);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (value == null) {
                log.error(String.format("Null value for field %s!", field.getName()));
                throw new MissingRequestFieldsException();
            }
        }
        return false;
    }
}
