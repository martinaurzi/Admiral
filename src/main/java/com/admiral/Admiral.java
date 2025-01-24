package com.admiral;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Admiral {

    private static Admiral admiral;
    private Itinerario itinerarioCorrente;
    private Map<String, Itinerario> itinerari;
    private Map<String, Destinazione> destinazioni;
    private Map<String, Porto> porti;
    private Map<String, Nave> navi;

    private Admiral() {
        this.itinerari = new HashMap<>();
        this.destinazioni = new HashMap<>();
        this.porti = new HashMap<>();
        this.navi = new HashMap<>();

        loadDestinazioni();
        loadPorti();
        loadNavi();
    }

    public static Admiral getInstance() {
        if (admiral == null)
            admiral = new Admiral();
        else
            System.out.println("Istanza già creata");

        return admiral;
    }

    public void inserisciNuovoItinerario(String codice, String codiceDestinazione, String codiceNave,
            String codicePortoPartenza, LocalDate dataPartenza, LocalDate dataRitorno) {
        try {
            getPorto(codicePortoPartenza);
        } catch (Exception e) {
            
        }

        this.itinerarioCorrente = new Itinerario(codice, codiceDestinazione, codiceNave, codicePortoPartenza, dataPartenza, dataRitorno);
        System.out.println("Itinerario Inserito");
    }

    public void inserisciPortoDaVisitare(String codicePorto) {
        if (itinerarioCorrente != null) {
            Porto p = porti.get(codicePorto);
            if (p != null) {
                this.itinerarioCorrente.inserisciPortoDaVisitare(codicePorto, p);
                System.out.println("Porto da visitare inserito");
            } else
                System.out.println("Porto Inesistente");
        }
    }

    public void confermaInserimento() {
        if (itinerarioCorrente != null) {
            this.itinerari.put(itinerarioCorrente.getCodice(), itinerarioCorrente);
            System.out.println("Operazione Inserimento Intinerario Conclusa");
        }
    }

    public void loadDestinazioni() {
        Destinazione d1 = new Destinazione("1", "Mediterraneo", 100F);
        Destinazione d2 = new Destinazione("2", "Caraibi", 200F);
        Destinazione d3 = new Destinazione("3", "Nord Europa", 300F);
        this.destinazioni.put("1", d1);
        this.destinazioni.put("2", d2);
        this.destinazioni.put("3", d3);
        System.out.println("Caricamento Destinazioni Completato");
    }

    public void loadPorti() {
        Porto p1 = new Porto("1", "Catania");
        Porto p2 = new Porto("2", "Guadalupa");
        Porto p3 = new Porto("3", "Amburgo");
        this.porti.put("1", p1);
        this.porti.put("2", p2);
        this.porti.put("3", p3);
        System.out.println("Caricamento Porti Completato");
    }

    public void loadNavi() {
        Nave n1 = new Nave("1", "Smeralda");
        Nave n2 = new Nave("2", "Favolosa");
        Nave n3 = new Nave("3", "Diadema");
        this.navi.put("1", n1);
        this.navi.put("2", n2);
        this.navi.put("3", n3);
        System.out.println("Caricamento Navi Completato");
    }

    public Destinazione getDestinazione(String codDestinazione) {
        return destinazioni.get(codDestinazione);
    }

    public Porto getPorto(String codPorto) {
        return porti.get(codPorto);
    }

    public Nave getNave(String codNave) {
        return navi.get(codNave);
    }

    public Itinerario getItinerarioCorrente() {
        return itinerarioCorrente;
    }
}