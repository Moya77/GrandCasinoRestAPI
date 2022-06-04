package com.casino.grandcasinorestapi.Models;

public class ConsolidadoJuego {



    public String Cliente;
    public String Juego;
    public int dropMesAnte;
    public int resultMesAnte;
    public int dropMesActu;
    public int resultMesActu;
    public int dropGlobal;
    public int resultadoGlobal;

    public ConsolidadoJuego(){}

    public ConsolidadoJuego( String juego,int dropMesAnte,int resultMesAnte, int dropMesActu,int resultMesActu, int dropGlobal, int resultadoGlobal){
        this.Juego =juego;
        this.dropMesAnte=dropMesAnte;
        this.resultMesAnte=resultMesAnte;
        this.dropMesActu=dropMesActu;
        this.resultMesActu=resultMesActu;
        this.dropGlobal = dropGlobal;
        this.resultadoGlobal=resultadoGlobal;
    }
}
