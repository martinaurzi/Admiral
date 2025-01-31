package com.admiral;

import java.util.HashMap;
import java.util.Map;

public class Nave {
    
    private String codice;
    private String nome;
    private Map<Integer, TipoCabina> tipiCabina;

    public Nave(String codice, String nome){
        this.codice = codice;
        this.nome = nome;

        this.tipiCabina = new HashMap<>();

        loadTipiCabina();
    }

    public void loadTipiCabina(){
        TipoCabina tc1 = new TipoCabina(1, "Cabina interna", 0);
        TipoCabina tc2 = new TipoCabina(2, "Cabina vista mare", 200F);
        TipoCabina tc3 = new TipoCabina(3, "Cabina con balcone", 300F);
        TipoCabina tc4 = new TipoCabina(3, "Suite", 500F);
        this.tipiCabina.put(1, tc1);
        this.tipiCabina.put(2, tc2);
        this.tipiCabina.put(3, tc3);
        this.tipiCabina.put(4, tc4);
        System.out.println("Caricamento TipiCabina in Nave Completato");
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
