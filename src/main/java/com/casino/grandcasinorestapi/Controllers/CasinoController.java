package com.casino.grandcasinorestapi.Controllers;

import com.casino.grandcasinorestapi.Models.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        ArrayList<Jugada> jugadasAnteriores = database.obtenerJudadasPorFechas(restarMes(fechaIni),restarMes(fechaFin));

        ArrayList<Jugada> jugadasActuales= database.obtenerJudadasPorFechas(fechaIni,fechaFin);

        HashMap clientesConsolidados = new HashMap<>();

        ordenamientoJugadas(true,consolidado, newConsolid, jugadasAnteriores, clientesConsolidados);

        ordenamientoJugadas(false,consolidado, newConsolid, jugadasActuales, clientesConsolidados);

        return clientesConsolidados;
    }

    private void ordenamientoJugadas(boolean isAnterior,ConsolidadoClientesXJueConstruct consolidado, ConsolidadoJuego newConsolid, ArrayList<Jugada> jugadasActuales, HashMap clientesConsolidados) {
        for (Jugada jugada:jugadasActuales) {
            if(clientesConsolidados.containsKey(jugada.idJugador)){

                addConsolidadoJugador(isAnterior,consolidado,clientesConsolidados,jugada,newConsolid);

            }else{
                clientesConsolidados.put(jugada.idJugador,new ConsolidadoClientesXJueConstruct(getNameById(Integer.parseInt(jugada.idJugador)),new HashMap()));
                addConsolidadoJugador(isAnterior,consolidado,clientesConsolidados,jugada,newConsolid);
            }

        }
    }

    public void addConsolidadoJugador(boolean anterior, ConsolidadoClientesXJueConstruct consolidado, HashMap clientesConsolidados, Jugada jugada, ConsolidadoJuego newConsolid) {
        consolidado = (ConsolidadoClientesXJueConstruct) clientesConsolidados.get(jugada.idJugador);
        clientesConsolidados.remove(jugada.idJugador);
        if(consolidado.consolidadoJuegos.containsKey(jugada.Juego)){
            newConsolid =(ConsolidadoJuego) consolidado.consolidadoJuegos.get(jugada.Juego);
            consolidado.consolidadoJuegos.remove(jugada.Juego);
            if(anterior) {
                newConsolid.dropMesActu = newConsolid.dropMesActu + jugada.Drop;
                newConsolid.resultMesActu = newConsolid.resultMesActu + jugada.Resultado;
            }else{
                newConsolid.dropMesAnte = newConsolid.dropMesAnte + jugada.Drop;
                newConsolid.resultMesAnte = newConsolid.resultMesAnte + jugada.Resultado;
            }
            consolidado.consolidadoJuegos.put(jugada.Juego,newConsolid);
        }else{
           if(anterior){
               consolidado.consolidadoJuegos.put(jugada.Juego,new ConsolidadoJuego(jugada.Juego,
                       0,0,jugada.Drop,jugada.Resultado));
           }else{
               consolidado.consolidadoJuegos.put(jugada.Juego,new ConsolidadoJuego(jugada.Juego
                       ,jugada.Drop,jugada.Resultado,0,0));
           }
        }
        clientesConsolidados.put(jugada.idJugador,consolidado);
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
