package com.admiral;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class ItinerarioTest {

    @Test void durataTest(){
        LocalDate dataPartenza = LocalDate.of(2025, 1, 15);
        LocalDate dataRitorno = LocalDate.of(2025, 1, 30);

        Itinerario i = new Itinerario("I1", "1", "1", "1", dataPartenza, dataRitorno);
    
        assertEquals(15, i.getDurata());
    }

    @Test void calcolaPrezzoItinerarioTest(){
        LocalDate dataPartenza = LocalDate.of(2025, 1, 15);
        LocalDate dataRitorno = LocalDate.of(2025, 1, 30);

        Destinazione d = new Destinazione("1", "Mediterraneo", 900F);
        Itinerario i = new Itinerario("I1", d.getCodice(), "1", "1", dataPartenza, dataRitorno);
    
        assertEquals(2400, i.getPrezzo());
    }
    
    @Test
    public void inserisciPortoDaVisitareTest(){
        LocalDate dataPartenza = LocalDate.of(2025, 1, 15);
        LocalDate dataRitorno = LocalDate.of(2025, 1, 22);

        Itinerario i = new Itinerario("I1", "1", "1", "1", dataPartenza, dataRitorno);
        assertNotNull(i, "i non inserito");
        assertEquals("I1", i.getCodice());

        Porto p = new Porto("4", "Palermo");
        i.inserisciPortoDaVisitare(p.getCodice(), p);
        assertNotNull(i.getPortiDaVisitare(), "Nessun porto da visitare");
        assertEquals(1, i.getPortiDaVisitare().size());
    }

}
