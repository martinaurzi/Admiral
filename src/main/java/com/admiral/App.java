package com.admiral;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Map;

public class App {

    static public int menu(BufferedReader bf) {
        try {
            System.out.println("\n***ADMIRAL***");
            System.out.println("1. Inserisci un nuovo Itinerario");
            System.out.println("2. Effettua una nuova prenotazione");
            System.out.println("3. Acquista un pacchetto");
            System.out.println("4. Inserisci una nuova nave");
            System.out.println("5. Visualizza tutte le prenotazioni");
            System.out.println("6. Visualizza tutte le destinazioni");
            System.out.println("7. Visualizza tutti gli itinerari");
            System.out.println("8. Visualizza tutti i porti");
            System.out.println("9. Visualizza tutte le navi");
            System.out.println("0. Esci");
            System.out.print("---> Inserisci il numero dell'operazione che vuoi effettuare: ");
            return (Integer.parseInt(bf.readLine()));
        } catch (IOException e) {
            System.out.println("ERRORE durante la lettura da tastiera");
            System.exit(-1);
        }
        return -1;
    }

    public static void main(String[] args) {
        Admiral admiral = Admiral.getInstance();
        Catalogo catalogo = Catalogo.getInstance();
        GestorePrenotazioni gestorePrenotazioni = GestorePrenotazioni.getInstance();

        String codiceDestinazione, codiceNave, codicePortoPartenza, codicePorto, codiceItinerario;
        String nomeTipoCabina, nomeNave;
        LocalDate dataPartenza, dataRitorno;
        int mesePartenza, codiceTipoCabina, numeroOspiti, numeroCabina;

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        int scelta;

        admiral.inserisciNuovoItinerario("1", "1", "2", LocalDate.of(2025, 02, 03), LocalDate.of(2025, 02, 10));
        admiral.inserisciPortoDaVisitare("2");
        admiral.inserisciPortoDaVisitare("3");
        admiral.inserisciPortoDaVisitare("4");
        admiral.inserisciPortoDaVisitare("5");
        admiral.confermaInserimento();

        admiral.nuovaPrenotazione();
        admiral.selezionaDestinazione("1", 2);
        admiral.selezionaItinerario("I1");
        admiral.selezionaTipoCabina(4);
        admiral.inserisciNumeroOspiti(2);
        admiral.confermaPrenotazione();

        while ((scelta = menu(buf)) != 0) {
            switch (scelta) {
                // Nuovo itinerario
                case 1:
                    try {
                        System.out.println("Inserire le informazioni dell'itinerario");
                        do {
                            System.out.print("Codice della destinazione: ");
                            codiceDestinazione = buf.readLine();
                            if (!catalogo.validateCodiceDestinazione(codiceDestinazione)) {
                                System.out.println("Codice destinazione inesistente, riprova.");
                            }
                        } while (!catalogo.validateCodiceDestinazione(codiceDestinazione));
                        dataPartenza = DateValidation.leggiData(buf, "Data di partenza (YYYY-MM-DD): ");
                        do {
                            dataRitorno = DateValidation.leggiData(buf, "Data di ritorno (YYYY-MM-DD): ");
                            if (dataRitorno.isBefore(dataPartenza) || dataRitorno.isEqual(dataPartenza)) {
                                System.out.println(
                                        "La data di ritorno deve essere successiva alla data di partenza, riprova.");
                            }
                        } while (dataRitorno.isBefore(dataPartenza) || dataRitorno.isEqual(dataPartenza));
                        boolean codiceValido, disponibilitaValida = false;
                        do {
                            System.out.print("Codice della nave: ");
                            codiceNave = buf.readLine();

                            codiceValido = catalogo.validateCodiceNave(codiceNave);
                            if (!codiceValido) {
                                System.out.println("Codice nave inesistente, riprova.");
                                continue;
                            }

                            disponibilitaValida = catalogo.validateDisponibilitaNave(codiceNave, dataPartenza,
                                    dataRitorno);
                            if (!disponibilitaValida) {
                                System.out.println("La nave è già impegnata nelle date selezionate, riprova.");
                            }

                        } while (!codiceValido || !disponibilitaValida);

                        do {
                            System.out.print("Codice del porto di partenza: ");
                            codicePortoPartenza = buf.readLine();
                            if (!catalogo.validateCodicePorto(codicePortoPartenza)) {
                                System.out.println("Codice porto inesistente, riprova.");
                            }
                        } while (!catalogo.validateCodicePorto(codicePortoPartenza));

                        admiral.inserisciNuovoItinerario(codiceDestinazione, codiceNave, codicePortoPartenza,
                                dataPartenza, dataRitorno);

                        do {
                            System.out.print("Inserire il codice del porto da visitare ('stop' per terminare): ");
                            codicePorto = buf.readLine();
                            if (codicePorto.equalsIgnoreCase("stop"))
                                if (!catalogo.validateInserisciPortoDaVisitare()) {
                                    System.out.println("Inserire almeno un porto da visitare.");
                                } else
                                    break;
                            else if (!catalogo.validateCodicePorto(codicePorto)) {
                                System.out.println("Codice porto inesistente, riprova.");
                            } else {
                                admiral.inserisciPortoDaVisitare(codicePorto);

                            }
                        } while (!catalogo.validateCodicePorto(codicePorto) || !codicePorto.equals("stop")
                                || !catalogo.validateInserisciPortoDaVisitare());

                        System.out.print("\nConferma inserimento (si o no): ");
                        if (buf.readLine().equalsIgnoreCase("si")) {
                            admiral.confermaInserimento();
                        } else
                            break;
                    } catch (IOException e) {
                    }
                    break;

                // Nuova prenotazione
                case 2:
                    admiral.nuovaPrenotazione();

                    try {
                        Map<String, Itinerario> itinerariTrovati;
                        do {
                            do {
                                System.out.print("\nInserisci il codice della destinazione desiderata: ");
                                codiceDestinazione = buf.readLine();
                                if (!catalogo.validateCodiceDestinazione(codiceDestinazione)) {
                                    System.out.println("Codice destinazione inesistente, riprova.");
                                }
                            } while (!catalogo.validateCodiceDestinazione(codiceDestinazione));

                            boolean meseValido;
                            do {
                                System.out.print("\nInserisci il mese di partenza: ");
                                mesePartenza = Integer.parseInt(buf.readLine());

                                meseValido = mesePartenza >= 1 && mesePartenza <= 12;
                                if (!meseValido) {
                                    System.out.println("Mese non valido, riprova.");
                                }
                            } while (!meseValido);
                            itinerariTrovati = admiral.selezionaDestinazione(codiceDestinazione, mesePartenza);
                            if (itinerariTrovati.isEmpty()) {
                                System.out.println("Non è stato trovato nessun itinerario");
                            } else {
                                itinerariTrovati.forEach((codice, itinerario) -> {
                                    System.out.println(itinerario);
                                });
                            }
                        } while (itinerariTrovati.isEmpty());

                        do {
                            System.out.print("\nInserisci il codice dell' itinerario scelto: ");
                            codiceItinerario = buf.readLine();
                            if (!catalogo.validateCodiceItinerario(codiceItinerario)) {
                                System.out.println("Codice itinerario inesistente, riprova.");
                            } else {
                                admiral.selezionaItinerario(codiceItinerario);
                            }
                        } while (!catalogo.validateCodiceItinerario(codiceItinerario));

                        do {
                            System.out.print("\nInserisci il codice del tipo di cabina scelto: ");
                            codiceTipoCabina = Integer.parseInt(buf.readLine());

                            if (!gestorePrenotazioni.validateSelezionaTipoCabina(codiceTipoCabina)) {
                                System.out.println("Codice tipo cabina inesistente, riprova.");
                            } else {
                                do {
                                    if (admiral.selezionaTipoCabina(codiceTipoCabina) == null) {
                                        System.out.println("Il tipo di cabina che hai selezionato è sold out, riprova.");
                                    } else {
                                        admiral.selezionaTipoCabina(codiceTipoCabina);
                                    }
                                } while (admiral.selezionaTipoCabina(codiceTipoCabina) == null);
                            }
                        } while (!gestorePrenotazioni.validateSelezionaTipoCabina(codiceTipoCabina));

                        boolean numeroOspitiValido;
                        do {
                            System.out.print("\nInseririsci il numero di ospiti (max = 4): ");
                            numeroOspiti = Integer.parseInt(buf.readLine());
                            numeroOspitiValido = numeroOspiti >= 1 && numeroOspiti <= 4;
                            if (!numeroOspitiValido) {
                                System.out.println("Puoi inserire fino a 4 ospiti.");
                            }
                        } while (!numeroOspitiValido);
                        admiral.inserisciNumeroOspiti(numeroOspiti);

                        System.out.print("\nConferma la prenotazione (si o no): ");
                        if (buf.readLine().equalsIgnoreCase("si")) {
                            admiral.confermaPrenotazione();
                        } else
                            break;

                    } catch (IOException e) {
                    }
                    break;

                // Acquista pacchetto
                case 3:
                    try {
                        int numeroPrenotazione;
                        do {
                            System.out.print("Inserisci il numero della prenotazione: ");
                            numeroPrenotazione = Integer.parseInt(buf.readLine());

                            if (admiral.inserisciNumeroPrenotazione(numeroPrenotazione)) {
                                gestorePrenotazioni.visualizzaPacchetti();
                                boolean pacchettoValido;
                                do {
                                    System.out.print("Inserisci il codice del pacchetto da acquistare: ");
                                    String codicePacchetto = buf.readLine();
                                    pacchettoValido = gestorePrenotazioni.validatePacchetto(codicePacchetto);
                                    if (!pacchettoValido) {
                                        System.out.println("Codice pacchetto inesistente, riprova.");
                                    } else {
                                        admiral.selezionaPacchetto(codicePacchetto);
                                        admiral.confermaAcquisto();
                                    }
                                } while (!pacchettoValido);
                            } else
                                System.err.println("Prenotazione non trovata");
                        } while (!admiral.inserisciNumeroPrenotazione(numeroPrenotazione));
                    } catch (IOException e) {
                    }
                    break;

                // Nuova nave
                case 4:
                    try {
                        do {
                            System.out.print("Inserire il nome della nave: ");
                            nomeNave = buf.readLine();
                            if (!catalogo.validateNomeNave(nomeNave))
                                break;
                            System.out.print("La nave e' già presente nel Sistema\n4");
                        } while (true);
                        admiral.inserisciNuovaNave(nomeNave);

                        do {
                            System.out
                                    .print("Inserire il nome del tipo di cabina da aggiungere (digita 'stop' per terminare): ");
                            nomeTipoCabina = buf.readLine();
                            if (nomeTipoCabina.equalsIgnoreCase("stop"))
                                break;
                            if (catalogo.validateNomeTipoCabina(nomeTipoCabina)) {
                                admiral.inserisciTipoCabina(nomeTipoCabina);

                                do {
                                    System.out
                                            .print("Inserire il numero della cabina (digita '0' per terminare): ");
                                    numeroCabina = Integer.parseInt(buf.readLine());
                                    if (numeroCabina == 0) {
                                        break;
                                    } else {
                                        admiral.inserisciCabina(numeroCabina);
                                    }
                                } while (numeroCabina != 0);
                            } else {
                                System.out.println("Nome Tipo Cabina non valido, riprova.");
                            }
                        } while (!nomeTipoCabina.equals("stop"));

                        System.out.print("\nConferma inserimento (si o no): ");
                        if (buf.readLine().equalsIgnoreCase("si")) {
                            admiral.confermaInserimentoNave();
                        } else
                            break;
                    } catch (IOException e) {

                    }
                    break;

                // Visualizza prenotazioni
                case 5:
                    gestorePrenotazioni.visualizzaPrenotazioni();
                    break;

                // Visualizza destinazioni
                case 6:
                    catalogo.visualizzaDestinazioni();
                    break;

                // Visualizza itinerari
                case 7:
                    catalogo.visualizzaItinerari();
                    break;

                // Visualizza porti
                case 8:
                    catalogo.visualizzaPorti();
                    break;

                // Visualizza navi
                case 9:
                    catalogo.visualizzaNavi();
                    break;
            }
        }
    }
}