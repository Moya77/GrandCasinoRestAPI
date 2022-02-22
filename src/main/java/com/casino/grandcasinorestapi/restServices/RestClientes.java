package com.casino.grandcasinorestapi.restServices;

import com.casino.grandcasinorestapi.Controllers.CasinoController;
import com.casino.grandcasinorestapi.Models.Cliente;
import com.casino.grandcasinorestapi.Models.messajes;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class RestClientes {

    CasinoController casino = new CasinoController();


@GetMapping("/getName")
    public List<Object> getName(){
    List<Object> datos = new ArrayList<>();
    datos.add("nombre1");
    datos.add("nombre");
        return datos;
    }

    @PostMapping("/setDatos" )
    @ResponseBody
    public messajes setData(@RequestBody Cliente cliente){

        return   casino.insertarCliente(cliente);

    }

    @PostMapping("/check" )
    @ResponseBody
    public messajes check(@RequestBody Cliente cliente){

        return casino.comprobarNombre(cliente.Nombre);
    }
}
