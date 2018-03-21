/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import static View.ConnectionForm.connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author HenrryJLopez
 */
public class FillTables {
    public ResultSet result;
    public Statement sentence;
    
    public ArrayList<String> getTables(){
        ArrayList<String> dataTables=new ArrayList<String>();
        String query="SHOW TABLES";
        try{
            sentence = connection.createStatement();
            result=sentence.executeQuery(query);
            while(result.next()){
                dataTables.add(result.getString(1));
            }
        }catch(Exception e){
            System.out.print(e);
        }
        
        return dataTables;
    }
    
    public void fillComboboxTable(JComboBox cmbTables){
        //cmbTables.removeAllItems();
        ArrayList<String> data=getTables();
        //cmbTables.addItem(data);
        for (int i = 0; i < data.size(); i++) {
            cmbTables.addItem(data.get(i));
        }
    }
}
