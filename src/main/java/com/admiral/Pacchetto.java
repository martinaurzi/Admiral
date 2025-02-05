package com.admiral;

public abstract class Pacchetto {

    protected String codice;
    protected String nome;
    protected float prezzo;
    
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
}
