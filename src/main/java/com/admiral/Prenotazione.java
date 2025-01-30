package com.admiral;

import java.time.LocalDate;

public class Prenotazione {
    
    private int numeroPrenotazione;
    private int numeroOspiti;
    private float prezzoTotale;
    private Itinerario itinerario;
    private Cabina cabina;
    private ScontoStrategy scontoStrategy;

    public Prenotazione(int numeroPrenotazione){
        this.numeroPrenotazione = numeroPrenotazione;
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

    public void setPrezzoTotale(float prezzoTotale){
        this.prezzoTotale = prezzoTotale;
    }

    public void setScontoStrategy(ScontoStrategy scontoStrategy){
        this.scontoStrategy = scontoStrategy;
    }

    public int getNumero(){
        return this.numeroPrenotazione;
    }


}