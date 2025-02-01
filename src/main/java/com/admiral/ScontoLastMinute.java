package com.admiral;

public class ScontoLastMinute extends ScontoStrategy{

    public float calcolaSconto(float prezzoTotale) {
        return prezzoTotale * 0.2F;
    }
    
}
