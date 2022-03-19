package com.casino.grandcasinorestapi.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsolidadoClientesXJue {

    public String Cliente;
    public HashMap consolidadoJuegos;

    public ConsolidadoClientesXJue(){}
    public ConsolidadoClientesXJue(String cliente, HashMap consolidadoJuegos){

        this.Cliente=cliente;
        this.consolidadoJuegos=consolidadoJuegos;
    }
}
