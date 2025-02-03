package com.admiral;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Nave {
    
    private String codice;
    private String nome;
    private Map<Integer, TipoCabina> tipiCabina;
    private TipoCabina tipoCabinaCorrente;

    public Nave(String codice, String nome){
        this.codice = codice;
        this.nome = nome;

        this.tipiCabina = new HashMap<>();

        loadTipiCabina();
    }

    public Nave(String codice, String nome, boolean caricaTipiCabina){
        this.codice = codice;
        this.nome = nome;

        this.tipiCabina = new HashMap<>();

        if(caricaTipiCabina){
            loadTipiCabina();
        }
    }

    public int generaCodiceTipoCabina() {
        return tipiCabina.size() + 1;
    }

    public void inserisciTipoCabina(String nomeTipoCabina){
        if(!nomeTipoCabina.equalsIgnoreCase("Cabina interna") 
        || !nomeTipoCabina.equalsIgnoreCase("Cabina vista mare")
        || !nomeTipoCabina.equalsIgnoreCase("Cabina con balcone")
        || !nomeTipoCabina.equalsIgnoreCase("Suite"))
        System.out.println("Tipo di cabina non previsto");

        int codice = generaCodiceTipoCabina();

        TipoCabina tipoCabinaCorrente = new TipoCabina(codice, nomeTipoCabina);
        this.tipiCabina.put(codice, tipoCabinaCorrente);
    } 

    public void inserisciCabina(int numeroCabina){
        tipoCabinaCorrente.inserisciCabina(numeroCabina);
    } 

    public void loadTipiCabina(){
        ObjectMapper tipiCabinaMapper = new ObjectMapper();

        try {
            JsonNode tipiCabinaNode = tipiCabinaMapper.readTree(new File("src/main/java/com/admiral/data/tipiCabina.json"));

            for (JsonNode node : tipiCabinaNode) {
                int id = node.get("id").intValue();
                String nome = node.get("nome").asText();
                float prezzo = node.get("prezzo").floatValue();
    
                TipoCabina tc = new TipoCabina(id, nome, prezzo);
                //tc.setNave(this);

                this.tipiCabina.put(id, tc);
            }

            System.out.println("Caricamento Tipi Cabina Completato");
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file tipiCabina.json");
            e.printStackTrace();
        }
    }

    public Map<Integer, TipoCabina> getTipiCabina(){
        return tipiCabina;
    }

    public TipoCabina selezionaTipoCabina(int idTipoCabina){
        return tipiCabina.get(idTipoCabina);
    }

    public String getNome(){
        return nome;
    }

    public String getCodice(){
        return codice;
    }

    public String toString(){
        return "(" + codice + ") " + nome;
    }
}
