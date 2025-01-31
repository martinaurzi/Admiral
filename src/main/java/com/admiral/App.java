package com.admiral;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class App {

    static public int menu(BufferedReader bf) {
        try {
            System.out.println("***ADMIRAL***");
            System.out.println("1. Inserire un nuovo Itinerario");
            System.out.println("2. Effettuare una nuova prenotazione");
            System.out.println("3. Aggiungere un pacchetto");
            System.out.println("4. Visualizzare tutti gli itinerari");
            System.out.println("5. Visualizzare tutte le destinazioni");
            System.out.println("6. Visualizzare tutte le navi");
            System.out.println("7. Visualizzare tutti i porti");
            System.out.println("0. Uscita");
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

        String codiceDestinazione, codiceNave, codicePortoPartenza, codicePorto, codiceItinerario;
        LocalDate dataPartenza, dataRitorno;
        int mesePartenza, codiceTipoCabina, numeroOspiti;

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        int scelta;

        System.out.println("Applicazione avviata e Catalogo inizializzato.");

        while ((scelta = menu(buf)) != 0) {
            switch (scelta) {
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
                        dataRitorno = DateValidation.leggiData(buf, "Data di ritorno (YYYY-MM-DD): ");
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

                        System.out.print("Codice del porto di partenza: ");
                        codicePortoPartenza = buf.readLine();
                        admiral.inserisciNuovoItinerario(codiceDestinazione, codiceNave, codicePortoPartenza,
                                dataPartenza, dataRitorno);

                        System.out.println("Inserire i porti da visitare");
                        while (true) {
                            System.out
                                    .print("Inserire il codice del porto da visitare (digita 'stop' per terminare): ");
                            codicePorto = buf.readLine();
                            if (codicePorto.equalsIgnoreCase("stop"))
                                break;
                            admiral.inserisciPortoDaVisitare(codicePorto);
                        }

                        System.out.print("\nConferma inserimento (si o no)");
                        if (buf.readLine().equalsIgnoreCase("si")) {
                            admiral.confermaInserimento();
                        } else
                            break;
                    } catch (IOException e) {
                    }
                    break;

                case 2:
                    admiral.nuovaPrenotazione();

                    try {
                        System.out.println("Seleziona la destinazione");
                        System.out.print("Inserisci il codice della destinazione desiderata: ");
                        codiceDestinazione = buf.readLine();
                        System.out.print("Inserisci il mese di partenza: ");
                        mesePartenza = Integer.parseInt(buf.readLine());
                        admiral.selezionaDestinazione(codiceDestinazione, mesePartenza);

                        System.out.println("Seleziona l' itinerario");
                        System.out.print("Inserisci il codice dell' itinerario scelto: ");
                        codiceItinerario = buf.readLine();
                        admiral.selezionaItinerario(codiceItinerario);

                        System.out.println("Seleziona il tipo di cabina");
                        System.out.print("Inserisci il codice del tipo di cabina scelto: ");
                        codiceTipoCabina = Integer.parseInt(buf.readLine());
                        admiral.selezionaTipoCabina(codiceTipoCabina);

                        System.out.println("Specificare il numero di ospiti");
                        do {
                            System.out.print("Inserire il numero di ospiti (max = 4): ");
                            numeroOspiti = Integer.parseInt(buf.readLine());
                        } while (numeroOspiti > 4);
                        admiral.inserisciNumeroOspiti(numeroOspiti);

                        System.out.print("\nConferma la prenotazione (si o no): ");
                        if (buf.readLine().equalsIgnoreCase("si")) {
                            admiral.confermaPrenotazione();
                        } else
                            break;

                    } catch (IOException e) {
                    }
                    break;

                case 5:
                    catalogo.getDestinazioni();
                    break;
            }
        }
    }
}