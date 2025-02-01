package com.admiral;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidation {
    public static LocalDate leggiData(BufferedReader buf, String messaggio) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = null;

        while (data == null) {
            try {
                System.out.print(messaggio);
                String input = buf.readLine();

                data = LocalDate.parse(input, formatter);

                if (data.isBefore(LocalDate.now())) {
                    System.out.println("Inserisci una data successiva ad oggi, riprova.");
                    data = null;
                }

            } catch (IOException e) {
                System.out.println("Errore durante la lettura dell'input, riprova.");
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido. Usa il formato YYYY-MM-DD.");
            }
        }

        return data;
    }
}
