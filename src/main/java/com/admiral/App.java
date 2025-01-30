package com.admiral;

import java.io.*;
import java.time.LocalDate;

public class App {

    static public int menu(BufferedReader bf){
        try {
            System.out.println("1. Inserire un nuovo Itinerario");
            System.out.println("2. Effettuare una nuova prenotazione");
            System.out.println("3. Aggiungere un pacchetto");
            System.out.println("4. Visualizzare tutti gli itinerari");
            System.out.println("5. Uscita");
            System.out.println("---> Inserisci il numero dell'operazione che vuoi effettuare: ");
            return (Integer.parseInt(bf.readLine()));
        } catch (IOException e) {
            System.out.println("ERRORE durante la lettura da tastiera");
            System.exit(-1);
        }
        return -1;
    }

    public static void main(String[] args) {
        Admiral admiral = Admiral.getInstance();
        
        String codiceDestinazione, codiceNave, codicePortoPartenza, codicePorto, codiceItinerario;
        LocalDate dataPartenza, dataRitorno;
        int mesePartenza, codiceTipoCabina, numeroOspiti;

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        int scelta;

        System.out.println("Applicazione avviata e Catalogo inizializzato.");

        while((scelta = menu(buf)) != 5) {
            switch (scelta){
                case 1:
                    System.out.println("Inserire le informazioni dell'itinerario"); // codiceDestinazione, codiceNave, codicePortoPartenza, dataPartenza, dataRitorno): ");
                    System.out.print("Codice della destinazione: ");
                    codiceDestinazione = buf.readLine();
                    System.out.print("Codice della nave: ");
                    codiceNave = buf.readLine();
                    System.out.print("Codice del porto di partenza: ");
                    codicePortoPartenza = buf.readLine();
                    System.out.print("Data di partenza: ");
                    dataPartenza = LocalDate.parse(buf.readLine());
                    System.out.print("Data di ritorno: ");
                    dataRitorno = LocalDate.parse(buf.readLine());
                    admiral.inserisciNuovoItinerario(codiceDestinazione, codiceNave, codicePortoPartenza, dataPartenza, dataRitorno);

                    System.out.println("Inserire i porti da visitare");
                    while(true){
                        System.out.print("Inserire il codice del porto da visitare (digita 'stop' per terminare): ");
                        codicePorto = buf.readLine();
                        if (codicePorto.equalsIgnoreCase("stop")) break;
                        admiral.inserisciPortoDaVisitare(codicePorto);
                    }

                    System.out.print("\nConferma inserimento (si o no)");
                    if (buf.readLine().equalsIgnoreCase("si")){
                        admiral.confermaInserimento();
                    } else break;
                    
                break;
                
                case 2: 
                    admiral.nuovaPrenotazione();

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
                    do{
                    System.out.print("Inserire il numero di ospiti (max = 4): ");
                    numeroOspiti = Integer.parseInt(buf.readLine());
                    }while(numeroOspiti>4);
                    admiral.inserisciNumeroOspiti(numeroOspiti);

                    System.out.print("\nConferma la prenotazione (si o no): ");
                    if (buf.readLine().equalsIgnoreCase("si")){
                        admiral.confermaPrenotazione();
                    } else break;

                break;
            }
        }
    }
}