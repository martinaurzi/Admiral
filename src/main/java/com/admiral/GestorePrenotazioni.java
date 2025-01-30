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
        if (prenotazioni == null) return 1;
        return prenotazioni.size() + 1;
    }

    public void selezionaTipoCabina(int idTipoCabina){
        TipoCabina tc = naveCorrente.selezionaTipoCabina(idTipoCabina);
        Itinerario i = prenotazioneCorrente.getItinerario();

        prenotazioni.forEach((numP, p) -> {
            if(p.getItinerario().getCodice() == i.getCodice()){
                Cabina cabinaPrenotata = p.getCabina();

                if(cabinaPrenotata.getTipo().getId() == tc.getId()){
                    tc.rimuoviCabina(cabinaPrenotata);
                }
            }
        });

        Cabina cabina = tc.getCabinaDisponibile();

        if(cabina != null)
            prenotazioneCorrente.setCabina(cabina);
    }

    public void inserisciNumeroOspiti(int numeroOspiti){
        prenotazioneCorrente.setNumeroOspiti(numeroOspiti);
    }

    public void confermaPrenotazione(){
        if (prenotazioneCorrente != null) {
            this.prenotazioni.put(prenotazioneCorrente.getNumero(), prenotazioneCorrente);
            System.out.println("Prenotazione Effettuata con Successo");
        }
    }

    public void setItinerario(Itinerario i){ 
        prenotazioneCorrente.setItinerario(i);

        Map<Integer, TipoCabina> tipiCabine = naveCorrente.getTipiCabina();

        tipiCabine.forEach((id, tc) -> {
            System.out.println(tc);
        });
    }

    public void setNave(){
        naveCorrente = prenotazioneCorrente.getItinerario().getNave(); // togliere n Nave in setNave?
    }
}