/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geo;

import DATA.MiscData;
import Main.Myrran;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Hanto
 */
public class Estatico 
{
    private int ID;
    private String Nombre;
    private Image IMG;
    private TextureRegion texture;
    private String IMGFilename;
    
    private Boolean isSolido;
    
    public int getID()                                  { return ID; }
    public void setNombre (String nombre)               { Nombre = nombre; }
    public Image getIMG ()                              { return IMG; }
    public Boolean isSolido ()                          { return isSolido; }
    public String getIMGFilename ()                     { return IMGFilename; }
    public TextureRegion getTexture()                   { return texture; }
    
    public void setID(int i)                            { ID = i; }
    public String getNombre ()                          { return Nombre;}
    public void setIMGFilename (String imgFilename)     { IMGFilename = imgFilename; }
    public void setIMG (String string)                  { try {IMG = ImageIO.read(new File (string));IMG.flush();} catch (Exception e) {;}}
    public void setIMG (File file)                      { try {IMG = ImageIO.read(file);IMG.flush();} catch (Exception e) {;}}
    public void isSolido (Boolean B)                    { isSolido = B; }
    public void setTexture (TextureRegion t)            { texture = t;}
    
    public Estatico ()
    {
        if (Myrran.getMundo().ListaEstaticos().isEmpty()) ID = 0;
        else {ID = Myrran.getMundo().ListaEstaticos().get(Myrran.getMundo().ListaEstaticos().size()-1).getID()+1;}
        
        Nombre = MiscData.ESTATICO_NOMBRE_NEW+"_"+ID;
        isSolido = false;
    }
    
    public void nuevoTipoEstatico ()
    {
        this.setIMG(MiscData.ESTATICO_DIRECCIONBITMAP_NEW);
        this.setIMGFilename(MiscData.ESTATICO_DIRECCIONBITMAP_NEW);
    }
    
    public void LoadImage (String imgFilename)
    {
        URL url = getClass().getClassLoader().getResource(imgFilename);
        if (url == null) {System.out.println (imgFilename+" del Grafico del Estatico no se encuentra.");}
        else 
        {       
            try { IMG = ImageIO.read(url);}
            catch (Exception e) {}
        }
    }
    
}
