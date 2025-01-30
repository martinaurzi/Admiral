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

    public void rimuoviCabina(Cabina cabina){
        cabine.remove(cabina.getNumeroCabina());
    }

    public Cabina getCabinaDisponibile(){
        // Implementare eccezione Cabine sold out
        if(!cabine.isEmpty()){
            return cabine.values().iterator().next();
        }

        return null;
    }

    public int getId(){
        return id;
    }

    public float getPrezzo(){
        return prezzo;
    }

    public String toString() {
        String s = "Tipo Cabina " + id + "\n"
                + "Nome: " + nome + "\n"
                + "Prezzo: " + prezzo + "\n";
                
        return s;
    }
}
