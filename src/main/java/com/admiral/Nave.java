package com.admiral;

import java.util.Map;

public class Nave {
    
    private String codice;
    private String nome;
    private Map<Integer, TipoCabina> tipiCabina;

    public Nave(String codice, String nome){
        this.codice = codice;
        this.nome = nome;
    }

    public Map<Integer, TipoCabina> getTipiCabina(){
        return tipiCabina;
    }

    public TipoCabina selezionaTipoCabina(int idTipoCabina){
        return tipiCabina.get(idTipoCabina);
    }

    public String toString(){
        return nome + "(" + codice + ")";
    }
}
