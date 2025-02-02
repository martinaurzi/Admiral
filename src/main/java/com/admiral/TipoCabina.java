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

        loadCabine();
    }

    public void loadCabine(){
        Cabina c1 = new Cabina(1, this);
        Cabina c2 = new Cabina(2, this);
        Cabina c3 = new Cabina(3, this);
        Cabina c4 = new Cabina(4, this);
        this.cabine.put(1, c1);
        this.cabine.put(2, c2);
        this.cabine.put(3, c3);
        this.cabine.put(4, c4);
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

    public String getNome(){
        return nome;
    }

    public String toString() {
        String s = "Tipo Cabina " + id + "\n"
                + "Nome: " + nome + "\n"
                + "Prezzo: " + prezzo + "\n";
                
        return s;
    }
}
