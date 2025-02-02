package com.admiral;

public class Cabina {
    
    private int numeroCabina;
    private TipoCabina tipoCabina;

    public Cabina(int numeroCabina, TipoCabina tipoCabina){
        this.numeroCabina = numeroCabina;
        this.tipoCabina = tipoCabina;
    }

    public int getNumeroCabina(){
        return numeroCabina;
    }

    public float getPrezzo(){
        return tipoCabina.getPrezzo();
    }

    public TipoCabina getTipo(){
        return tipoCabina;
    }

    public String toString() {
        return "\nCabina " + tipoCabina.getNome() + " " + numeroCabina + "\n";
    }
}
