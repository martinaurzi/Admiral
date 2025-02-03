package com.admiral;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CatalogoTest {

    Catalogo catalogo;

    @BeforeEach
    public void setUp() {
        catalogo = Catalogo.getInstance();
    }

    @Test
    public void catalogoInizializzazioneTest() {
        assertNotNull(catalogo);
    }

    @Test
    public void caricamentoDatiTest() {
        assertNotNull(catalogo.getDestinazione("1"));
        assertNotNull(catalogo.getPorto("1"));
        assertNotNull(catalogo.getNave("1"));
    }

    @Test
    public void inserisciNuovoItinerarioTest() {
        catalogo.inserisciNuovoItinerario("1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        assertNotNull(catalogo.getItinerarioCorrente());
    }

    @Test
    public void confermaInserimentoTest() {
        catalogo.inserisciNuovoItinerario("1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        catalogo.confermaInserimento();
        assertEquals(1, catalogo.getItinerari().size());
        assertNotNull(catalogo.getItinerario("I1"));
    }

    @Test
    public void inserisciPortoDaVisitareTest() {
        Itinerario i = new Itinerario("1", "1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        assertNotNull(i);

        Porto p = catalogo.getPorto("2");
        assertNotNull(p);

        i.inserisciPortoDaVisitare(p.getCodice(), p);
        assertEquals(1, i.getPortiDaVisitare().size());
        assertNotNull(i.getPorto("2"));

        assertNull(i.getPorto("8"));
    }

    @Test
    public void TrovaItinerariTest(){
        catalogo.inserisciNuovoItinerario("1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        catalogo.confermaInserimento();

        catalogo.trovaItinerari("1", 1);
        
    }
}
