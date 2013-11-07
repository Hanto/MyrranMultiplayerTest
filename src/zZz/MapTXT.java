/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zZz;

import Main.Myrran;
import Geo.Terreno;
import zZz.MapaOLD;
import Geo.Mapa;
import Geo.Mundo;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Hanto
 */
public class MapTXT extends javax.swing.JFrame implements Runnable

{
    private int mapY;
    private int mapX; 
    private Mapa mapa;
    
    @Override
    public void run ()
    {
      //  printMap ();
    }
    
    public MapTXT() 
    {
        initComponents();
        TPMap.setBackground(Color.BLACK);
    }
    
    
    public void printMap ()
    {   /*
        mapa = Myrran.getMundo().ListaMapas2().get(WorldEditor.getnumMapa());
        TPMap.setText(null);
        
        for (int i=0;i<mapa.getMapeadoLenghti();i++)
        {
            for (int j=0;j<mapa.getMapeadoLenghtj();j++)
            { 
                String imagen;
                Color color;
                
                imagen = mapa.getMapImagen(i, j);
                    
                color = mapa.getMapColor(i, j);
                 
                //Resaltar casilla seleccionada
                if (i==mapY && j==mapX) {printColorChar (imagen,color,Color.RED);}
                else {printColorChar (imagen,color);}
                //Las demas casillas pintarlas normal
            }
            printColorChar ("\n",Color.BLACK);
        }*/
    }
    /*
    public void printColorChar (String string, Color color)
    {   
        SimpleAttributeSet saSet = new SimpleAttributeSet ();
        StyleConstants.setForeground(saSet, color);
        
        Document doc = TPMap.getStyledDocument();
        
        try { doc.insertString(doc.getLength(), string, saSet); }
        catch (Exception e) {}
        
        TPMap.setCaretPosition(TPMap.getDocument().getLength());
    }
    
    public void printColorChar (String string, Color foreground, Color background)
    {   
        SimpleAttributeSet saSet = new SimpleAttributeSet ();
        StyleConstants.setForeground(saSet, foreground);
        StyleConstants.setBackground(saSet, background);
        
        Document doc = TPMap.getStyledDocument();
        
        try { doc.insertString(doc.getLength(), string, saSet); }
        catch (Exception e) {}
        
        TPMap.setCaretPosition(TPMap.getDocument().getLength());
    }
    
    public void salvarCelda (int x, int y)
    {
        
        if (WorldEditor.getInsertarCelda() && !Myrran.getMundo().ListaTerrenos().isEmpty())
        {
            mapa.getTemplate()[x][y] = WorldEditor.getbrochaIDTerreno();
        }
    }
    */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TPMap = new javax.swing.JTextPane();

        TPMap.setEditable(false);
        TPMap.setBackground(new java.awt.Color(0, 0, 0));
        TPMap.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        TPMap.setForeground(new java.awt.Color(255, 255, 255));
        TPMap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TPMapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TPMapMouseEntered(evt);
            }
        });
        TPMap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPMapKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TPMap);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1029, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TPMapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TPMapMouseClicked
        /*
        try {
        //JTextPane editor = (JTextPane) evt.getSource();
        Point pt = new Point(evt.getX(), evt.getY());
        //int posicion = editor.viewToModel(pt); 
        int posicion = TPMap.viewToModel(pt);
        
        int columnas = (mapa.getMapeadoLenghtj())+1;
        mapY= posicion/columnas;
        mapX= posicion%columnas;
        
        //Si hemos activado el flag de edicion de mapa y hay al menos un tipo de celda creado
        salvarCelda (mapY, mapX);
        printMap();
        } catch (Exception e) {} */
    }//GEN-LAST:event_TPMapMouseClicked

    private void TPMapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPMapKeyPressed
        //Si va hacia el norte (Aprieta la W)
       /* if (evt.getKeyCode()==87 || evt.getKeyCode()==38)
        {   mapY = mapY-1;
            if (mapY<0) {mapY = mapa.getMapeadoLenghti()-1;}
            printMap();
        }
        //si va hacia el sur (Aprieta la S)
        if (evt.getKeyCode()==83 || evt.getKeyCode()==40)
        {   mapY = mapY+1;
            if (mapY>mapa.getMapeadoLenghti()-1) {mapY = 0;}
            printMap();
        }
        //si va hacia el Oeste (Aprieta la A)
        if (evt.getKeyCode()==65 || evt.getKeyCode()==37)
        {   mapX = mapX-1;
            if (mapX<0) {mapX = mapa.getMapeadoLenghtj()-1;}
           printMap();
        }
        //si va hacia el Este (Aprieta la D)
        if (evt.getKeyCode()==68 || evt.getKeyCode()==39)
        {   mapX = mapX+1;
            if (mapX>mapa.getMapeadoLenghtj()-1) {mapX = 0;}
            printMap();
        }
        //Barra espaciadora es el equivalente de hacer click con el raton
        if (evt.getKeyCode()==32)
        {   
            salvarCelda (mapY, mapX);     
            printMap();
        }
        //System.out.println(evt.getKeyCode());
        else return;*/
        
    }//GEN-LAST:event_TPMapKeyPressed

    private void TPMapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TPMapMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TPMapMouseEntered

 
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MapTXT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MapTXT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MapTXT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MapTXT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MapTXT().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextPane TPMap;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
} 
