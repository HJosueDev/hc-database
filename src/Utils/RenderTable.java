/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author HenrryJLopez
 */
public class RenderTable extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        if (o instanceof JTextField) {
            JTextField txt=(JTextField) o;
            txt.setEditable(true);
            txt.setEnabled(true);
            return txt;
        }
        if (o instanceof JLabel) {
            JLabel lbl=(JLabel) o;
            return lbl;
        }
        
        return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
    }

}