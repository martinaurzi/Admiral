package com.admiral;

public abstract class Pacchetto {

    private String codice;
    private String nome;
    private float prezzo;
    
    public Pacchetto(String codice, String nome, float prezzo){
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public String toString() {
        String s = "Pacchetto " + codice + "\n"
                + "Nome: " + nome + "\n"
                + "Prezzo: " + prezzo + " euro " + "\n";
        return s;
    }
}
