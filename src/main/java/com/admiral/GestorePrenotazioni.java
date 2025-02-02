package com.admiral;

import java.util.HashMap;
import java.util.Map;

public class GestorePrenotazioni {
    
    private static GestorePrenotazioni gestorePrenotazioni;
    private Prenotazione prenotazioneCorrente;
    private Nave naveCorrente;
    private Map<Integer, Prenotazione> prenotazioni;
    private Map<String, Pacchetto> pacchetti;

    private GestorePrenotazioni(){
        this.prenotazioni = new HashMap<>();
        this.pacchetti = new HashMap<>();

        loadPacchetti();
    }

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
        System.out.println(prenotazioneCorrente);
    }

    public void confermaPrenotazione(){
        if (prenotazioneCorrente != null) {
            this.prenotazioni.put(prenotazioneCorrente.getNumero(), prenotazioneCorrente);
            System.out.println(prenotazioneCorrente);
        }
    }

    public void loadPacchetti(){
        Pacchetto p1 = new PacchettoBevande("p1", "Bevande", 200F);
        Pacchetto p2 = new PacchettoBevande("p2", "Bevande Plus", 250F);
        Pacchetto p3 = new PacchettoEscursioni("p3", "Escursioni", 600F, 3, true);
        Pacchetto p4 = new PacchettoBenessere("p4", "Benessere", 300F, 3);
        Pacchetto p5 = new PacchettoEscursioni("p5", "Escursioni", 1200F, 10, true);
        Pacchetto p6 = new PacchettoBenessere("p6", "Benessere", 90F, 1);
        this.pacchetti.put("p1", p1);
        this.pacchetti.put("p2", p2);
        this.pacchetti.put("p3", p3);
        this.pacchetti.put("p4", p4);
        this.pacchetti.put("p5", p5);
        this.pacchetti.put("p6", p6);
        System.out.println("Caricamento Pacchetti Completato");
    }

    public boolean verificaNumeroPrenotazione(int numeroPrenotazione){
        Prenotazione p = this.prenotazioni.get(numeroPrenotazione);
        setPrenotazioneCorrente(p);  

        if(p != null) 
            return true; 
        else 
            return false;
    }

    public void visualizzaPacchetti(){
        pacchetti.forEach((codice, p) -> {
            System.out.println(p);
        });
    }

    public void visualizzaPrenotazioni(){
        prenotazioni.forEach((numero, p) -> {
            System.out.println(p);
        });
    }

    public void findPacchetto(String codicePacchetto){
        Pacchetto pa = pacchetti.get(codicePacchetto);

        if(pa != null)
            prenotazioneCorrente.setPacchettoCorrente(pa);
    }

    public void confermaAcquisto(){
        prenotazioneCorrente.confermaAcquisto();
    }

    public void setItinerario(Itinerario i){ 
        prenotazioneCorrente.setItinerario(i);
        setNave();

        Map<Integer, TipoCabina> tipiCabine = naveCorrente.getTipiCabina();

        tipiCabine.forEach((id, tc) -> {
            System.out.println(tc);
        });
    }

    public void setNave(){
        naveCorrente = prenotazioneCorrente.getItinerario().getNave(); // togliere n Nave in setNave?
    }

    public Prenotazione getPrenotazioneCorrente(){
        return prenotazioneCorrente;
    }

    public Map<Integer, Prenotazione> getPrenotazioni(){
        return prenotazioni;
    }

    public void setPrenotazioneCorrente(Prenotazione p){
        this.prenotazioneCorrente = p;
    }
}