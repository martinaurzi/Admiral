package com.admiral;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AdmiralTest {
    static Admiral admiral;

    @BeforeAll
    public static void initTest() {
        admiral = Admiral.getInstance();
    }

    @Test
    public void inserisciNuovoItinerarioTest() {
        admiral.inserisciNuovoItinerario("1", "1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        assertNotNull(admiral.getItinerarioCorrente());
    }

    @Test
    public void testConfermaInserimento(){
        admiral.inserisciNuovoItinerario("1", "1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        admiral.confermaInserimento();
        assertEquals(1, admiral.getItinerari().size());
        assertNotNull(admiral.getItinerario("1"));
    }
}
