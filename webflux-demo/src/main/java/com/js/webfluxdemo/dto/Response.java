package com.js.webfluxdemo.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
public class Response {
    private final Date date = new Date();
    private final int output;

    public Response(int output) {
        this.output = output;
    }
}
