package com.admiral;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

        this.portiDaVisitare = new HashMap<>();
    }

    public void inserisciPortoDaVisitare(String codicePorto, Porto p){
        this.portiDaVisitare.put(codicePorto, p);
    }

    public String getCodice(){
        return codice;
    }

    public String toString(){
        String s = "Itinerario " + codice + "\n"
                    + "Destinazione: " + destinazione + "\n"
                    + "Porto di partenza: " + portoPartenza +"\n"
                    + "Nave: " + nave + "\n"
                    + "Prezzo per persona: " + destinazione.getPrezzo() + "\n"
                    + "Data di partenza: " + dataPartenza + "  Data di ritorno: " + dataRitorno + "\n"
                    + "Durata: " + durata + " giorni\n"
                    + "Lista Porti da visitare: \n";
        for (Porto porto : portiDaVisitare.values()) {
                    s += "\t" + porto.getNome() + "\n";    
        }
        return s;
    }

    public Map<String, Porto> getPortiDaVisitare(){
        return this.portiDaVisitare;
    }

    public Porto getPorto(String codice){
        return portiDaVisitare.get(codice);
    }
}