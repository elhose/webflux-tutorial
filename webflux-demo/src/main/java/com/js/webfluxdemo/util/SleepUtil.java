package com.js.webfluxdemo.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SleepUtil {
    @SneakyThrows
    public static void sleepSeconds(int seconds) {
        Thread.sleep(1000L * seconds);
    }
}
