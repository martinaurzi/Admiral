package com.admiral;

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

    public void setNumeroOspiti(int numeroOspiti){
        this.numeroOspiti = numeroOspiti;
    }

    public float calcolaPrezzoPerNumeroOspiti(){
        return itinerario.getPrezzo() * numeroOspiti;
    }

    public float calcolaPrezzoPrenotazione(){
        setPrezzoTotale(calcolaPrezzoPerNumeroOspiti() + cabina.getPrezzo());

        return this.prezzoTotale;
    }

    public void setPrezzoTotale(float prezzoTotale){
        this.prezzoTotale = prezzoTotale;
    }

    public void setScontoStrategy(ScontoStrategy scontoStrategy){
        this.scontoStrategy = scontoStrategy;
    }

    /*
    public float calcolaSconto(){
        setScontoStrategy(new ScontoLastMinute());
        scontoStrategy.calcolaSconto(this.prezzoTotale);
    }*/
}