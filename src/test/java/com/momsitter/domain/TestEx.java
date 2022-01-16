package com.momsitter.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestEx {

    @Test
    void test() {
        String birthday = "19920530";
        LocalDate parse = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(parse);
        LocalDate localDate = LocalDate.of(1992, 5, 30);
        System.out.println(localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
