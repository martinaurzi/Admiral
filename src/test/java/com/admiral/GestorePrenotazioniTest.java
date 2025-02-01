package com.admiral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestorePrenotazioniTest {
    GestorePrenotazioni gestorePrenotazioni;
    Catalogo catalogo;

    @BeforeEach
    public void setUp() {
        gestorePrenotazioni = GestorePrenotazioni.getInstance();
        catalogo = Catalogo.getInstance();
    }

    @Test
    public void gestoreInizializzazioneTest() {
        assertNotNull(gestorePrenotazioni);
        assertNotNull(catalogo);
    }
    
    @Test
    public void nuovaPrenotazioneTest(){
        gestorePrenotazioni.nuovaPrenotazione();
        assertNotNull(gestorePrenotazioni.getPrenotazioneCorrente());
    }
    
    @Test
    public void confermaPrenotazioneTest(){
        gestorePrenotazioni.nuovaPrenotazione();
        Itinerario i = new Itinerario("i1", "1", "1", "1", LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 30));
        gestorePrenotazioni.setItinerario(i);
        gestorePrenotazioni.selezionaTipoCabina(1);
        gestorePrenotazioni.inserisciNumeroOspiti(2);
        gestorePrenotazioni.confermaPrenotazione();
        assertNotNull(gestorePrenotazioni.getPrenotazioni().values().contains(gestorePrenotazioni.getPrenotazioneCorrente()));
        assertEquals(1, gestorePrenotazioni.getPrenotazioni().size());
    }

    @Test
    public void verificaNumeroPrenotazioneTest(){
        Prenotazione p = new Prenotazione(6);
        gestorePrenotazioni.getPrenotazioni().put(6, p);
        boolean result = gestorePrenotazioni.verificaNumeroPrenotazione(6);
        assertEquals(true, result);
    }
}
