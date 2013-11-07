/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Main.Myrran;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Hanto
 */
public class Plantilla 
{
    private int ID;
    private Image IMGicono;
    private Boolean [][] Forma;    
    private int CoordenadasPunteroX;
    private int CoordenadasPunteroY;
    
    public int getID()                                  { return ID;}
    public int getX()                                   { return CoordenadasPunteroX; }
    public int getY()                                   { return CoordenadasPunteroY; }
    public Image getIMGicono ()                         { return IMGicono; }
    public Boolean getForma (int x, int y)              { return Forma[x][y]; }
    public Boolean [][] getForma ()                     { return Forma;}
                              
    public void setPuntero(int x, int y)                { CoordenadasPunteroX = x; CoordenadasPunteroY = y; }
    public void setForma (int x, int y, Boolean b)      { Forma[x][y] = b; }
    public void setForma (Boolean[][] b)                { Forma = b;}
    public void setIMGIcono (String s)                  { LoadIMGicono(s); }
    
    public int getAnchura ()                            { return Forma[0].length;}
    public int getAltura ()                             { return Forma.length;}
    
    //Hace el array de booleanos lo mas pequeño posible para optimizar las labores de dibujado grafico posteriores
    public void CompactarArrayForma ()
    {
        
    }
    
    public void LoadIMGicono (String imgFilename)
    {
        URL url = getClass().getClassLoader().getResource(imgFilename);
        if (url == null) {System.out.println (imgFilename+" del Grafico del Mob no se encuentra.");}
        else 
        {       
            try { IMGicono = ImageIO.read(url);}
            catch (Exception e) {}
        }
    }
    
    public Plantilla ()
    {
        if (Myrran.getMundo().ListaPlantillas().isEmpty()) ID = 0;
        else {ID = Myrran.getMundo().ListaPlantillas().get(Myrran.getMundo().ListaPlantillas().size()-1).getID()+1;}
    }
}
