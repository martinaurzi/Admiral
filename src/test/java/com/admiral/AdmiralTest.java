package com.admiral;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AdmiralTest {
    static Admiral admiral;

    @BeforeAll
    public static void initTest() {
        admiral = Admiral.getInstance();
    }
}
