package com.casino.grandcasinorestapi.Models;

public class ConsolidadoJuego {



    public String juego;
    public int dropMesAnte;
    public int resultMesAnte;
    public int dropMesActu;
    public int resultMesActu;

    public ConsolidadoJuego(){}

    public ConsolidadoJuego(String juego,int dropMesAnte,int resultMesAnte, int dropMesActu,int resultMesActu){
        this.juego=juego;
        this.dropMesAnte=dropMesAnte;
        this.resultMesAnte=resultMesAnte;
        this.dropMesActu=dropMesActu;
        this.resultMesActu=resultMesActu;
    }
}
