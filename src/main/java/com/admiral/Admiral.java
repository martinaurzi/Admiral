package com.admiral;

import java.time.LocalDate;
import java.util.Map;

public class Admiral {

    private static Admiral admiral;
    private Catalogo catalogo;
    private GestorePrenotazioni gestorePrenotazioni;

    private Admiral() {
        this.catalogo = Catalogo.getInstance();
        this.gestorePrenotazioni = GestorePrenotazioni.getInstance();
    }

    public static Admiral getInstance() {
        if (admiral == null)
            admiral = new Admiral();
        else
            System.out.println("Istanza già creata");

        return admiral;
    }

    public void inserisciNuovoItinerario(String codiceDestinazione, String codiceNave,
            String codicePortoPartenza, LocalDate dataPartenza, LocalDate dataRitorno) {
        catalogo.inserisciNuovoItinerario(codiceDestinazione, codiceNave, codicePortoPartenza, dataPartenza,
                dataRitorno);
    }

    public void inserisciPortoDaVisitare(String codicePorto) {
        catalogo.inserisciPortoDaVisitare(codicePorto);
    }

    public void confermaInserimento() {
        catalogo.confermaInserimento();
    }

    public void nuovaPrenotazione() {
        gestorePrenotazioni.nuovaPrenotazione();

        catalogo.visualizzaDestinazioni();
    }

    public Map<String, Itinerario> selezionaDestinazione(String codiceDestinazione, int mesePartenza) {
        return catalogo.trovaItinerari(codiceDestinazione, mesePartenza);
    }

    public void selezionaItinerario(String codiceItinerario) {
        Itinerario i = catalogo.selezionaItinerario(codiceItinerario);

        gestorePrenotazioni.setItinerario(i);
    }

    // UC2 SD Seleziona Tipo Cabina
    public Cabina selezionaTipoCabina(int idTipoCabina) {
        return gestorePrenotazioni.selezionaTipoCabina(idTipoCabina);

    }

    public void inserisciNumeroOspiti(int numeroOspiti) {
        gestorePrenotazioni.inserisciNumeroOspiti(numeroOspiti);
    }

    public void confermaPrenotazione() {
        gestorePrenotazioni.confermaPrenotazione();
    }

    // UC3 Inserisci Numero Prenotazione
    public boolean inserisciNumeroPrenotazione(int numeroPrenotazione) {
        return gestorePrenotazioni.verificaNumeroPrenotazione(numeroPrenotazione);
    }

    // UC3 Seleziona pacchetto
    public void selezionaPacchetto(String codicePacchetto) {
        gestorePrenotazioni.findPacchetto(codicePacchetto);
    }

    // UC3 Conferma acquisto
    public void confermaAcquisto() {
        gestorePrenotazioni.confermaAcquisto();
    }

    // UC4 Inserisci nuova nave
    public void inserisciNuovaNave(String nomeNave) {
        catalogo.inserisciNuovaNave(nomeNave);
    }

    // UC4 Inserisci tipo cabina
    public void inserisciTipoCabina(String nomeTipoCabina) {
        catalogo.inserisciTipoCabina(nomeTipoCabina);
    }

    // UC4 Inserisci cabina
    public void inserisciCabina(int numeroCabina) {
        catalogo.inserisciCabina(numeroCabina);
    }

    // UC4 Conferma inserimento nave
    public void confermaInserimentoNave() {
        catalogo.confermaInserimentoNave();
    }

    // UC5 Verifica itinerario
    public boolean verificaItinerario(String codiceItinerario){
       if(catalogo.verificaItinerario(codiceItinerario)){
        return true;
       }else return false;
    }

    // UC5 Modifica date
    public void modificaDateItinerario(LocalDate dataPartenza, LocalDate dataRitorno){
        catalogo.modificaDateItinerario(dataPartenza, dataRitorno);
    }

    // UC5 Modifica porti da visitare
    public void modificaPortiDaVisitare(){
        catalogo.modificaPortiDaVisitare();
    }

    // UC6 Inserisci escursione in porto
    public boolean inserisciEscursioneInPorto(String codicePorto){
        if (catalogo.inserisciEscursioneInPorto(codicePorto)){
            return true;
        }else return false;
    }

    // UC6 Inserisci escursione
    public void inserisciEscursione(String nome, int durata, int difficolta){
        catalogo.inserisciEscursione(nome, durata, difficolta);
    }
    
    // UC6 Conferma inserimento escursione
    public void confermaInserimentoEscursione(){
        catalogo.confermaInserimentoEscursione();
    }

    // UC7 Annulla prenotazione
    public boolean annullaPrenotazione(int numeroPrenotazione){
        Prenotazione p = gestorePrenotazioni.annullaPrenotazione(numeroPrenotazione);

        if(p != null){
            if(gestorePrenotazioni.checkIfAnnullabile(p))
                if(gestorePrenotazioni.rimuoviPrenotazione(p)){
                    System.out.println("Prenotazione annullata con successo");
                    return true;
                }
        }

        return false;
    }

    // UC8 Visualizza itinerari
    public void visualizzaItinerari(){
        catalogo.visualizzaItinerari();
    }
}