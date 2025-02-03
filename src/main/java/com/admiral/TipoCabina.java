package com.admiral;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public TipoCabina(int id, String nome){
        this.id = id;
        this.nome = nome;
        
        if (nome.equalsIgnoreCase("Cabina interna")) this.prezzo = 0F;
        if (nome.equalsIgnoreCase("Cabina vista mare")) this.prezzo = 200F ;
        if (nome.equalsIgnoreCase("Cabina con balcone")) this.prezzo = 300F;
        if (nome.equalsIgnoreCase("Suite")) this.prezzo = 500F;
    
        this.cabine = new HashMap<>();
    }

    public void inserisciCabina(int numeroCabina){
        Cabina c = new Cabina(numeroCabina, this);

        this.cabine.put(numeroCabina, c);
    } 

    public void loadCabine(){
        ObjectMapper cabineMapper = new ObjectMapper();

        try {
            JsonNode cabineNode = cabineMapper.readTree(new File("src/main/java/com/admiral/data/cabine.json"));

            for (JsonNode node : cabineNode) {
                int numeroCabina = node.get("numeroCabina").intValue();

                int idTipoCabina = node.get("tipoCabina").get("id").intValue();
    
                Cabina c = new Cabina(numeroCabina, this);

                if(this.id == idTipoCabina)
                    this.cabine.put(numeroCabina, c);
            }
            System.out.println("Caricamento Cabine Completato");  
        } catch (IOException e) {
                System.out.println("Errore nella lettura del file cabine.json");
                e.printStackTrace();
            }
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

    public Map<Integer, Cabina> getCabine(){
        return cabine;
    }

    public String toString() {
        String s = "Tipo Cabina " + id + "\n"
                + "Nome: " + nome + "\n"
                + "Prezzo: " + prezzo + "\n";
                
        return s;
    }
}
