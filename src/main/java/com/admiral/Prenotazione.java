package com.admiral;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Prenotazione {
    
    private int numeroPrenotazione;
    private int numeroOspiti;
    private float prezzoTotale;
    private Itinerario itinerario;
    private Cabina cabina;
    private ScontoStrategy scontoStrategy;
    private Pacchetto pacchettoCorrente;
    private Map<String, Pacchetto> pacchettiPrenotati;

    public Prenotazione(int numeroPrenotazione){
        this.numeroPrenotazione = numeroPrenotazione;

        this.pacchettiPrenotati = new HashMap<>();
    }

    public void calcolaPrezzo(){
        calcolaPrezzoPrenotazione();
        float sconto = calcolaSconto();
        setPrezzoTotale(this.prezzoTotale - sconto);
    }

    public void setNumeroOspiti(int numeroOspiti){
        this.numeroOspiti = numeroOspiti;
        calcolaPrezzo();
    }

    public void setItinerario(Itinerario i){
        itinerario = i;
    }

    public float calcolaPrezzoPerNumeroOspiti(){
        return itinerario.getPrezzo() * numeroOspiti;
    }

    public void calcolaPrezzoPrenotazione(){
        setPrezzoTotale(calcolaPrezzoPerNumeroOspiti() + cabina.getPrezzo());

    }

    public float calcolaSconto(){
        float scontoLastMinute = 0;

        if(LocalDate.now().getMonthValue() == (itinerario.getDataPartenza().minusMonths(1).getMonthValue())){
            setScontoStrategy(new ScontoLastMinute());
            scontoLastMinute = scontoStrategy.calcolaSconto(this.prezzoTotale);
        }

        return scontoLastMinute;
    }

    public Itinerario getItinerario(){
        return itinerario;
    }

    public Cabina getCabina(){
        return cabina;
    }

    public void setPrezzoTotale(float prezzoTotale){
        this.prezzoTotale = prezzoTotale;
    }

    public void setScontoStrategy(ScontoStrategy scontoStrategy){
        this.scontoStrategy = scontoStrategy;
    }

    public void setCabina(Cabina cabina){
        this.cabina = cabina;
    }

    public int getNumero(){
        return this.numeroPrenotazione;
    }

    public String toString() {
        String s = "Prenotazione " + numeroPrenotazione + "\n"
                + "Destinazione: " + itinerario.getDestinazione().getNome() + "\n"
                + "Porto di partenza: " + itinerario.getPortoPartenza().getNome() + "\n"
                + "Nave: " + itinerario.getNave().getNome() + "\n"
                + "Prezzo totale: " + prezzoTotale + "\n"
                + "Data di partenza: " + itinerario.getDataPartenza() + "\n";
        return s;
    }
}