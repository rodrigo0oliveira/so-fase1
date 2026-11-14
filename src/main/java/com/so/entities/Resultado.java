package com.so.entities;

public class Resultado {

    private final String algoritmo;
    private final String ordem;
    private final double esperaMedia;
    private final int trocas;

    public Resultado(String algoritmo, String ordem, double esperaMedia, int trocas) {
        this.algoritmo = algoritmo;
        this.ordem = ordem;
        this.esperaMedia = esperaMedia;
        this.trocas = trocas;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public String getOrdem() {
        return ordem;
    }

    public double getEsperaMedia() {
        return esperaMedia;
    }

    public int getTrocas() {
        return trocas;
    }
}