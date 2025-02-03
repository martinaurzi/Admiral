package com.admiral;

public class PacchettoBevande extends Pacchetto {

    public PacchettoBevande(String codice, String nome, float prezzo) {
        super(codice, nome, prezzo);
    }

    @Override
    public String toString() {
        String s = "Pacchetto " + nome + " " + codice + "\n"
                + "Prezzo: " + prezzo + " euro " + "\n";
        return s;
    }
}
