package com.admiral;

public class Cabina {
    
    private int numeroCabina;
    private TipoCabina tipoCabina;

    public Cabina(int numeroCabina, TipoCabina tipoCabina){
        this.numeroCabina = numeroCabina;
        this.tipoCabina = tipoCabina;
    }

    public float getPrezzo(){
        return tipoCabina.getPrezzo();
    }
}
