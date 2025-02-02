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

    public String getCodice(){
        return codice;
    }

    public float getPrezzo(){
        return prezzo;
    }

    public String getNome(){
        return nome;
    }

    public String toString() {
        String s = "Pacchetto " + nome + " " + codice + "\n"
                + "Prezzo: " + prezzo + " euro " + "\n";
        return s;
    }
}
