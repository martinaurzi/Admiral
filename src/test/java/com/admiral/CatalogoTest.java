package com.admiral;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertNotNull(catalogo.getDestinazione("D1"));
        assertNotNull(catalogo.getPorto("PO1"));
        assertNotNull(catalogo.getNave("N1"));
    }

    @Test
    public void inserisciNuovoItinerarioTest() {
        catalogo.inserisciNuovoItinerario("D1", "N1", "PO1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        assertNotNull(catalogo.getItinerarioCorrente());
    }

    @Test
    public void confermaInserimentoTest() {
        catalogo.inserisciNuovoItinerario("D1", "N1", "PO1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        catalogo.confermaInserimento();
        assertEquals(1, catalogo.getItinerari().size());
        assertNotNull(catalogo.getItinerario("I1"));
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
        Nave n = new Nave("N1","Dorata", false);
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

    @Test
    public void inserisciEscursioneInPortoTest(){
        catalogo.inserisciEscursioneInPorto("PO1");
        assertNotNull(catalogo.getPortoCorrente());
    }

    @Test
    public void inserisciEscursioneTest(){
        catalogo.inserisciEscursioneInPorto("PO1");
        catalogo.inserisciEscursione("Trekking", 2, 3);
        Porto portoCorrente = catalogo.getPortoCorrente();
        assertNotNull(portoCorrente.getEscursioneCorrente());      
    }

    @Test
    public void confermaInserimentoEscursioneTest(){
        catalogo.inserisciEscursioneInPorto("PO1");
        catalogo.inserisciEscursione("Trekking", 2, 3);
        catalogo.confermaInserimentoEscursione();
        Porto portoCorrente = catalogo.getPortoCorrente();
        assertEquals(1, portoCorrente.getEscursioni().size());
        assertNotNull(portoCorrente.getEscursione("E1"));
    }

}
