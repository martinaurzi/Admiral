package com.admiral;

public class ScontoMaxImporto extends ScontoStrategy{

    @Override
    float calcolaSconto(float prezzoTotale) {
        if(prezzoTotale >= 5000F)
            return prezzoTotale - 200F;
        
        return 0;
    }
}
