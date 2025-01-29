package com.admiral;

public class Admiral {

    private static Admiral admiral;
    private Catalogo catalogo;

    private Admiral() {
        this.catalogo = Catalogo.getInstance();
    }

    public static Admiral getInstance() {
        if (admiral == null)
            admiral = new Admiral();
        else
            System.out.println("Istanza già creata");

        return admiral;
    }
}