package com.admiral;

public class Nave {
    
    private String codice;
    private String nome;

    public Nave(String codice, String nome){
        this.codice = codice;
        this.nome = nome;
    }

    public String toString(){
        return nome + "(" + codice + ")";
    }
}
