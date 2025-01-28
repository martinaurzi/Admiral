package com.admiral;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    public void inserisciPortoDaVisitareTest(){
        Itinerario i = new Itinerario("1", "1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        assertNotNull(i);

        Porto p = admiral.getPorto("2");
        assertNotNull(p);

        i.inserisciPortoDaVisitare(p.getCodice(), p);
        assertEquals(1, i.getPortiDaVisitare().size());
        assertNotNull(i.getPorto("2"));

        assertNull(i.getPorto("8"));
    }
}