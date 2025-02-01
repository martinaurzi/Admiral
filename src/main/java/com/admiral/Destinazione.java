package com.admiral;

public class Destinazione {
    
    private String codice;
    private String nome;
    private Float prezzo;

    public Destinazione(String codice, String nome, Float prezzo){
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

    public String toString(){
        return nome + "(" + codice + ")";
    }
}
