package com.admiral;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AdmiralTest {
    static Admiral admiral;

    @BeforeAll
    public static void initTest() {
        admiral = Admiral.getInstance();
    }

    @Test
    public void testInserisciNuovoItinerario() {
        admiral.inserisciNuovoItinerario("1", "1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        assertNotNull(admiral.getItinerarioCorrente());
    }

}
