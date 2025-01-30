package com.admiral;

import java.util.Map;

public class GestorePrenotazioni {
    
    private static GestorePrenotazioni gestorePrenotazioni;
    private Map<Integer, Prenotazione> prenotazioni;
    private Prenotazione prenotazioneCorrente;
    private Nave naveCorrente;

    private GestorePrenotazioni(){}

    public static GestorePrenotazioni getInstance() {
        if (gestorePrenotazioni == null)
            gestorePrenotazioni = new GestorePrenotazioni();

        return gestorePrenotazioni;
    }

    public void nuovaPrenotazione(){
        int numeroPrenotazione = generaNumeroPrenotazione();

        this.prenotazioneCorrente = new Prenotazione(numeroPrenotazione);
    }

    public int generaNumeroPrenotazione(){
        return prenotazioni.size() + 1;
    }

    public void selezionaTipoCabina(int idTipoCabina){

    }

    public void inserisciNumeroOspiti(int numeroOspiti){

    }

    public void confermaPrenotazione(){

    }

    public void setCabina(Cabina cabina){

    }

    public void setItinerario(Itinerario i){ 
        prenotazioneCorrente.setItinerario(i);

        naveCorrente.getTipiCabina(); // qui
    }

    public void setNave(){
        naveCorrente = prenotazioneCorrente.getItinerario().getNave(); // togliere n Nave in setNave?
    }
}