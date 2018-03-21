/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import static View.ConnectionForm.connection;
import static View.ConnectionForm.tableName;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author HenrryJLopez
 */
public class CreateFormView {
    public ResultSet resultTablesNames;
    public Statement sentenceTableNames;
    
    public ResultSet resultDataTable;
    public Statement sentenceDataTable;
    
    private ArrayList<Object> dataTable;
    private ArrayList<Object> columnTable;
    
    
    
    private List<JButton> buttons;
    private List<JLabel> labelColumn;
    private List<String> columnName;
    private List<JTextField> textfields;
    
    private int xLabel;
    private int yLabel;
    
    private int xTextField;
    private int yTextField;
    
    private DefaultTableModel modelTable;
    
    
    public Statement insertData;
    public Statement updateData;
    public Statement deleteData;

    
     public CreateFormView() {
        buttons=new ArrayList<>();
        labelColumn=new ArrayList<>();
        textfields=new ArrayList<>();
        
        dataTable=new ArrayList();
        columnTable=new ArrayList();
        columnName=new ArrayList();
        
        //Coordenadas de posición
        xLabel=20;
        yLabel=20;
        xTextField=150;
        yTextField=20;
    }
    
    public void buildFormView(JPanel contentView){
        /**
         * tableName y connection, son variables staticas creadas en el formulario de conexion
         **/
        ArrayList<String> dataTables=new ArrayList<String>();
        String query="DESCRIBE "+tableName;
        try{
            sentenceTableNames = connection.createStatement();
            resultTablesNames=sentenceTableNames.executeQuery(query);
            
            while(resultTablesNames.next()){
                
                JLabel label=new JLabel(resultTablesNames.getString(1));
                JTextField text=new JTextField();
                
                label.setBounds(xLabel, yLabel, 150, 30);
                text.setBounds(xTextField, yTextField, 320, 30);

                contentView.add(label);
                contentView.add(text);
                columnName.add(resultTablesNames.getString(1));
                labelColumn.add(label);
                textfields.add(text);

                contentView.updateUI(); 

                yLabel+=30;
                yTextField+=30;
        
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }

    public void buildTableView(JTable contentTableView){
        modelTable= (DefaultTableModel)  contentTableView.getModel();
        
        //Sección 2
        Object [] column=new Object[columnName.size()]; 

        for (int i = 0; i < columnName.size(); i++) {
            columnTable.add(columnName.get(i));
            modelTable.addColumn(columnName.get(i));
        }

        
        ArrayList<String> dataTables=new ArrayList<String>();
        String query="SELECT * FROM "+tableName;
        try{
            sentenceDataTable = connection.createStatement();
            resultDataTable=sentenceDataTable.executeQuery(query);
            
            while(resultDataTable.next()){
                Object [] row=new Object[labelColumn.size()];
                for (int i = 0; i < labelColumn.size(); i++) {
                    row[i]=resultDataTable.getString(i+1);
                }
                
                dataTable.add(row);
                modelTable.addRow(row);
            }
        }catch(Exception e){
            System.out.print(e);
        }
        contentTableView.setModel(modelTable);
    }
    

    
    public void refreshTable(JTable contentTableView){
        contentTableView.setModel(modelTable);
    }
    
    public boolean newData(){
        Object [] newRow= new Object[textfields.size()];
        String query="INSERT INTO "+tableName+" VALUES (";
        int index=0;
        for (JTextField text : textfields) {
            query+="'"+text.getText()+"'";
            newRow[index]=text.getText();
            index++;
            if (index!=textfields.size()) {
                query+=",";
            }
        }
        
        query+=")";
        
        System.out.print(query);
        try{
            insertData = connection.createStatement();
            insertData.executeUpdate(query);
            modelTable.addRow(newRow);
            return true;
        }catch(Exception e){
            System.out.print(e);
        }
        return false;
    }
    
    
    public boolean updateData(JTable contentTableView){
        int selectedRow=contentTableView.getSelectedRow();
        
        Object [] newRow= new Object[textfields.size()];
        String query="UPDATE "+tableName+" SET ";
        //Inicia en 1 ya que se asume que el primer campo es la clave y no se it
        int index=0;
        for (JTextField text : textfields) {
            if (index>=1) {
                query+=columnName.get(index)+"= '"+text.getText()+"'";
            }
            
            newRow[index]=text.getText();
            
            index++;
            
            if (index!=textfields.size() && index>=2) {
                query+=",";
            }
        }
        
        query+=" WHERE "+columnName.get(0)+"='"+modelTable.getValueAt(selectedRow, 0)+"'" ;
        
        System.out.print(query);
        try{
            insertData = connection.createStatement();
            insertData.executeUpdate(query);
            //modelTable.insertRow(selectedRow, newRow);
            for (int i = 0; i < newRow.length; i++) {
                modelTable.setValueAt(newRow[i], selectedRow, i);
            }
            //modelTable.setValueAt(this, index, index);
            return true;
        }catch(Exception e){
            System.out.print(e);
        }
        return false;
    }
    
    

    
    public boolean deleteData(JTable contentTableView){
        int selectedRow=contentTableView.getSelectedRow();
        Object [] newRow= new Object[textfields.size()];
        String query="DELETE FROM "+tableName+" WHERE "+columnName.get(0)+"='"+modelTable.getValueAt(selectedRow, 0)+"'";
        
        System.out.print(query);
        try{
            deleteData = connection.createStatement();
            deleteData.executeUpdate(query);
            modelTable.removeRow(selectedRow);
            return true;
        }catch(Exception e){
            System.out.print(e);
        }
        return false;
    }
    
    
    public void loadData(JTable contentTableView){
        int selectedRow=contentTableView.getSelectedRow();
        Object [] newRow= new Object[textfields.size()];
        int index=0;
        for (JTextField text : textfields) {
            text.setText(modelTable.getValueAt(selectedRow, index).toString());
            index++;
        }
    }
    
    
    public boolean cleanData(){
        for (JTextField text : textfields) {
            text.setText("");   
        }
        return false;
    }
    
    
}
