package com.casino.grandcasinorestapi.Models;

import java.util.HashMap;

public class ConsolidadoClientesXJueConstruct {

    public String Cliente;
    public HashMap consolidadoJuegos;

    public ConsolidadoClientesXJueConstruct(){}
    public ConsolidadoClientesXJueConstruct(String cliente, HashMap consolidadoJuegos){

        this.Cliente=cliente;
        this.consolidadoJuegos=consolidadoJuegos;
    }
}
