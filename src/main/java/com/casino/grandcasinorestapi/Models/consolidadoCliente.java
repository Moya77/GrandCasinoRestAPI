package com.casino.grandcasinorestapi.Models;

public class consolidadoCliente {

    public String cliente;
    public int dropActual;
    public int resultadoActual;
    public int dropAnterior;
    public int resultadoAnterior;

    public consolidadoCliente(String cliente, int drop, int resultado, int dropAnterior, int resultadoAnterior) {
        this.cliente = cliente;
        this.dropActual = drop;
        this.resultadoActual = resultado;
        this.dropAnterior = dropAnterior;
        this.resultadoAnterior = resultadoAnterior;
    }




    public consolidadoCliente(){}
}
