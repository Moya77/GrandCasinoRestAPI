package com.casino.grandcasinorestapi.Controllers;

import com.casino.grandcasinorestapi.Models.Cliente;
import com.casino.grandcasinorestapi.Models.messajes;
import com.casino.grandcasinorestapi.Models.usuarios;

import java.util.ArrayList;

public class CasinoController {
DBcontroller database = new DBcontroller();

    public messajes insertarCliente(Cliente cliente){
       return database.saveCliente(cliente);
    }

    public messajes comprobarNombre(String nombre){
        return database.checkName(nombre);
    }

    public messajes loggIn(usuarios user){
        return database.allowUser(user);
    }

    public ArrayList<String> obtenerClientes(){
        return database.getClientes();
    }
}
