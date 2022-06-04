package com.casino.grandcasinorestapi.Controllers;

import com.casino.grandcasinorestapi.Models.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

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
        ArrayList<Jugada> jugadasAnteriores = database.obtenerJudadasPorFechas(restarMes(fechaIni),restarMes(fechaFin));

        ArrayList<Jugada> jugadasActuales= database.obtenerJudadasPorFechas(fechaIni,fechaFin);



        HashMap clientesConsolidados = new HashMap<>();

        ordenamientoJugadas(true,false,consolidado, newConsolid, jugadasAnteriores, clientesConsolidados);

        ordenamientoJugadas(false,true,consolidado, newConsolid, jugadasActuales, clientesConsolidados);

        ordenamientoJugadas(true, true ,consolidado, newConsolid, jugadasAnteriores, clientesConsolidados);
        ordenamientoJugadas(true, true ,consolidado, newConsolid, jugadasActuales, clientesConsolidados);

        return clientesConsolidados;
    }

    public ArrayList<consolidadoCliente> genInformConsolidadoClientes(String fechaIni, String fechaFin){
        ArrayList<consolidadoCliente> informe;
     ArrayList<Jugada> jugadasActuales = database.obtenerJudadasPorFechas(fechaIni,fechaFin);
        ArrayList<Jugada> jugadasAnterior = database.obtenerJudadasPorFechas(restarMes(fechaIni),restarMes(fechaFin));


     HashMap consolidadoClientes = new HashMap<>();
     addConsolidadoJugador(true,false,consolidadoClientes,jugadasActuales);
     addConsolidadoJugador(false,true,consolidadoClientes,jugadasAnterior);

        Set<String> keySet = consolidadoClientes.keySet();
        ArrayList<String> listOfKeys = new ArrayList<String>(keySet);
        Collection<consolidadoCliente> values = consolidadoClientes.values();

        informe =  new ArrayList<>(values);
        return informe;

    }

    private void ordenamientoJugadas(boolean isAnterior, boolean isActual,ConsolidadoClientesXJueConstruct consolidado, ConsolidadoJuego newConsolid, ArrayList<Jugada> jugadasActuales, HashMap clientesConsolidados) {
        for (Jugada jugada:jugadasActuales) {
            if(clientesConsolidados.containsKey(jugada.idJugador)){

                addConsolidadoJugadorXjuego(isAnterior,isActual,consolidado,clientesConsolidados,jugada,newConsolid);

            }else{
                clientesConsolidados.put(jugada.idJugador,new ConsolidadoClientesXJueConstruct(getNameById(Integer.parseInt(jugada.idJugador)),new HashMap()));
                addConsolidadoJugadorXjuego(isAnterior,isActual,consolidado,clientesConsolidados,jugada,newConsolid);
            }

        }
    }

    public void addConsolidadoJugadorXjuego(boolean anterior, boolean actual, ConsolidadoClientesXJueConstruct consolidado, HashMap clientesConsolidados, Jugada jugada, ConsolidadoJuego newConsolid) {
        consolidado = (ConsolidadoClientesXJueConstruct) clientesConsolidados.get(jugada.idJugador);
        clientesConsolidados.remove(jugada.idJugador);
        if(consolidado.consolidadoJuegos.containsKey(jugada.Juego)){
            newConsolid =(ConsolidadoJuego) consolidado.consolidadoJuegos.get(jugada.Juego);
            consolidado.consolidadoJuegos.remove(jugada.Juego);
            if(anterior && !actual) {
                newConsolid.dropMesActu = newConsolid.dropMesActu + jugada.Drop;
                newConsolid.resultMesActu = newConsolid.resultMesActu + jugada.Resultado;
            }else if (actual && !anterior){
                newConsolid.dropMesAnte = newConsolid.dropMesAnte + jugada.Drop;
                newConsolid.resultMesAnte = newConsolid.resultMesAnte + jugada.Resultado;
            }else if (anterior && actual){
                newConsolid.dropGlobal = newConsolid.dropGlobal + jugada.Drop;
                newConsolid.resultadoGlobal = newConsolid.resultadoGlobal + jugada.Resultado;
            }
            consolidado.consolidadoJuegos.put(jugada.Juego,newConsolid);
        }else{
           if(anterior && !actual){
               consolidado.consolidadoJuegos.put(jugada.Juego,new ConsolidadoJuego(jugada.Juego,
                       0,0,jugada.Drop,jugada.Resultado,0,0));
           }else if(actual && !anterior){
               consolidado.consolidadoJuegos.put(jugada.Juego,new ConsolidadoJuego(jugada.Juego
                       ,jugada.Drop,jugada.Resultado,0,0,0,0));
           }else if(actual && anterior){
               consolidado.consolidadoJuegos.put(jugada.Juego,new ConsolidadoJuego(jugada.Juego
                       ,0,0,0,0,jugada.Drop,jugada.Resultado));
           }
        }
        clientesConsolidados.put(jugada.idJugador,consolidado);
    }

    public void addConsolidadoJugador(boolean isActual, boolean isAnterior,HashMap clientesConsolidados, ArrayList<Jugada> jugadas) {
        consolidadoCliente consolidado;
        for (Jugada jugada: jugadas) {

            if(clientesConsolidados.containsKey(jugada.idJugador)){
                consolidado =(consolidadoCliente) clientesConsolidados.get(jugada.idJugador);
                clientesConsolidados.remove(jugada.idJugador);
                if (isActual) {
                    consolidado.dropActual = consolidado.dropActual + jugada.Drop;
                    consolidado.resultadoActual = consolidado.resultadoActual + jugada.Resultado;
                }else if(isAnterior){
                    consolidado.dropAnterior = consolidado.dropAnterior + jugada.Drop;
                    consolidado.resultadoAnterior = consolidado.resultadoAnterior + jugada.Resultado;
                }
                clientesConsolidados.put(jugada.idJugador,consolidado);
            }else{
                clientesConsolidados.put(jugada.idJugador, new consolidadoCliente(jugada.Nombre,jugada.Drop,jugada.Resultado,0,0));
            }
        }

    }



    String getNameById(int id){
       return database.getNameById(id);
    }

    public String restarMes(String date) {
       String[] parts = date.split("-");
        String fecha;
       int mes = Integer.parseInt(parts[1]);
        int anio = Integer.parseInt(parts[0]);
        if(mes==01){
            mes = 12;
            anio=anio-1;
        }else{
            mes = mes-1;
        }
        if(mes == 1 || mes == 2 || mes == 3 || mes == 4 ||mes == 5 ||mes == 6 ||mes == 7 ||
                mes == 8 ||mes == 9 ){
            fecha = parts[0]+"-0"+mes+"-"+parts[2];
        }else {
            fecha = anio + "-" + mes + "-" + parts[2];
        }
        return fecha;
    }
}
