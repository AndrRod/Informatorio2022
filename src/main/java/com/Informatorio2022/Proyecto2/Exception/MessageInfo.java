package com.Informatorio2022.Proyecto2.Exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageInfo {
    @JsonProperty("message")
    private String message;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("path")
    private String path;
}
