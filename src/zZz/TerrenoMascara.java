/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zZz;

import java.awt.Color;

/**
 *
 * @author Hanto
 */

//En caso de ser verdadero el campo, significa que tiene preferencia sobre el template de base
public class TerrenoMascara 
{
    private Boolean ID = Boolean.FALSE;
    private Boolean Nombre = Boolean.FALSE;
    private Boolean Imagen = Boolean.FALSE;
    private Boolean Bitmap = Boolean.FALSE;
    private Boolean DireccionBitmap = Boolean.FALSE;
    private Boolean ColorTerreno = Boolean.FALSE;
    private Boolean FlagMuro = Boolean.FALSE;
    private Boolean FlagPuerta = Boolean.FALSE;
    private Boolean FlagLlave = Boolean.FALSE;
    
    public void setID (Boolean bol)                 {ID = bol;}
    public void setNombre (Boolean bol)             {Nombre = bol;}
    public void setImagen (Boolean bol)             {Imagen = bol;}
    public void setBitmap (Boolean bol)             {Bitmap = bol;}
    public void setDireccionBitmap (Boolean bol)    {DireccionBitmap = bol;}
    public void setColorTerreno (Boolean bol)       {ColorTerreno = bol;}
    public void setFlagMuro (Boolean bol)           {FlagMuro = bol;}
    public void setFlagPuerta (Boolean bol)         {FlagPuerta = bol;}
    public void setFlagLlave (Boolean bol)          {FlagLlave = bol;}           
    
    public Boolean getID ()                         {return ID;}
    public Boolean getNombre ()                     {return Nombre;}
    public Boolean getImagen ()                     {return Imagen;}
    public Boolean getBitmap ()                     {return Bitmap;}
    public Boolean getDireccionBitmap ()            {return DireccionBitmap;}
    public Boolean getColorTerreno ()               {return ColorTerreno;}
    public Boolean getFlagMuro ()                   {return FlagMuro;}
    public Boolean getFlagPuerta ()                 {return FlagPuerta;}
    public Boolean getLlave ()                      {return FlagLlave;}
    
}
