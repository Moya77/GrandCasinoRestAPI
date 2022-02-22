package com.casino.grandcasinorestapi.Controllers;

import com.casino.grandcasinorestapi.Models.Cliente;
import com.casino.grandcasinorestapi.Models.messajes;

public class CasinoController {
DBcontroller database = new DBcontroller();

    public messajes insertarCliente(Cliente cliente){
       return database.saveCliente(cliente);
    }

    public messajes comprobarNombre(String nombre){
        return database.checkName(nombre);
    }
}
