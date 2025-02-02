package com.admiral;

public class PacchettoBenessere extends Pacchetto{

    private int durataOre;

    public PacchettoBenessere(String codice, String nome, float prezzo, int durataOre) {
        super(codice, nome, prezzo);
        this.durataOre = durataOre;
    }
    
}
