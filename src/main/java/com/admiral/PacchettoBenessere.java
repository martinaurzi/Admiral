package com.admiral;

public class PacchettoBenessere extends Pacchetto{

    private int durataOre;

    public PacchettoBenessere(String codice, String nome, float prezzo, int durataOre) {
        super(codice, nome, prezzo);
        this.durataOre = durataOre;
    }
    
    @Override
    public String toString() {
        String s = "Pacchetto " + nome + " " + codice + "\n"
                + "Prezzo: " + prezzo + " euro " + "\n"
                + "Durata Ore: " + durataOre + " ore" + "\n";
        return s;
    }
}
