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
public class iglesias {

    private IntegerProperty idiglesias;
    private StringProperty nombreIglesias;
    private StringProperty reseña;
    private StringProperty calle;
    private distritos distritos;

    public iglesias(Integer idiglesias,
            String nombreIglesias,
            String reseña,
            String calle,
            distritos distritos) {

        this.idiglesias = new SimpleIntegerProperty(idiglesias);
        this.nombreIglesias = new SimpleStringProperty(nombreIglesias);
        this.reseña = new SimpleStringProperty(reseña);
        this.calle = new SimpleStringProperty(calle);
        this.distritos = distritos;

    }
    //metodos idiglesias

    public int getidiglesias() {
        return idiglesias.get();
    }

    public void setidiglesia(int idiglesias) {
        this.idiglesias = new SimpleIntegerProperty(idiglesias);
    }

    public IntegerProperty idiglesiaProperty() {
        return idiglesias;
    }
    //metodos nombreIglesias

    public String getnombreIglesias() {
        return nombreIglesias.get();
    }

    public void setnombreIglesias(String nombreIglesias) {
        this.nombreIglesias = new SimpleStringProperty(nombreIglesias);
    }

    public StringProperty nombreIglesiasProperty() {
        return nombreIglesias;
    }
    //metodos distritos

    public distritos getdistritos() {
        return distritos;
    }

    public void setdistritos(distritos distritos) {
        this.distritos = distritos;
    }
    //metodos reseña

    public String getreseña() {
        return reseña.get();
    }

    public void setreseña(String reseña) {
        this.reseña = new SimpleStringProperty(reseña);
    }

    public StringProperty reseñaProperty() {
        return reseña;
    }
    //metodos calle

    public String getcalle() {
        return calle.get();
    }

    public void setcalle(String calle) {
        this.calle = new SimpleStringProperty(calle);
    }

    public StringProperty calleProperty() {
        return calle;
    }

    //metodo guardar
    public int guardarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion
                   = connection.prepareStatement("INSERT INTO `iglesias`.`iglesias`"
                            + " (`nombreIglesias`,`iddistritos_distritos`, `reseña`,`calle`)\n"
                            + "VALUES (?,?,?,?);");           
   
        //    instruccion.setInt(1,idiglesias.get());
            instruccion.setString(1,nombreIglesias.get());
            instruccion.setInt(2, distritos.getiddistritos());
            instruccion.setString(3,reseña.get());
            instruccion.setString(4,calle.get());
            
            return instruccion.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int actualizarRegistro(Connection connection){
        try {
            PreparedStatement instruccion = 
                    connection.prepareStatement (
            "UPDATE `iglesias`.`iglesias`"
            + "SET nombreIglesias= ?, "
            + "reseña= ?, "
            + "calle= ?,"
            + "iddistritos_distritos= ? "
            + "WHERE idiglesias= ?"
            );
            instruccion.setString(1, nombreIglesias.get());
            instruccion.setString(2,reseña.get());
            instruccion.setString(3, calle.get());
            instruccion.setInt(4, distritos.getiddistritos());
            instruccion.setInt(5, idiglesias.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int eliminarRegistro(Connection connection){
    try {
        PreparedStatement instruccion = connection.prepareStatement(
         "DELETE FROM `iglesias`.`iglesias` WHERE (`idiglesias` = ?)"
        );
        instruccion.setInt(1,idiglesias.get());
        return instruccion.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
    

    public static void llenarInformacionIglesias(Connection connection, ObservableList<iglesias> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery("SELECT\n"
                    + "i.idiglesias AS idiglesias,\n"
                    + "i.nombreIglesias AS nombreIglesias, \n"
                    + "i.iddistritos_distritos AS iddistritos_distritos,\n"
                    + "i.reseña AS reseña,\n"
                    + "i.calle AS calle,\n"
                    + "d.iddistritos as iddistritos, \n"
                    + "d.nombreDistritos as nombreDistritos     \n"
                    + "FROM iglesias AS i\n"
                    + "INNER JOIN distritos AS d ON i.iddistritos_distritos= d.iddistritos;"
            );
            //recorrer resultset
            while (resultado.next()) {
                lista.add(
                        new iglesias(
                                resultado.getInt("idiglesias"),
                                resultado.getString("nombreIglesias"),
                                resultado.getString("reseña"),
                                resultado.getString("calle"),
                                //instancia
                                new distritos(resultado.getInt("iddistritos"),
                                        resultado.getString("nombreDistritos"))
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
