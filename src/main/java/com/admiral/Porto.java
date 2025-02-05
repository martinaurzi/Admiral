package com.admiral;

import java.util.HashMap;
import java.util.Map;

public class Porto {
    
    private String codice;
    private String nome;
    private Escursione escursioneCorrente;
    private Map<String, Escursione> escursioni;

    public Porto(String codice, String nome){
        this.codice = codice;
        this.nome = nome;

        this.escursioni = new HashMap<>();
    }

    public String generaCodiceEscursione(){
        return "E" + String.valueOf(escursioni.size() + 1);
    }

    public void inserisciEscursione(String nome, int durata, int difficolta){
        String codice = generaCodiceEscursione();

        escursioneCorrente = new Escursione(codice, nome, durata, difficolta);
    }

    public void confermaInserimentoEscursione(){
        String codice = escursioneCorrente.getCodice();
        escursioni.put(codice, escursioneCorrente);
    }

    public String getCodice(){
        return this.codice;
    }

    public String getNome(){
        return nome;
    }

    public Escursione getEscursioneCorrente(){
        return escursioneCorrente;
    }

    public Escursione getEscursione(String codice){
        return escursioni.get(codice);
    }

    public Map<String, Escursione> getEscursioni(){
        return this.escursioni;
    }

    public String toString(){
        String s =  "(" + codice + ") " + nome + "\n";

        if(escursioni.size() > 0){
            s += "\t" + "Escursioni:\n\t";

            for (Escursione escursione: escursioni.values()) {
                s += escursione.getNome() + " ";
            }

            s += "\n";
        }

        return s;
    }
}
