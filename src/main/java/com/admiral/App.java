package com.admiral;

public class App {

    public static void main(String[] args) {
        Admiral admiral = Admiral.getInstance();
        
        admiral.loadDestinazioni();
        admiral.loadPorti();
        admiral.loadNavi();
    }
}