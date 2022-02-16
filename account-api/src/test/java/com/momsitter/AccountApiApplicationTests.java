package com.momsitter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void failTest() {
        assertThat(1234).isEqualTo(12);
    }
}
