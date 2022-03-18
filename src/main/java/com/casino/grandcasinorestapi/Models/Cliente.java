package com.casino.grandcasinorestapi.Models;

public class Cliente {

    public Cliente(){}
    public int id_cliente;
    public String Nombre;
    public String Nacionalidad;
    public String Telefono;
    public int Edad;
    public String Correo;
    public String Genero;

    public Cliente( int id_cliente, String Nombre,String Nacionalidad, String Telefono, int Edad, String Correo, String Genero){
        this.id_cliente = id_cliente;
        this.Nombre = Nombre;
        this.Nacionalidad=Nacionalidad;
        this.Telefono = Telefono;
        this.Edad = Edad;
        this.Correo = Correo;
        this.Genero = Genero;
    }
}
