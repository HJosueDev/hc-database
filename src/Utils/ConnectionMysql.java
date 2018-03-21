/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author HenrryJLopez
 */
public class ConnectionMysql {
    private String host="";
    private String port="";
    private String database="";
    private String user="";
    private String password="";
    
    private Connection connection = null;
    
    public ConnectionMysql(){
    }
    
    public ConnectionMysql(String host, String port, String database, String user, String password){
        this.host=host;
        this.port=port;
        this.database=database;
        this.user=user;
        this.password=password;
    }
    
    public Connection getConnection(JFrame form) throws SQLException, ClassNotFoundException {
        if (this.connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.port+"/"+this.database, this.user,this.password);
            } catch (SQLException ex) {
                //throw new SQLException(ex);
                //System.out.print(ex.getErrorCode());
                //Error 0 conexion no establecida
                //Error 1049 error base de datos no encontrada
                //Error 1045 error usuario u contraseña son incorrectos o no existen
                String Message="";
                switch(ex.getErrorCode()){
                    case 0:
                        Message="El servidor/host o puerto\n proporcionado no es correcto";
                        break;
                        
                    case 1049:
                        Message="El nombre de base de datos\n proporcionado no es correcto";
                        break;
                        
                    case 1045:
                        Message="El usuario o la contraseña proporcionada\n no existe o es incorrecta";
                        break;
                        
                    default:
                        Message="Excepción no encontrada";
                        break;
                        
                }
                JOptionPane.showMessageDialog(form, Message);

            } catch (ClassNotFoundException ex) {
                //throw new ClassCastException(ex.getMessage());
                System.out.print(ex.getMessage());
            }
        }
        return this.connection;
    }
    
    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }
    
}
