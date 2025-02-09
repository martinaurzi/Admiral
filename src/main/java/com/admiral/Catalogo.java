package com.admiral;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Catalogo {

    private static Catalogo catalogo;
    private Map<String, Itinerario> itinerari;
    private Map<String, Destinazione> destinazioni;
    private Map<String, Porto> porti;
    private Map<String, Nave> navi;
    private String codice;
    private Itinerario itinerarioCorrente;
    private Nave naveCorrente;
    private Porto portoCorrente;

    private Catalogo() {
        this.itinerari = new HashMap<>();
        this.destinazioni = new HashMap<>();
        this.porti = new HashMap<>();
        this.navi = new HashMap<>();

        loadDestinazioni();
        loadPorti();
        loadNavi();
    }

    public static Catalogo getInstance() {
        if (catalogo == null)
            catalogo = new Catalogo();

        return catalogo;
    }

    public String generaCodiceItinerario() {
        return "I" + String.valueOf(itinerari.size() + 1);
    }

    public String generaCodiceNave() {
        return "N" + String.valueOf(navi.size() + 1);
    }

    public void inserisciNuovoItinerario(String codiceDestinazione, String codiceNave,
            String codicePortoPartenza, LocalDate dataPartenza, LocalDate dataRitorno) {
        if (getDestinazione(codiceDestinazione) == null) {
            System.out.println("Destinazione inesistente");
            return;
        }

        if (getPorto(codicePortoPartenza) == null) {
            System.out.println("Porto di partenza inesistente");
            return;
        }
        if (getNave(codiceNave) == null) {
            System.out.println("Porto di partenza inesistente");
            return;
        }

        codice = generaCodiceItinerario();

        this.itinerarioCorrente = new Itinerario(codice, codiceDestinazione, codiceNave, codicePortoPartenza,
                dataPartenza, dataRitorno);
    }

    public void inserisciPortoDaVisitare(String codicePorto) {
        if (itinerarioCorrente != null) {
            Porto p = getPorto(codicePorto);

            if (p != null) {
                this.itinerarioCorrente.inserisciPortoDaVisitare(codicePorto, p);
            } else
                System.out.println("Porto Inesistente");
        }
    }

    public void confermaInserimento() {
        if (itinerarioCorrente != null) {
            this.itinerari.put(itinerarioCorrente.getCodice(), itinerarioCorrente);
            System.out.println("Operazione Inserimento Itinerario Conclusa");
        }
    }

    public Map<String, Itinerario> trovaItinerari(String codiceDestinazione, int mesePartenza) {
        Map<String, Itinerario> checkItinerari = new HashMap<>();
        Destinazione d = findDestinazione(codiceDestinazione);

        itinerari.forEach((codiceItinerario, i) -> {
            Itinerario itinerarioCheck = catalogo.checkItinerario(d, mesePartenza, i);
            if (itinerarioCheck != null)
                checkItinerari.put(codiceItinerario, itinerarioCheck);
        });

        return checkItinerari;
    }

    public Itinerario selezionaItinerario(String codiceItinerario) {
        return itinerari.get(codiceItinerario);
    }

    public Destinazione findDestinazione(String codiceDestinazione) {
        return destinazioni.get(codiceDestinazione);
    }

    public Itinerario checkItinerario(Destinazione d, int mesePartenza, Itinerario i) {

        if (i.getDestinazione().getCodice().equals(d.getCodice()) && i.checkMesePartenza(mesePartenza)) {
            return i;
        }

        return null;
    }

    public boolean validateNomeNave(String nomeNave) {
        for (Nave n : navi.values()) {
            if (nomeNave.equalsIgnoreCase(n.getNome())) {
                return true;
            }
        }
        return false;
    }

    public boolean validateNomeTipoCabina(String nomeTipoCabina) {
        if (nomeTipoCabina.equalsIgnoreCase("Cabina interna")
                || nomeTipoCabina.equalsIgnoreCase("Cabina vista mare")
                || nomeTipoCabina.equalsIgnoreCase("Cabina con balcone")
                || nomeTipoCabina.equalsIgnoreCase("Suite")) {
            return true;
        }
        return false;
    }

    public void inserisciNuovaNave(String nomeNave) {
        String codice = generaCodiceNave();

        this.naveCorrente = new Nave(codice, nomeNave, false);
    }

    public void inserisciTipoCabina(String nomeTipoCabina) {
        naveCorrente.inserisciTipoCabina(nomeTipoCabina);
    }

    public void inserisciCabina(int numeroCabina) {
        naveCorrente.inserisciCabina(numeroCabina);
    }

    public void confermaInserimentoNave() {
        if (naveCorrente != null) {
            this.navi.put(naveCorrente.getCodice(), naveCorrente);
            System.out.println("Operazione Inserimento Nave Conclusa");
        }
    }

    public boolean verificaItinerario(String codiceItinerario) {
        Itinerario i = itinerari.get(codiceItinerario);
        if (i != null) {
            setItinerarioCorrente(i);
            return true;
        } else
            return false;
    }

    public void modificaDateItinerario(LocalDate dataPartenza, LocalDate dataRitorno) {
        itinerarioCorrente.setDate(dataPartenza, dataRitorno);

        System.out.println("Dati itinerario aggiornati: \n");
        ;
        System.out.println(itinerarioCorrente);
    }

    public void modificaPortiDaVisitare() {
        itinerarioCorrente.modificaPortiDaVisitare();
    }

    public boolean inserisciEscursioneInPorto(String codicePorto) {
        Porto p = porti.get(codicePorto);
        if (p != null) {
            setPortoCorrente(p);
            return true;
        } else
            return false;
    }

    public void inserisciEscursione(String nome, int durata, int difficolta) {
        portoCorrente.inserisciEscursione(nome, durata, difficolta);
    }

    public void confermaInserimentoEscursione() {
        portoCorrente.confermaInserimentoEscursione();
    }

    public void loadDestinazioni() {
        ObjectMapper destinazioniMapper = new ObjectMapper();

        try {
            JsonNode destinazioniNode = destinazioniMapper
                    .readTree(new File("src/main/java/com/admiral/data/destinazioni.json"));

            for (JsonNode node : destinazioniNode) {
                String codice = node.get("codice").asText();
                String nome = node.get("nome").asText();
                float prezzo = node.get("prezzo").floatValue();

                Destinazione d = new Destinazione(codice, nome, prezzo);
                this.destinazioni.put(codice, d);
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file destinazioni.json");
            e.printStackTrace();
        }
    }

    public void loadPorti() {
        ObjectMapper portiMapper = new ObjectMapper();

        try {
            JsonNode portiNode = portiMapper.readTree(new File("src/main/java/com/admiral/data/porti.json"));

            for (JsonNode node : portiNode) {
                String codice = node.get("codice").asText();
                String nome = node.get("nome").asText();

                Porto p = new Porto(codice, nome);
                this.porti.put(codice, p);
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file porti.json");
            e.printStackTrace();
        }
    }

    public void loadNavi() {
        ObjectMapper naviMapper = new ObjectMapper();

        try {
            JsonNode naviNode = naviMapper.readTree(new File("src/main/java/com/admiral/data/navi.json"));

            for (JsonNode node : naviNode) {
                String codice = node.get("codice").asText();
                String nome = node.get("nome").asText();

                Nave n = new Nave(codice, nome);
                this.navi.put(codice, n);
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file navi.json");
            e.printStackTrace();
        }
    }

    public Itinerario getItinerarioCorrente() {
        return itinerarioCorrente;
    }

    public Nave getNaveCorrente() {
        return naveCorrente;
    }

    public Map<String, Itinerario> getItinerari() {
        return this.itinerari;
    }

    public Map<String, Nave> getNavi() {
        return navi;
    }

    public Itinerario getItinerario(String codice) {
        return itinerari.get(codice);
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

    public Nave getNaveByNome(String nomeNave) {
        for (Nave n : navi.values()) {
            if (nomeNave.equalsIgnoreCase(n.getNome())) {
                return n;
            }
        }
        return null;
    }

    public Porto getPortoCorrente() {
        return portoCorrente;
    }

    public void visualizzaDestinazioni() {
        destinazioni.values().forEach(System.out::println);
    }

    public void visualizzaItinerari() {
        if (!itinerari.isEmpty()) {
            itinerari.forEach((codice, i) -> {
                System.out.println(i);
            });
        } else
            System.out.println("Nessun itinerario");
    }

    public void visualizzaPorti() {
        porti.forEach((codice, p) -> {
            System.out.println(p);
        });
    }

    public void visualizzaNavi() {
        navi.forEach((codice, n) -> {
            System.out.println(n);
        });
    }

    public boolean validateCodiceDestinazione(String codiceDestinazione) {
        return destinazioni.containsKey(codiceDestinazione);
    }

    public boolean validateDisponibilitaNave(String codiceNave, LocalDate partenza, LocalDate ritorno) {
        for (Itinerario i : itinerari.values()) {
            if (i.getNave().getCodice().equals(codiceNave)) {
                LocalDate partenzaItinerario = i.getDataPartenza();
                LocalDate ritornoItinerario = i.getDataRitorno();
                if (!(ritorno.isBefore(partenzaItinerario) || ritornoItinerario.isBefore(partenza))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateCodiceNave(String codice) {
        return navi.containsKey(codice);
    }

    public boolean validateCodicePorto(String codice) {
        return porti.containsKey(codice);
    }

    public boolean validateCodiceItinerario(String codice) {
        return itinerari.containsKey(codice);
    }

    public boolean validateInserisciPortoDaVisitare() {
        return !itinerarioCorrente.getPortiDaVisitare().isEmpty();
    }

    public void setPortoCorrente(Porto p) {
        this.portoCorrente = p;
    }

    public void setItinerarioCorrente(Itinerario i) {
        this.itinerarioCorrente = i;
    }
}