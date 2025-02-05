package com.admiral;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Map;

public class App {

    // Metodo per validare e leggere un numero intero
    private static int leggiNumeroIntero(BufferedReader buf, String messaggio) throws IOException {
        int numero;
        while (true) {
            System.out.print(messaggio);
            try {
                numero = Integer.parseInt(buf.readLine());
                return numero;  // Ritorna il numero valido
            } catch (NumberFormatException e) {
                System.out.println("Errore: Inserire solo numeri interi validi!");
            }
        }
    }

    static public int menuPrincipale(BufferedReader bf) {
        try {
            System.out.println("\n***MENU PRINCIPALE ADMIRAL***");
            System.out.println("1. Amministratore");
            System.out.println("2. Cliente");
            System.out.println("0. Esci");
            System.out.print("---> Inserisci il numero relativo al tuo ruolo: ");
            return (Integer.parseInt(bf.readLine()));
        } catch (IOException e) {
            System.out.println("ERRORE durante la lettura da tastiera");
            System.exit(-1);
        }

        return -1;
    }

    static public int menuAmministratore(BufferedReader bf) {
        try {
            System.out.println("\n***MENU AMMINISTRATORE ADMIRAL***");
            System.out.println("1. Inserisci un nuovo itinerario");
            System.out.println("2. Modifica itinerario");
            System.out.println("3. Inserisci una nuova escursione");
            System.out.println("4. Inserisci una nuova nave");
            System.out.println("5. Visualizza tutti gli itinerari");
            System.out.println("6. Visualizza tutte le destinazioni");
            System.out.println("7. Visualizza tutti i porti");
            System.out.println("8. Visualizza tutte le navi");
            System.out.println("9. Visualizza tutte le prenotazioni");
            System.out.println("0. Esci");
            System.out.print("---> Inserisci il numero dell'operazione che vuoi effettuare: ");
            return (Integer.parseInt(bf.readLine()));
        } catch (IOException e) {
            System.out.println("ERRORE durante la lettura da tastiera");
            System.exit(-1);
        }

        return -1;
    }

