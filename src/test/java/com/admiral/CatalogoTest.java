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
    public void trovaItinerariTest(){
        catalogo.inserisciNuovoItinerario("1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        catalogo.confermaInserimento();

        catalogo.trovaItinerari("1", 1);
        
    }

    @Test
    public void inserisciNuovaNaveTest(){
        catalogo.inserisciNuovaNave("Dorata");
        assertNotNull(catalogo.getNaveCorrente());
    }

    @Test
    public void confermaInserimentoNaveTest() {
        catalogo.inserisciNuovaNave("Dorata");
        catalogo.confermaInserimentoNave();
        assertEquals(9, catalogo.getNavi().size());
        assertNotNull(catalogo.getNaveByNome("Dorata"));
    }

    @Test
    public void inserisciTipoCabinaTest() {
        Nave n = new Nave("n1","Dorata", false);
        assertNotNull(n);

        n.inserisciTipoCabina("Cabina interna");
        assertEquals(1, n.getTipiCabina().size());
        assertNotNull(n.getTipoCabinaCorrente());
    }

    @Test
    public void inserisciCabinaTest() {
        TipoCabina tc = new TipoCabina(1,"Cabina interna");
        assertNotNull(tc);

        tc.inserisciCabina(1);
        assertEquals(1, tc.getCabine().size());
        assertNotNull(tc.getCabina(1));
    }

}
