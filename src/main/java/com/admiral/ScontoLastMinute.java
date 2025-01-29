package com.admiral;

public class ScontoLastMinute extends ScontoStrategy{

    public float calcolaSconto(float prezzoTotale) {
        /* Se la crociera si 
            svolge entro il mese 
            successivo al mese 
            della prenotazione 
            viene indicata come 
            crociera “Last 
            minute” e viene 
            applicato uno 
            sconto del 20% sul 
            prezzo della 
            prenotazione */
            return prezzoTotale;
        
    }
    
}
