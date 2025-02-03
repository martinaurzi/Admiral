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

    // 7. UC2 SD Seleziona Tipo Cabina
    public Cabina selezionaTipoCabina(int idTipoCabina) {
        return gestorePrenotazioni.selezionaTipoCabina(idTipoCabina);

    }

    public void inserisciNumeroOspiti(int numeroOspiti) {
        gestorePrenotazioni.inserisciNumeroOspiti(numeroOspiti);
    }

    public void confermaPrenotazione() {
        gestorePrenotazioni.confermaPrenotazione();
    }

    // 10. UC3 SD Inserisci Numero Prenotazione
    public boolean inserisciNumeroPrenotazione(int numeroPrenotazione) {
        return gestorePrenotazioni.verificaNumeroPrenotazione(numeroPrenotazione);
    }

    // 11. UC3 SD Seleziona pacchetto
    public void selezionaPacchetto(String codicePacchetto) {
        gestorePrenotazioni.findPacchetto(codicePacchetto);
    }

    // 12. UC3 SD Conferma acquisto
    public void confermaAcquisto() {
        gestorePrenotazioni.confermaAcquisto();
    }

    // 13. UC4 SD Inserisci nuova nave
    public void inserisciNuovaNave(String nomeNave) {
        catalogo.inserisciNuovaNave(nomeNave);
    }

    // 14. UC4 SD Inserisci tipo cabina
    public void inserisciTipoCabina(String nomeTipoCabina) {
        catalogo.inserisciTipoCabina(nomeTipoCabina);
    }

    // 15. UC4 Inserisci cabina
    public void inserisciCabina(int numeroCabina) {
        catalogo.inserisciCabina(numeroCabina);
    }

    // 16. UC4 SD Conferma inserimento nave
    public void confermaInserimentoNave() {
        catalogo.confermaInserimentoNave();
    }
}