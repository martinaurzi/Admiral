package com.admiral;

public class PacchettoEscursioni extends Pacchetto{

    private int numeroEscursioni;
    private boolean includeGuida;

    public PacchettoEscursioni(String codice, String nome, float prezzo,int numeroEscursioni, boolean includeGuida) {
        super(codice, nome, prezzo);
        this.numeroEscursioni = numeroEscursioni;
        this.includeGuida = includeGuida;
    }
    
    @Override
    public String toString() {
        String s = "Pacchetto " + nome + " " + codice + "\n"
                + "Prezzo: " + prezzo + " euro " + "\n"
                + "Numero Escursioni: " + numeroEscursioni + "\n"
                + "Guida: " + (includeGuida ? "inclusa" : "non inclusa") + "\n";
        return s;
    }
}
