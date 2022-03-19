package com.casino.grandcasinorestapi.Models;

public class Jugada {

    public String idJugador;
    public String Nombre;
    public String Juego;
    public int Drop;
    public int Resultado;
    public String Fecha;

    public Jugada(){}
    public Jugada(String idJugador, String Nombre, String Juego, int Drop, int Resultado, String Fecha){
    this.idJugador = idJugador;
    this.Nombre=Nombre;
    this.Juego = Juego;
    this.Drop=Drop;
    this.Resultado=Resultado;
    this.Fecha=Fecha;
    }

}
