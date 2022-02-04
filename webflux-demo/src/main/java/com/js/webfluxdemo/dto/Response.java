package com.js.webfluxdemo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
public class Response {
    private final Date date = new Date();
    private final int output;

    @JsonCreator
    public Response(@JsonProperty("output") int output) {
        this.output = output;
    }
}
