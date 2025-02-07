package com.admiral;

import org.junit.jupiter.api.BeforeAll;

public class AdmiralTest {
    static Admiral admiral;

    @BeforeAll
    public static void initTest() {
        admiral = Admiral.getInstance();
    }
}
