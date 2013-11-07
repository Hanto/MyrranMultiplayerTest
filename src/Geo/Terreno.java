/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geo;

import DATA.MiscData;
import Main.Myrran;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Hanto
 */
public class Terreno 
{
    //ID del tipo de celda para cargar su template
    private int ID;
    private String Nombre;
    private String Caracter;
    private Sprite sprite;
    private Image IMG;
    private String IMGFile;
    private Color ColorTerreno;
    //Flags diversos
    private Boolean FlagMuro = Boolean.FALSE;
    private Boolean FlagPuerta = Boolean.FALSE;
    private ArrayList <Integer> Llaves = new ArrayList <Integer>();
    
    public void setID (int id)                  { ID = id;}
    public void setNombre (String nombre)       { Nombre = nombre; }
    public void setCaracter (String imagen)     { Caracter = imagen.substring(0, 1); }
    public void setIMG (Image img)              { IMG = img;}
    public void setIMG (File file)              { try {IMG = ImageIO.read(file);IMG.flush();} catch (Exception e) {;}}
    public void setIMG (String string)          { try {IMG = ImageIO.read(new File ("images/"+string));IMG.flush();} catch (Exception e) {;}}
    public void setIMGFile (String s)           { IMGFile = s;}
    public void setColorTerreno (Color color)   { ColorTerreno = color;}
    public void setFlagMuro (Boolean bol)       { FlagMuro = bol;}
    public void setFlagPuerta (Boolean bol)     { FlagPuerta = bol;}
    public void setSprite (Sprite t)            { sprite = t; }
    
    public int getID ()                         { return ID;}
    public String getNombre ()                  { return Nombre;}
    public String getCaracter ()                { return Caracter;}
    public Image getIMG ()                      { return IMG;}
    public String getIMGFile ()                 { return IMGFile;}
    public Color getColorTerreno ()             { return ColorTerreno;}
    public Boolean getFlagMuro ()               { return FlagMuro; }
    public Boolean getFlagPuerta ()             { return FlagPuerta; }
    public Sprite getSprite()                   { return sprite; }
    
    public ArrayList getLlaves ()               { return Llaves; }
    
    public void nuevoTipoTerreno ()
    {
        if (Myrran.getMundo().ListaTerrenos().isEmpty()) ID = 0;
        else {ID = Myrran.getMundo().ListaTerrenos().get(Myrran.getMundo().ListaTerrenos().size()-1).getID()+1;}
        
        Nombre = MiscData.TERRENO_NOMBRE_NEW+"_"+ID;
        Caracter = MiscData.TERRENO_IMAGEN_NEW;
        IMGFile = MiscData.TERRENO_DIRECCIONBITMAP_NEW;
        this.setIMG(IMGFile);
        ColorTerreno = MiscData.TERRENO_COLORTERRENO_NEW;
        
        FlagMuro = Boolean.FALSE;
        FlagPuerta =Boolean.FALSE;
    }
}
