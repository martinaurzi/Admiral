package com.admiral;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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

    private GestorePrenotazioni() {
        this.prenotazioni = new HashMap<>();
        this.pacchetti = new HashMap<>();

        loadPacchetti();
    }

    public static GestorePrenotazioni getInstance() {
        if (gestorePrenotazioni == null)
            gestorePrenotazioni = new GestorePrenotazioni();

        return gestorePrenotazioni;
    }

    public void nuovaPrenotazione() {
        int numeroPrenotazione = generaNumeroPrenotazione();

        this.prenotazioneCorrente = new Prenotazione(numeroPrenotazione);
    }

    public int generaNumeroPrenotazione() {
        if (prenotazioni == null)
            return 1;

        return prenotazioni.size() + 1;
    }

    public Cabina selezionaTipoCabina(int idTipoCabina) {
        TipoCabina tc = naveCorrente.selezionaTipoCabina(idTipoCabina);
        Itinerario i = prenotazioneCorrente.getItinerario();

        prenotazioni.forEach((numP, p) -> {
            if (p.getItinerario().getCodice().equals(i.getCodice())) {
                Cabina cabinaPrenotata = p.getCabina();

                if (cabinaPrenotata.getTipo().getId() == tc.getId()) {
                    tc.rimuoviCabina(cabinaPrenotata);
                }
            }
        });

        Cabina cabina = tc.getCabinaDisponibile();

        if (cabina != null)
            prenotazioneCorrente.setCabina(cabina);

        return cabina;
    }

    public void inserisciNumeroOspiti(int numeroOspiti) {
        prenotazioneCorrente.setNumeroOspiti(numeroOspiti);
        System.out.println(prenotazioneCorrente);
    }

    public void confermaPrenotazione() {
        if (prenotazioneCorrente != null) {
            this.prenotazioni.put(prenotazioneCorrente.getNumero(), prenotazioneCorrente);
            System.out.println(prenotazioneCorrente);
        }
    }

    public void loadPacchetti() {
        ObjectMapper pacchettiMapper = new ObjectMapper();

        try {
            JsonNode pacchettiNode = pacchettiMapper
                    .readTree(new File("src/main/java/com/admiral/data/pacchetti.json"));

            for (JsonNode node : pacchettiNode) {
                String codice = node.get("codice").asText();
                String nome = node.get("nome").asText();
                float prezzo = node.get("prezzo").floatValue();

                if (nome.equals("Bevande") || nome.equals("Bevande Plus")) {
                    Pacchetto p = new PacchettoBevande(codice, nome, prezzo);

                    this.pacchetti.put(codice, p);
                } else if (nome.equals("Escursioni")) {
                    int numeroEscursioni = node.get("numeroEscursioni").intValue();
                    boolean includeGuida = node.get("includeGuida").booleanValue();

                    Pacchetto p = new PacchettoEscursioni(codice, nome, prezzo, numeroEscursioni, includeGuida);

                    this.pacchetti.put(codice, p);
                } else if (nome.equals("Benessere")) {
                    int durataOre = node.get("durataOre").intValue();

                    Pacchetto p = new PacchettoBenessere(codice, nome, prezzo, durataOre);

                    this.pacchetti.put(codice, p);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file pacchetti.json");
            e.printStackTrace();
        }
    }

    public boolean verificaNumeroPrenotazione(int numeroPrenotazione) {
        Prenotazione p = findPrenotazione(numeroPrenotazione);
        setPrenotazioneCorrente(p);

        return p != null;
    }

    public Prenotazione annullaPrenotazione(int numeroPrenotazione){
        return findPrenotazione(numeroPrenotazione);
    }

    public boolean checkIfAnnullabile(Prenotazione p){
        if(LocalDate.now().getMonthValue() <= (p.getItinerario().getDataPartenza().minusMonths(2).getMonthValue()))
            return true;
        else
            return false;
    }

    public boolean rimuoviPrenotazione(Prenotazione p){
        if(prenotazioni.remove(p.getNumero()) != null)
            return true;
        else
            return false;
    }

    public void visualizzaPacchetti() {
        pacchetti.forEach((codice, p) -> {
            System.out.println(p);
        });
    }

    public void visualizzaPrenotazioni() {
        if(!prenotazioni.isEmpty()){
            prenotazioni.forEach((numero, p) -> {
                System.out.println(p);
            });
        } else {
            System.out.println("Nessuna prenotazione");
        }
    }

    public Prenotazione findPrenotazione(int numeroPrenotazione){
        Prenotazione p = this.prenotazioni.get(numeroPrenotazione);

        if(p != null)
            return p;
        
            System.out.println("Prenotazione non trovata");
            return null;
        
    }

    public void findPacchetto(String codicePacchetto) {
        Pacchetto pa = pacchetti.get(codicePacchetto);

        if (pa != null)
            prenotazioneCorrente.setPacchettoCorrente(pa);
    }

    public void confermaAcquisto() {
        prenotazioneCorrente.confermaAcquisto();
    }

    public void setItinerario(Itinerario i) {
        prenotazioneCorrente.setItinerario(i);
        setNave();

        Map<Integer, TipoCabina> tipiCabine = naveCorrente.getTipiCabina();

        tipiCabine.forEach((id, tc) -> {
            System.out.println(tc);
        });
    }

    public void setNave() {
        naveCorrente = prenotazioneCorrente.getItinerario().getNave(); 
    }

    public Prenotazione getPrenotazioneCorrente() {
        return prenotazioneCorrente;
    }

    public Map<Integer, Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioneCorrente(Prenotazione p) {
        this.prenotazioneCorrente = p;
    }

    public boolean validateSelezionaTipoCabina(int codiceTipoCabina) {
        return naveCorrente.getTipiCabina().containsKey(codiceTipoCabina);
    }

    public boolean validatePacchetto(String codicePacchetto) {
        return pacchetti.containsKey(codicePacchetto);
    }
}