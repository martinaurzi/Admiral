package com.admiral;

public class Porto {
    
    private String codice;
    private String nome;

    public Porto(String codice, String nome){
        this.codice = codice;
        this.nome = nome;
    }

    public String getCodice(){
        return this.codice;
    }

    public String toString(){
        return "(" + codice + ") " + nome;
    }

    public String getNome(){
        return nome;
    }
}
