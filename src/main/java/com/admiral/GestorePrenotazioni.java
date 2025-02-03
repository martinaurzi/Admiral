package com.admiral;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        ObjectMapper pacchettiMapper = new ObjectMapper();

        try {
            JsonNode pacchettiNode = pacchettiMapper.readTree(new File("src/main/java/com/admiral/data/pacchetti.json"));

            for (JsonNode node : pacchettiNode) {  
                String codice = node.get("codice").asText();
                String nome = node.get("nome").asText();
                float prezzo = node.get("prezzo").floatValue();

                if(nome.equals("Bevande") || nome.equals("Bevande Plus")){
                    Pacchetto p = new PacchettoBevande(codice, nome, prezzo);

                    this.pacchetti.put(codice, p);
                }
                else if(nome.equals("Escursioni")){
                    int numeroEscursioni = node.get("numeroEscursioni").intValue();
                    boolean includeGuida = node.get("includeGuida").booleanValue();

                    Pacchetto p = new PacchettoEscursioni(codice, nome, prezzo, numeroEscursioni, includeGuida);

                    this.pacchetti.put(codice, p);
                }
                else if(nome.equals("Benessere")){
                    int durataOre = node.get("durataOre").intValue();

                    Pacchetto p = new PacchettoBenessere(codice, nome, prezzo, durataOre);

                    this.pacchetti.put(codice, p);
                }
            }

            System.out.println("Caricamento Pacchetti Completato");
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file pacchetti.json");
            e.printStackTrace();
        }
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