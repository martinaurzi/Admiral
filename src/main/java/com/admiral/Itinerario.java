package com.admiral;

import java.util.Map;
import java.time.LocalDate;

public class Itinerario {

    private String codice;
    private LocalDate dataPartenza;
    private LocalDate dataRitorno;
    private Integer durata;
    private Float prezzo;
    private Destinazione destinazione;
    private Nave nave;
    private Porto portoPartenza;
    private Map<String, Porto> portiDaVisitare;
    
    public Itinerario(String codice, String codiceDestinazione, String codiceNave, String codicePortoPartenza,
                        LocalDate dataPartenza, LocalDate dataRitorno){
        Admiral admiral = Admiral.getInstance();

        this.codice = codice;
        this.destinazione = admiral.getDestinazione(codiceDestinazione);
        this.nave = admiral.getNave(codiceNave);
        this.portoPartenza = admiral.getPorto(codicePortoPartenza);
        this.dataPartenza = dataPartenza;
        this.dataRitorno = dataRitorno;
    }

    public void inserisciPortoDaVisitare(String codicePorto, Porto p){

    }

    public String getCodice(){
        return codice;
    }
}