    static public int menuCliente(BufferedReader bf) {
        try {
            System.out.println("\n***MENU CLIENTE ADMIRAL***");
            System.out.println("1. Effettua una nuova prenotazione");
            System.out.println("2. Acquista un pacchetto");
            System.out.println("3. Annulla prenotazione");
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
        String nomeTipoCabina, nomeNave, risposta;
        LocalDate dataPartenza, dataRitorno;
        int mesePartenza, codiceTipoCabina, numeroOspiti, numeroCabina, numeroPrenotazione;

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        int sceltaAttore, scelta;

        admiral.inserisciNuovoItinerario("D8", "N1", "PO2", LocalDate.of(2025, 05, 03), LocalDate.of(2025, 05, 10));
        admiral.inserisciPortoDaVisitare("PO2");
        admiral.inserisciPortoDaVisitare("PO3");
        admiral.inserisciPortoDaVisitare("PO4");
        admiral.inserisciPortoDaVisitare("PO5");
        admiral.confermaInserimento();

        admiral.nuovaPrenotazione();
        admiral.selezionaDestinazione("D1", 5);
        admiral.selezionaItinerario("I1");
        admiral.selezionaTipoCabina(3);
        admiral.inserisciNumeroOspiti(4);
        admiral.confermaPrenotazione();

        while ((sceltaAttore = menuPrincipale(buf)) != 0) {
            switch (sceltaAttore) {
                // Amministratore
                case 1:
                    while ((scelta = menuAmministratore(buf)) != 0) {
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
                                            System.out.println("La data di ritorno deve essere successiva alla data di partenza, riprova.");
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

                                        disponibilitaValida = catalogo.validateDisponibilitaNave(codiceNave,
                                                dataPartenza,
                                                dataRitorno);
                                        if (!disponibilitaValida) {
                                            System.out.println(
                                                    "La nave è già impegnata nelle date selezionate, riprova.");
                                        }

                                    } while (!codiceValido || !disponibilitaValida);

                                    do {
                                        System.out.print("Codice del porto di partenza: ");
                                        codicePortoPartenza = buf.readLine();
                                        if (!catalogo.validateCodicePorto(codicePortoPartenza)) {
                                            System.out.println("Codice porto inesistente, riprova.");
                                        }
                                    } while (!catalogo.validateCodicePorto(codicePortoPartenza));

                                    admiral.inserisciNuovoItinerario(codiceDestinazione, codiceNave,
                                            codicePortoPartenza,
                                            dataPartenza, dataRitorno);

                                    do {
                                        System.out.print(
                                                "Inserire il codice del porto da visitare ('stop' per terminare): ");
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

                            // Modifica itinerario
                            case 2:
                                try {
                                    do {
                                        System.out.print("Inserire il codice dell'itinerario da modificare: ");
                                        codiceItinerario = buf.readLine();
                                        if (!admiral.verificaItinerario(codiceItinerario)) {
                                            System.out.println("Codice itinerario inesistente, riprova.");
                                        }
                                    } while (!admiral.verificaItinerario(codiceItinerario));

                                    do {
                                        System.out.print("Cosa desideri modificare? (date o porti): ");
                                        risposta = buf.readLine();
                                        if (risposta.equalsIgnoreCase("date")) {
                                            dataPartenza = DateValidation.leggiData(buf,
                                                    "Data di partenza (YYYY-MM-DD): ");
                                            do {
                                                dataRitorno = DateValidation.leggiData(buf,
                                                        "Data di ritorno (YYYY-MM-DD): ");
                                                if (dataRitorno.isBefore(dataPartenza)
                                                        || dataRitorno.isEqual(dataPartenza)) {
                                                    System.out.println(
                                                            "La data di ritorno deve essere successiva alla data di partenza, riprova.");
                                                }
                                            } while (dataRitorno.isBefore(dataPartenza)
                                                    || dataRitorno.isEqual(dataPartenza));

                                            admiral.modificaDateItinerario(dataPartenza, dataRitorno);
                                        } else if (risposta.equalsIgnoreCase("porti")) {
                                            admiral.modificaPortiDaVisitare();

                                            do {
                                                System.out.print(
                                                        "Inserire il codice del porto da visitare ('stop' per terminare): ");
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
                                            } while (!catalogo.validateCodicePorto(codicePorto)
                                                    || !codicePorto.equals("stop")
                                                    || !catalogo.validateInserisciPortoDaVisitare());
                                        } else
                                            System.out.println(
                                                    "Modifica non prevista, scegliere tra 'date' o 'porti' \n");
                                    } while (!risposta.equalsIgnoreCase("porti") && !risposta.equalsIgnoreCase("date"));
                                } catch (IOException e) {
                                }
                                break;
                            
                            // Nuova escursione
                            case 3:
                                try {
                                    do{
                                        System.out.print("Inserisci il codice del porto: ");
                                        codicePorto = buf.readLine();

                                        if(admiral.inserisciEscursioneInPorto(codicePorto)){
                                            System.out.print("Inserisci il nome dell'escursione: ");
                                            String nome = buf.readLine();

                                            System.out.print("Inserisci la durata dell'escursione: ");
                                            int durata = Integer.parseInt(buf.readLine());

                                            System.out.print("Inserisci la difficoltà dell'escursione: ");
                                            int difficolta = Integer.parseInt(buf.readLine());

                                            admiral.inserisciEscursione(nome, durata, difficolta);

                                            System.out.print("\nConferma inserimento (si o no): ");
                                            if (buf.readLine().equalsIgnoreCase("si")) {
                                                admiral.confermaInserimentoEscursione();
                                            } else
                                                break;
                                        } else{
                                            System.out.println("Codice porto inesistente, riprova.");
                                        }
                                    } while(!catalogo.validateCodicePorto(codicePorto));
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
                                                numeroCabina = leggiNumeroIntero(buf, "Inserire il numero della cabina (digita '0' per terminare): ");
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

                            // Visualizza itinerari
                            case 5:
                                admiral.visualizzaItinerari();
                                break;

                            // Visualizza destinazioni
                            case 6:
                                catalogo.visualizzaDestinazioni();
                                break;

                            // Visualizza porti
                            case 7:
                                catalogo.visualizzaPorti();
                                break;

                            // Visualizza navi
                            case 8:
                                catalogo.visualizzaNavi();
                                break;

                            // Visualizza prenotazioni
                            case 9:
                                gestorePrenotazioni.visualizzaPrenotazioni();
                                break;
                        }
                    }
                    break;

                // Cliente
                case 2:
                    while ((scelta = menuCliente(buf)) != 0) {
                        switch (scelta) {
                            // Nuova prenotazione
                            case 1:
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
                                        itinerariTrovati = admiral.selezionaDestinazione(codiceDestinazione,
                                                mesePartenza);
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
                                        try {
                                            codiceTipoCabina = Integer.parseInt(buf.readLine());

                                            if (!gestorePrenotazioni.validateSelezionaTipoCabina(codiceTipoCabina)) {
                                                System.out.println("Codice tipo cabina inesistente, riprova.");
                                                continue;
                                            }

                                            if (admiral.selezionaTipoCabina(codiceTipoCabina) == null) {
                                                System.out.println(
                                                        "Il tipo di cabina che hai selezionato è sold out, riprova.");
                                            } else {
                                                break;
                                            }

                                        } catch (NumberFormatException e) {
                                            System.out.println("Codice tipo cabina inesistente, riprova.");
                                        }
                                    } while (true);

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
                            case 2:
                                try {
                                    do {
                                        System.out.print("Inserisci il numero della prenotazione: ");
                                        numeroPrenotazione = Integer.parseInt(buf.readLine());

                                        if (admiral.inserisciNumeroPrenotazione(numeroPrenotazione)) {
                                            gestorePrenotazioni.visualizzaPacchetti();
                                            boolean pacchettoValido;
                                            do {
                                                System.out.print("Inserisci il codice del pacchetto da acquistare: ");
                                                String codicePacchetto = buf.readLine();
                                                pacchettoValido = gestorePrenotazioni
                                                        .validatePacchetto(codicePacchetto);
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

                            // Annulla prenotazione
                            case 3:
                                try {
                                    System.out.print("Inserisci il numero della prenotazione: ");
                                    numeroPrenotazione = Integer.parseInt(buf.readLine());

                                    admiral.annullaPrenotazione(numeroPrenotazione);
                                } catch (IOException e) {
                                }
                                break;
                        }
                    }
                    break;
            }
        }
    }
}