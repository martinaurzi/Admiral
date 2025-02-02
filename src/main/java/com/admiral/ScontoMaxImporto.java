package com.admiral;

public class ScontoMaxImporto extends ScontoStrategy{

    @Override
    float calcolaSconto(float prezzoTotale) {
        if(prezzoTotale >= 5000)
            return prezzoTotale - 200F;
        
        return 0;
    }
}
