/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldBuilder;


import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

class TableRender extends JLabel implements TableCellRenderer
{

     public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
     {
        setOpaque(true);
        setBackground(table.getBackground());
        setForeground(table.getForeground());
        setFont(new java.awt.Font("Consolas", Font.PLAIN, 11));
        setHorizontalAlignment(SwingConstants.RIGHT);
        {     
            try { if (table.getValueAt(row, 1)!=table.getValueAt(row-1, 1)) { setBackground(new Color (225,225,225)); } } catch (Exception e) { setBackground(new Color (225,225,225)); }
            if (hasFocus) { setBackground(Color.RED);setForeground(Color.WHITE); }          
            if (value != null) setText(value.toString());
            else setText("");
        }
        return this;
    }
}