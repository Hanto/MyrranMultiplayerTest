/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldBuilder;

/**
 *
 * @author Hanto
 */
import GDX.GameplayScreen;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import Utils.Archivo;
import Main.Myrran;
 
 
public class SelectFile extends JFrame 

{

    private JButton btn ;
    private JTextField textField;
    private JFileChooser fc;

    private Boolean isTerreno;
    private Boolean isEstatico1;
    
    public void isTerreno ()                { isTerreno = true; isEstatico1 = false; }
    public void isEstatico1 ()              { isEstatico1 = true; isTerreno = false; }
    
    public SelectFile() 

    {
        initComponents();
        simpleMethod();
    }
    
    private void initComponents() 
    {   
        setSize(300, 60);
        setTitle("Browser");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        btn = new JButton("Browse");
        btn.setPreferredSize(new Dimension(100, 20));
        c.add(btn);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(160, 20));
        c.add(textField);
        pack();
    }

    private void simpleMethod() 
    {
        btn.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnBrowseActionPerformed(evt);}});
    }
    
    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) 
    {
        if (fc == null) { fc = new JFileChooser("."); }
 
        int returnVal = fc.showOpenDialog(this);
 
        // Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            textField.setText(fc.getSelectedFile().getPath());
            
            if (isTerreno)
            {
                try 
                {
                    Archivo a = new Archivo();
                    String pathfichero = a.Copiar(fc.getSelectedFile(), "TileSet_"+Myrran.getMundo().ListaTerrenos().get(WorldBuilder.WorldEditor.getnumTerreno()).getID()+"n");
                
                    Myrran.getMundo().ListaTerrenos().get(WorldBuilder.WorldEditor.getnumTerreno()).setIMG(fc.getSelectedFile());
                    Myrran.getMundo().ListaTerrenos().get(WorldBuilder.WorldEditor.getnumTerreno()).setIMGFile(pathfichero);
           
                    WorldEditor.ActualizarToolbarTerrenos();
                    WorldEditor.ActualizarDatosTerreno();
                    
                    GameplayScreen.reloadTexturas=true;
                } catch (Exception e) {System.out.println(e);}
            }
            if (isEstatico1)
            {
                try 
                {
                    Archivo a = new Archivo();
                    String pathfichero = a.Copiar(fc.getSelectedFile(), "Estatico_"+Myrran.getMundo().ListaEstaticos().get(WorldBuilder.WorldEditor.getnumEstatico1()).getID()+"n");
                
                    Myrran.getMundo().ListaEstaticos().get(WorldBuilder.WorldEditor.getnumEstatico1()).setIMG(fc.getSelectedFile());
                    Myrran.getMundo().ListaEstaticos().get(WorldBuilder.WorldEditor.getnumEstatico1()).setIMGFilename(pathfichero);
           
                    WorldEditor.ActualizarDatosEstatico1();
                } catch (Exception e) {}
                
                
            }
        } 
        else { textField.setText("");
        
        }
        // Reset the file chooser for the next time it's shown.
        fc.setSelectedFile(null);
    }

    public static void main(String[] args) 
    {
        SelectFile window = new SelectFile();
        window.setVisible(true);
    }
}