package com.casino.grandcasinorestapi.Controllers;

import com.casino.grandcasinorestapi.Models.*;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Cliente> obtenerClientes(){
        return database.getClientes();
    }

    public messajes guardarJugadas(ArrayList<Jugada> jugadas){
        return database.guardarJugadas(jugadas);
    }

    public HashMap genInformConsolidadoJuegos(String fechaIni, String fechaFin){
        ConsolidadoClientesXJueConstruct consolidado = null;
        ConsolidadoJuego newConsolid = null;
        ArrayList<Jugada> jugadas = database.obtenerJudadasPorFechas(fechaIni,fechaFin);

        HashMap clientesConsolidados = new HashMap<>();

        for (Jugada jugada:jugadas) {
        if(clientesConsolidados.containsKey(jugada.idJugador)){

            addConsolidadoJugador(consolidado,clientesConsolidados,jugada,newConsolid);

        }else{
            clientesConsolidados.put(jugada.idJugador,new ConsolidadoClientesXJueConstruct(getNameById(Integer.parseInt(jugada.idJugador)),new HashMap()));
            addConsolidadoJugador(consolidado,clientesConsolidados,jugada,newConsolid);
        }

        }
        return clientesConsolidados;
    }

    public void addConsolidadoJugador(ConsolidadoClientesXJueConstruct consolidado, HashMap clientesConsolidados, Jugada jugada, ConsolidadoJuego newConsolid) {
        consolidado = (ConsolidadoClientesXJueConstruct) clientesConsolidados.get(jugada.idJugador);
        clientesConsolidados.remove(jugada.idJugador);
        if(consolidado.consolidadoJuegos.containsKey(jugada.Juego)){
            newConsolid =(ConsolidadoJuego) consolidado.consolidadoJuegos.get(jugada.Juego);
            consolidado.consolidadoJuegos.remove(jugada.Juego);
            newConsolid.dropMesActu = newConsolid.dropMesActu +jugada.Drop;
            newConsolid.resultMesActu = newConsolid.resultMesActu+jugada.Resultado;
            consolidado.consolidadoJuegos.put(jugada.Juego,newConsolid);
        }else{
            consolidado.consolidadoJuegos.put(jugada.Juego,new ConsolidadoJuego(jugada.Juego,
                    0,0,jugada.Drop,jugada.Resultado));
        }
        clientesConsolidados.put(jugada.idJugador,consolidado);
    }

    String getNameById(int id){
       return database.getNameById(id);
    }
}
