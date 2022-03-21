package com.casino.grandcasinorestapi.restServices;

import com.casino.grandcasinorestapi.Controllers.CasinoController;
import com.casino.grandcasinorestapi.Models.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @GetMapping("/getclientes" )
    @ResponseBody
    public ArrayList<Cliente> getClientes(){

        return casino.obtenerClientes();

    }

    @PostMapping("/guardarJugadas" )
    @ResponseBody
    public messajes guardarJugadas(@RequestBody ArrayList<Jugada> jugadas){

    return casino.guardarJugadas(jugadas);
    }

    @GetMapping("/genConsolidado" )
    @ResponseBody
    public HashMap genConsolidado(){

    HashMap resultado = casino.genInformConsolidadoJuegos("2022-03-17","2022-03-18");



        return resultado;

    }
}
