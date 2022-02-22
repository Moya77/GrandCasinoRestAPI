package com.casino.grandcasinorestapi.Controllers;

import com.casino.grandcasinorestapi.Models.Cliente;
import com.casino.grandcasinorestapi.Models.messajes;

import java.sql.*;

public class DBcontroller {

    public static final String URL = "jdbc:mysql://localhost:3306/grandcasino";
    public static final String USER = "root";
    public static final String CLAVE = "";

    private messajes msj;
    public DBcontroller(){}

    public Connection DBcontroller(int indent) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            String result = "conexion realizada";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return con;
    }

    public messajes saveCliente(Cliente cliente) {
        msj = new messajes();
        PreparedStatement ps;
        String sql;
        try {

            sql = "insert into clientes(Nombre, Nacionalidad, Telefono, Edad, Correo, Genero)" +
                    "values ('" + cliente.Nombre.toUpperCase() + "','" + cliente.Nacionalidad.toUpperCase() + "','" + cliente.Telefono +
                    "','" + cliente.Edad + "','" + cliente.Correo + "','"+cliente.Genero+"')";
            ps = DBcontroller(1).prepareStatement(sql);
            ps.execute();

        } catch (SQLException e) {
            msj.messaje=e.getMessage();
            msj.state="error";
            return msj;
        }
        msj.messaje="El registro se agregó de forma exitosa!";
        msj.state="success";
      return msj;
    }

    public messajes checkName(String Name) {
        msj = new messajes();
        PreparedStatement ps;
        String sql;
        try {

            sql = "select Nombre from clientes where Nombre='"+Name.toUpperCase()+"'";
            ps = DBcontroller(1).prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            if (result.next()){
                msj.messaje="Ya existe un usuario registrado con este nombre, aun asi desea agregarlo?";
                msj.state="question";
               return msj;
            }

        } catch (SQLException e) {
            msj.messaje=e.getMessage();
            msj.state="error";
            return msj;
        }
        msj.messaje="ok";
        msj.state="success";
        return msj;
    }


}
