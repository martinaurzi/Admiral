package com.admiral;

public class Escursione {
    
    private String codice;
    private String nome;
    private int durataOre;
    private int difficoltà;

    public Escursione(String codice, String nome, int durataOre, int difficoltà){
        this.codice = codice;
        this.nome = nome;
        this.durataOre = durataOre;
        this.difficoltà = difficoltà;
    }

    public String getCodice(){
        return codice;
    }

    public String getNome(){
        return nome;
    }
}
