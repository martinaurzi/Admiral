package com.admiral;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Prenotazione {
    
    private int numeroPrenotazione;
    private int numeroOspiti;
    private float prezzoTotale;
    private Itinerario itinerario;
    private Cabina cabina;
    private ScontoStrategy scontoStrategy;
    private Pacchetto pacchettoCorrente;
    private Map<String, Pacchetto> pacchettiPrenotati;

    public Prenotazione(int numeroPrenotazione){
        this.numeroPrenotazione = numeroPrenotazione;

        this.pacchettiPrenotati = new HashMap<>();
    }

    public void calcolaPrezzo(){
        calcolaPrezzoPrenotazione();
        float sconto = calcolaSconto();
        setPrezzoTotale(this.prezzoTotale - sconto);
    }

    public void setNumeroOspiti(int numeroOspiti){
        this.numeroOspiti = numeroOspiti;
        calcolaPrezzo();
    }

    public void setItinerario(Itinerario i){
        itinerario = i;
    }

    public float calcolaPrezzoPerNumeroOspiti(){
        return itinerario.getPrezzo() * numeroOspiti;
    }

    public void calcolaPrezzoPrenotazione(){
        setPrezzoTotale(calcolaPrezzoPerNumeroOspiti() + cabina.getPrezzo());
    }

    public float calcolaSconto(){
        float sconto = 0;

        if(LocalDate.now().getMonthValue() == (itinerario.getDataPartenza().minusMonths(1).getMonthValue())){
            setScontoStrategy(new ScontoLastMinute());
            sconto = scontoStrategy.calcolaSconto(this.prezzoTotale);
        } 
        else if(itinerario.getDataPartenza().getMonthValue() == 9){
            setScontoStrategy(new ScontoMaxImporto());
            sconto = scontoStrategy.calcolaSconto(this.prezzoTotale);
        }

        return sconto;
    }

    public void confermaAcquisto(){
        String codicePacchetto = pacchettoCorrente.getCodice();
        addPacchetto(codicePacchetto);
        System.out.println("Pacchetto Acquistato");
    }

    public boolean checkIfSettembre(){
        return itinerario.getDataPartenza().getMonthValue() == 9;
    }

    public void addPacchetto(String codicePacchetto){
        if(!pacchettiPrenotati.containsKey(codicePacchetto))
            pacchettiPrenotati.put(codicePacchetto, pacchettoCorrente);
        else
            System.out.println("Pacchetto già acquistato");

        // Regola di dominio R2
        if((pacchettoCorrente.getNome() != "Bevande" || pacchettoCorrente.getNome() != "Bevande Plus") && !checkIfSettembre()){
            float prezzo = pacchettoCorrente.getPrezzo();
            aggiornaPrezzo(prezzo);
        }
    }

    public void aggiornaPrezzo(float prezzo){
        setPrezzoTotale(this.prezzoTotale += prezzo);
    }

    public Itinerario getItinerario(){
        return itinerario;
    }

    public Cabina getCabina(){
        return cabina;
    }

    public int getNumero(){
        return this.numeroPrenotazione;
    }

    public float getPrezzoTotale(){
        return prezzoTotale;
    }

    public Pacchetto getPacchettoCorrente(){
        return pacchettoCorrente;
    }

    public Map<String, Pacchetto> getPacchetti(){
        return pacchettiPrenotati;
    }

    public void setPrezzoTotale(float prezzoTotale){
        this.prezzoTotale = prezzoTotale;
    }

    public void setScontoStrategy(ScontoStrategy scontoStrategy){
        this.scontoStrategy = scontoStrategy;
    }

    public void setCabina(Cabina cabina){
        this.cabina = cabina;
    }

    public void setPacchettoCorrente(Pacchetto pa){
        this.pacchettoCorrente = pa;
    }

    public String toString() {
        String s = "\nPrenotazione " + numeroPrenotazione + "\n"
                + "\t" + cabina.getTipo().getNome() + " " + cabina.getNumeroCabina() + "\n"
                + "\t" + "Data di partenza: " + itinerario.getDataPartenza() + "\n"
                + "\t" + "Prezzo totale: " + prezzoTotale + " euro" + "\n"
                + "\t" + "Pacchetti:\n\t";
                for (Pacchetto pacchetto : pacchettiPrenotati.values()) {
                    s += pacchetto.getNome() + " ";
                };
        return s;
    }
}