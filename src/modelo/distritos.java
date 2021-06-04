/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Karen
 */
public class distritos {

    private IntegerProperty iddistritos;
    private StringProperty nombreDistritos;

    public distritos(int iddistritos, String nombreDistritos) {
        this.iddistritos = new SimpleIntegerProperty(iddistritos);
        this.nombreDistritos = new SimpleStringProperty(nombreDistritos);
    }
    //Metodos:iddistritos

    public int getiddistritos() {
        return iddistritos.get();
    }

    public void setiddistritos(int iddistritos) {
        this.iddistritos = new SimpleIntegerProperty(iddistritos);
    }

    public IntegerProperty iddistritosProperty() {
        return iddistritos;
    }
    // Metodos:nombreDistritos

    public String getnombreDistritos() {
        return nombreDistritos.get();
    }

    public void setnombreDistritos(String nombreDistritos) {
        this.nombreDistritos = new SimpleStringProperty(nombreDistritos);
    }

    public StringProperty nombreDistritosProperty() {
        return nombreDistritos;
    }

    public static void llenarInformacion(Connection connection, ObservableList<distritos> lista) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("select iddistritos, nombreDistritos FROM distritos;"
            );
            while (resultado.next()) {
                lista.add(new distritos(
                        resultado.getInt("iddistritos"),
                        resultado.getString("nombreDistritos")
                )
                );
            }
           } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 /*   public int filtrarUbicacion(Connection connection){
    try {
        PreparedStatement instruccion = connection.prepareStatement(
                "SELECT nombreIglesias FROM iglesias WHERE iddistritos_distritos = ?"
            );
        instruccion.setInt(1,iddistritos.get());
        return instruccion.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}*/
    @Override
    public String toString(){
        return nombreDistritos.get();
    }
}
