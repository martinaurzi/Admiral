package com.admiral;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
            LocalDate dataPartenza, LocalDate dataRitorno) {
        Catalogo catalogo = Catalogo.getInstance();

        this.codice = codice;
        this.destinazione = catalogo.getDestinazione(codiceDestinazione);
        this.nave = catalogo.getNave(codiceNave);
        this.portoPartenza = catalogo.getPorto(codicePortoPartenza);
        this.dataPartenza = dataPartenza;
        this.dataRitorno = dataRitorno;
        this.durata = calcolaDurata(dataPartenza, dataRitorno);
        this.prezzo = calcolaPrezzoItinerario();

        this.portiDaVisitare = new HashMap<>();
    }

    public void modificaPortiDaVisitare(){
        this.portiDaVisitare.clear();
    }

    public void setDate(LocalDate dataPartenza, LocalDate dataRitorno){
        this.dataPartenza = dataPartenza;
        this.dataRitorno = dataRitorno;
        
        this.durata = calcolaDurata(dataPartenza, dataRitorno);
        this.prezzo = calcolaPrezzoItinerario();
    }

    public void inserisciPortoDaVisitare(String codicePorto, Porto p) {
        this.portiDaVisitare.put(codicePorto, p);
    }

    // Regola di dominio R1
    public float calcolaPrezzoItinerario() {
        return destinazione.getPrezzo() + (100 * durata);
    }

    public int calcolaDurata(LocalDate dataPartenza, LocalDate dataRitorno){
        return (int) ChronoUnit.DAYS.between(dataPartenza, dataRitorno);
    }

    public boolean checkMesePartenza(int mesePartenza) {
        return dataPartenza.getMonthValue() == mesePartenza;
    }

    public String getCodice() {
        return codice;
    }

    public Map<String, Porto> getPortiDaVisitare() {
        return this.portiDaVisitare;
    }

    public Porto getPorto(String codice) {
        return portiDaVisitare.get(codice);
    }

    public float getPrezzo() {
        return prezzo;
    }

    public int getDurata() {
        return durata;
    }

    public LocalDate getDataPartenza() {
        return dataPartenza;
    }

    public LocalDate getDataRitorno() {
        return dataRitorno;
    }

    public Destinazione getDestinazione() {
        return destinazione;
    }

    public Nave getNave() {
        return nave;
    }

    public Porto getPortoPartenza() {
        return portoPartenza;
    }

    public String toString() {
        String s = "\nItinerario " + codice + "\n"
                + "\t" + "Destinazione: " + destinazione + "\n"
                + "\t" + "Porto di partenza: " + portoPartenza + "\n"
                + "\t" + "Nave: " + nave + "\n"
                + "\t" + "Prezzo per persona: " + prezzo + " euro" +"\n"
                + "\t" + "Data di partenza: " + dataPartenza + "\n"
                + "\t" + "Data di ritorno: " + dataRitorno + "\n"
                + "\t" + "Durata: " + durata + " giorni\n"
                + "\t" + "Porti da visitare:\n"
                + "\t";
        for (Porto porto : portiDaVisitare.values()) {
            s += porto.getNome() + " ";
        }
        return s;
    }
}