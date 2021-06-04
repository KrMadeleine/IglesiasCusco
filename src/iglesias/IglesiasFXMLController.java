/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iglesias;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import modelo.Conexion;
import modelo.distritos;

import modelo.iglesias;


public class IglesiasFXMLController implements Initializable {

    @FXML
    private JFXComboBox<distritos> cmbUbicacion;
    @FXML
    private JFXComboBox<distritos> cmbDistrito;
    @FXML
    private JFXTextField Nombretxt;
    @FXML
    private JFXTextField Calletxt;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnActualizar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextArea Reseñatxt;
    @FXML
    private TableView<iglesias> tblIglesias;
    @FXML
    private TableColumn<iglesias, Integer> clmNumero;
    @FXML
    private TableColumn<iglesias, String> clmIglesias;

    IglesiasFXMLController FXMLDocumentController;
      
    //Colecciones
    private ObservableList<iglesias> listaiglesias;
    private ObservableList<distritos> listadistritos;
    
    private Conexion conexion;

    @FXML
    private JFXTextField idiglesiatxt;
   
       
    @Override
  
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        conexion = new Conexion();
        conexion.establecerConexion();
        
       // FXMLDocumentController = this;
        
        //inicializar listas
        listaiglesias = FXCollections.observableArrayList();
        listadistritos = FXCollections.observableArrayList();
        
        //llenar listas
        iglesias.llenarInformacionIglesias(conexion.getConnection(), listaiglesias);
        distritos.llenarInformacion(conexion.getConnection(), listadistritos);
       
        //Enlazar tabla
        tblIglesias.setItems(listaiglesias);
        clmNumero.setCellValueFactory(new PropertyValueFactory<iglesias,Integer>("idiglesias"));
        clmIglesias.setCellValueFactory(new PropertyValueFactory<iglesias,String>("nombreIglesias"));
        //enlazar combobox
        cmbDistrito.setItems(listadistritos);
        cmbUbicacion.setItems(listadistritos);
        gestionarEventos();
        conexion.cerrarConexion();               
    }    
    @FXML
    public void gestionarEventos(){
        tblIglesias.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<iglesias>(){
            @Override
            public void changed(ObservableValue<? extends iglesias> observable,
                    iglesias oldValue, iglesias newValue){
                if(newValue != null){
                   idiglesiatxt.setText(String.valueOf(newValue.getidiglesias()));
                   Nombretxt.setText(newValue.getnombreIglesias());
                   Reseñatxt.setText(String.valueOf(newValue.getreseña()));
                   Calletxt.setText(String.valueOf(newValue.getcalle()));
                 }
               cmbDistrito.setValue(newValue.getdistritos());    
               btnActualizar.setDisable(false);
               btnEliminar.setDisable(false);
               btnNuevo.setDisable(false);
               btnGuardar.setDisable(false) ;                           
               
            }
          }
        );
    }
    
    @FXML
    public void guardarRegistro(){
        iglesias i = new iglesias(0, 
                Nombretxt.getText(), 
                Reseñatxt.getText(), 
                Calletxt.getText(), 
                cmbDistrito.getSelectionModel().getSelectedItem());
        //llamada a metodo guardar registro de la clase
        conexion.establecerConexion();
        int resultado = i.guardarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        
        if (resultado ==1){
            listaiglesias.add(i);
            Alert mensaje = new Alert(AlertType.INFORMATION);
            mensaje.setTitle("Registro Guardado");
            mensaje.setContentText("El Registro se guardó correctamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }      
    
    } 
    @FXML
    public void actualizarRegistro(){
      iglesias i = new iglesias(
              Integer.valueOf(idiglesiatxt.getText()),
              Nombretxt.getText(),
              Reseñatxt.getText(),
              Calletxt.getText(),
              cmbDistrito.getSelectionModel().getSelectedItem());
      
      conexion.establecerConexion();
      int resultado = i.actualizarRegistro(conexion.getConnection());
      conexion.cerrarConexion();
      
      if(resultado == 1){
          listaiglesias.set(tblIglesias.getSelectionModel().getSelectedIndex(),i);
          Alert mensaje = new Alert(AlertType.INFORMATION);
          mensaje.setTitle("Registro actualizado");
          mensaje.setContentText("El registro se actualizó correctamente");
          mensaje.setHeaderText("Resultado:");
          mensaje.show();
      }
     }
    @FXML 
    public void eliminarRegistro(){
        conexion.establecerConexion();
        int resultado = tblIglesias.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        
        if(resultado ==1){
            listaiglesias.remove(tblIglesias.getSelectionModel().getSelectedIndex());
            
            Alert mensaje = new Alert(AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro se eliminó correctamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }
    
    @FXML
    public void limpiarComponentes(){
        Nombretxt.setText(null);
        Reseñatxt.setText(null);
        cmbUbicacion.setValue(null);
        cmbDistrito.setValue(null);
        Calletxt.setText(null);
        idiglesiatxt.setText(null);
        
        btnActualizar.setDisable(false);
        btnEliminar.setDisable(false);   
        btnGuardar.setDisable(false);
        btnNuevo.setDisable(true);
    }  
    /*
    @FXML
    public void filtrarUbicacion(){
       cmbUbicacion.getSelectedItem().toString();
    }*/
 
}
