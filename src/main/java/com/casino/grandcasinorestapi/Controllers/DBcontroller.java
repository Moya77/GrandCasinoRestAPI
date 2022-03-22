package com.casino.grandcasinorestapi.Controllers;

import com.casino.grandcasinorestapi.Models.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBcontroller {

    public static final String URL = "jdbc:mysql://localhost:3306/grandcasino";
    public static final String USER = "root";
    public static final String CLAVE = "";
    Connection con = null;

    private messajes msj;
    public DBcontroller(){}

    public void openConxion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void closeConexion(){
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public messajes saveCliente(Cliente cliente) {
        msj = new messajes();
        PreparedStatement ps;
        String sql;
        try {
        openConxion();
            sql = "insert into clientes(Nombre, Nacionalidad, Telefono, Edad, Correo, Genero)" +
                    "values ('" + cliente.Nombre.toUpperCase() + "','" + cliente.Nacionalidad.toUpperCase() + "','" + cliente.Telefono +
                    "','" + cliente.Edad + "','" + cliente.Correo + "','"+cliente.Genero+"')";
            ps = con.prepareStatement(sql);
            ps.execute();



        } catch (SQLException e) {
            msj.messaje=e.getMessage();
            msj.state="error";
            closeConexion();
            return msj;
        }
        msj.messaje="El registro se agregó de forma exitosa!";
        msj.state="success";
        closeConexion();
      return msj;
    }

    public messajes checkName(String Name) {
        msj = new messajes();
        PreparedStatement ps;
        String sql;
        try {
            openConxion();
            sql = "select Nombre from clientes where Nombre='"+Name.toUpperCase()+"'";
            ps = con.prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            if (result.next()){
                msj.messaje="Ya existe un usuario registrado con este nombre, aun asi desea agregarlo?";
                msj.state="question";
                closeConexion();
               return msj;
            }

        } catch (SQLException e) {
            msj.messaje=e.getMessage();
            msj.state="error";
            closeConexion();
            return msj;
        }
        msj.messaje="ok";
        msj.state="success";
        closeConexion();
        return msj;
    }


    public messajes allowUser(usuarios user) {
        msj = new messajes();
        PreparedStatement ps;
        String sql;
        try {
                openConxion();
            sql = "select password from usuarios where usuario='"+user.usuario.toUpperCase()+"'";
            ps = con.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                if (result.getString(1).toUpperCase().equals(user.contrasena)) {
                    msj.messaje = "ok";
                    msj.state = "success";
                    closeConexion();
                    return msj;
                }
            }

        } catch (SQLException e) {
            msj.messaje=e.getMessage();
            msj.state="error";
            closeConexion();
            return msj;
        }
        msj.messaje="Usuario o contraseña incorrectos!";
        msj.state="error";
        closeConexion();
        return msj;
    }

    public ArrayList<Cliente> getClientes(){
        PreparedStatement ps;
        String sql;
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        try {
            openConxion();

            sql = "select id_cliente, Nombre from clientes;";
            ps = con.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while(result.next()) {
              int id = result.getInt(1);

                clientes.add(new Cliente(result.getInt(1),result.getString(2),"",""
                ,0,"",""));
            }

        } catch (SQLException e) {

        String error = e.getMessage();
        }
        closeConexion();
       return clientes;
    }

public messajes guardarJugadas(ArrayList<Jugada> jugadas){
    msj = new messajes();
    PreparedStatement ps;
    String sql;
    try {
        openConxion();
        for (Jugada jugada: jugadas) {
            sql = "insert into jugadas(id_cliente, juego, droped, resultado, fecha)" +
                    "values ('" + jugada.idJugador.toUpperCase() + "','" + jugada.Juego.toUpperCase() + "'," + jugada.Drop +
                    "," + jugada.Resultado + ",'"+jugada.Fecha+"')";
            ps = con.prepareStatement(sql);
            ps.execute();
        }


    } catch (SQLException e) {
        msj.messaje=e.getMessage();
        msj.state="error";
        closeConexion();
        return msj;
    }
    msj.messaje="El registro se agregó de forma exitosa!";
    msj.state="success";
    closeConexion();
    return msj;
}
// ************************************generar consolidado por juegos*********************************************

    public ArrayList<Jugada> obtenerJudadasPorFechas(String fechaIni, String fechaFin){

        PreparedStatement ps;
        String sql;
        ArrayList<Jugada> jugadas = new ArrayList<Jugada>();

        try {


            openConxion();

            sql = "select * from jugadas where fecha BETWEEN '"+fechaIni+"' and '"+fechaFin+"';";
            ps = con.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while(result.next()) {
               jugadas.add(new Jugada(result.getString(1),getNameById(Integer.parseInt(result.getString(1))),result.getString(2),
                       result.getInt(3),result.getInt(4),result.getDate(5).toString()));

            }

        } catch (SQLException e) {

            String error = e.getMessage();
        }
        closeConexion();
        return jugadas;
    }

    public String getNameById(int id){
        PreparedStatement ps;
        String sql;
        try {
            openConxion();
            sql = "select Nombre from clientes where id_cliente="+id+";";
            ps = con.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            result.next();
            String resultado =result.getString(1);
            closeConexion();
            return resultado;
        } catch (SQLException e) {

            String error = e.getMessage();
        }

        return "";
    }
}
