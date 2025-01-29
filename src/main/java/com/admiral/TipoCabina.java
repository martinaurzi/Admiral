package com.admiral;

import java.util.HashMap;
import java.util.Map;

public class TipoCabina {
    
    private int id;
    private String nome;
    private float prezzo;
    private Map<Integer, Cabina> cabine;

    public TipoCabina(int id, String nome, float prezzo){
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;

        this.cabine = new HashMap<>();
    }

    public float getPrezzo(){
        return prezzo;
    }
}
