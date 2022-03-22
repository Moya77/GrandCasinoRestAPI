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
    ArrayList<ConsolidadoJuego> temporalList;


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

    @PostMapping("/genConsolidado" )
    @ResponseBody
    public ArrayList<ConsolidadoInforme> genConsolidado(@RequestParam String fechainicio, @RequestParam String fechafinal){

    temporalList = new ArrayList<>();
    ArrayList<ConsolidadoInforme> informe = new ArrayList<>();

    HashMap resultado = casino.genInformConsolidadoJuegos(fechainicio,fechafinal);

        Set<String> keySet = resultado.keySet();
        ArrayList<String> listOfKeys = new ArrayList<String>(keySet);
        Collection<ConsolidadoClientesXJueConstruct> values = resultado.values();

        ArrayList<ConsolidadoClientesXJueConstruct> listOfValues = new ArrayList<ConsolidadoClientesXJueConstruct>(values);

        for(int i=0; i<listOfValues.size();i++){
            ConsolidadoInforme consolidado = new ConsolidadoInforme();

            consolidado.Cliente = listOfValues.get(i).Cliente;

            Set<String> keySet2 = listOfValues.get(i).consolidadoJuegos.keySet();
            ArrayList<String> listOfKeys2 = new ArrayList<String>(keySet2);
            Collection<ConsolidadoJuego> values2 = listOfValues.get(i).consolidadoJuegos.values();
            ArrayList<ConsolidadoJuego> listOfValues2 = new ArrayList<ConsolidadoJuego>(values2);

            consolidado.consolidadoJuegos = listOfValues2;

            informe.add(consolidado);
        }


        return informe;

    }
}
