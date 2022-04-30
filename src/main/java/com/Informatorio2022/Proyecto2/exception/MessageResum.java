package com.Informatorio2022.Proyecto2.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageResum {
    @Autowired
    private MessageSource messageSource;
    public String message(String message, String param){
        return messageSource.getMessage(message, new Object[]{param}, Locale.ENGLISH);
    }
}
