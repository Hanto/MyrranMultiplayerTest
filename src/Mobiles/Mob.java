/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobiles;

import DATA.MiscData;
import Main.Myrran;
import Utils.Coordenadas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/**
 *
 * @author Hanto
 */
public abstract class Mob
{
    //Posicion
    protected double X;
    protected double Y;
    protected double oldPositionX;
    protected double oldPositionY;
    protected int numMapa=0;                            // Mapa en el que se encuentra la entidad
    
    //Velocidad y Direccion
    protected double VelocidadMod=1;                    // Modificadores de Velocidad: debido a Snares, a Sprints, Roots
    protected double VelocidadMax;                      // Velocidad Maxima:
    protected double Velocidad;                         // Velocidad Actual:
    protected double Direccion;                         // Direccion Actual en Radianes
    
    //Sprites y Animacion
    protected Image img;
    public TextureRegion texture;
    public String nombreRecurso;
    protected String imgFilename;
    protected Coordenadas AnimacionBase = new Coordenadas(0,0);
    protected double contadoranimacion; //numero de iteraciones para que se active un paso de animaciom, depende del refresh rate, como es de 20 ms, y queremos una animacion cada 200 ms 200/20, cada 10 iteraciones, una animaciom
    protected int FrameInc = 0;
    protected int FrameActual = 0;
    protected int FramesTotales = 3;
    protected int FrameActual_Xcord;
    protected int FrameActual_YCord;
    protected int TamañoSprite = MiscData.MAP3D_TILESIZE;    
    
    //Sistema Waypoints
    public int numChoquesContraMuro=0;
    protected ArrayList <Coordenadas> MapaWaypoints;
    protected int WaypointActual =0;
    protected boolean AutopilotActivado=false;
    
    //Booleanos de Identificacion
    protected Boolean isPlayer=false;
    protected Boolean isPlayerFriend=false;
    protected Boolean isPlayerEnemy=false;
    protected Boolean isNPCEnemy=false;
    protected Boolean isNPCFriend=false;
    protected Boolean isProyectil=false;
   
    //Update Rate
    protected int UpdateRate=MiscData.SPRITE_IA_WAIT_REFRESH_RATE;
    
    
    public void setX(int x)                     { X=x;}
    public void setY(int y)                     { Y=y;}
    public void setPosXY(int x, int y)          { X=x;Y=y;}
    
    public void setVelocidadMod (double d)      { VelocidadMod =d ; }
    public void setVelocidad (double d)         { Velocidad = d;}
    public void setVelocidadMax (double d)      { VelocidadMax = d;}
    public void setDireccion (double d)         { Direccion = d;}
    public void setOldPositionX (int x)         { oldPositionX = x; }
    public void setOldPositionY (int y)         { oldPositionY = y; }
    
    public void setImg(Image i)                 { img = i;}
    public void setImgFilename (String s)       { imgFilename = s; }
    public void setAnimacionBase(Coordenadas c) { AnimacionBase = c;}
    public void setTamañoSprite(int i)          { TamañoSprite = i; }
    public void setFrameActual (int x)          { FrameActual = x;}
    public void setFrameActualXY (int x, int y) { FrameActual_Xcord = x; FrameActual_YCord =  y;}
    public void setFrameInc (int x)             { FrameInc = x; }
    public void setContadoranimacion (double x) { contadoranimacion = x; }
    
    public Image getImg ()                      { return img;}
    public String getNombreRecurso()            { return nombreRecurso; }
    public String getImgFilename()              { return imgFilename; }
    public Coordenadas getAnimacionBase ()      { return AnimacionBase; }
    public int getX()                           { return (int)Math.round(X);}
    public int getY()                           { return (int)Math.round(Y);}
    public int getSueloX()                      { return (int)Math.round(X);}
    public int getSueloY()                      { return (int)Math.round(Y)+MiscData.MAP3D_TILESIZE-1;}
    public int getPiesX()                       { return (int)Math.round(X)+MiscData.MAP3D_TILESIZE/2;}
    public int getPiesY()                       { return (int)Math.round(Y)+MiscData.MAP3D_TILESIZE-1;}
    public int getCentroX()                     { return ((int)X+MiscData.MAP3D_TILESIZE/2);}
    public int getCentroY()                     { return ((int)Y+MiscData.MAP3D_TILESIZE/2);}
    public int getOldPosititionX()              { return (int)Math.round(oldPositionX);}
    public int getOldPositionY()                { return (int)Math.round(oldPositionY);}
    public int getCamEsquinaSupIzdaX()          { return (int)X-MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2; }
    public int getCamEsquinaSupIzdaY()          { return (int)Y-MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2; }
    public int getCamEsquinaInfDchaX()          { return (int)X+MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2; }
    public int getCamEsquinaInfDchaY()          { return (int)Y+MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2; }
    
    public double getVelocidadMod()             { return VelocidadMod; }
    public double getVelocidadMax()             { return VelocidadMax; }
    public double getVelocidad ()               { return Velocidad; }
    public double getDireccion()                { return Direccion; }
    
    public int getTotalFrames ()                { return FramesTotales; }
    public int getFrameActual ()                { return FrameActual; }
    public int getFrameInc ()                   { return FrameInc; }
    
    public double getContadorAnimacion ()       { return contadoranimacion; }
    public int getFrameActual_Xcord ()          { return FrameActual_Xcord; }
    public int getFrameActual_Ycord ()          { return FrameActual_YCord; }
    
    public Boolean isPlayer()                   { return isPlayer; }
    public Boolean isPlayerFriend()             { return isPlayerFriend; }
    public Boolean isPlayerEnemy()              { return isPlayerEnemy; }
    public Boolean isNPCEnemy()                 { return isNPCEnemy; }
    public Boolean isNPCFriend()                { return isNPCFriend; }
    public Boolean isProyectil()                { return isProyectil; }
    
    
    public void LoadImage (String imgFilename)
    {
        URL url = getClass().getClassLoader().getResource(imgFilename);
        if (url == null) {System.out.println (imgFilename+" del Grafico del Mob no se encuentra.");}
        else 
        {   
            try 
            { 
                img = ImageIO.read(url); 
                this.imgFilename = imgFilename;
                int inicio = imgFilename.lastIndexOf("/")+1;
                nombreRecurso = imgFilename.substring(inicio, imgFilename.length()-4);
            }
            catch (Exception e) {}
        }
    }
    
    public Boolean CheckColisonMuro ()
    {   
        try 
        {
            int TileX = getPiesX()/MiscData.MAP3D_TILESIZE;
            int TileY = getPiesY()/MiscData.MAP3D_TILESIZE;
            
            if (Myrran.getMundo().ListaMapas().get(numMapa).map()[TileX][TileY].isSolido()) return Boolean.TRUE;
            else return Boolean.FALSE;
        } catch (Exception e) {return Boolean.FALSE;}
    }
    
    public void UpdateContadoresAnimacion ()
    { 
        if ( this.getFrameActual() >= this.getTotalFrames()-1) this.setFrameInc(-1);
        if ( this.getFrameActual() <= 0) this.setFrameInc(1);;
        
        this.setContadoranimacion(this.getContadorAnimacion()+1);
        if (this.getContadorAnimacion() >= MiscData.SPRITE_VELOCIDADANIMACIONES) 
        {   
            this.setContadoranimacion(0);
            FrameActual = FrameActual + FrameInc;  
        }
    }
    
    public void Moverse()
    {
        UpdateContadoresAnimacion();
        
        oldPositionX=X;
        oldPositionY=Y;
        
        Y=  (Y+ (Math.sin(Direccion))*VelocidadMax*VelocidadMod);
        X=  (X+ (Math.cos(Direccion))*VelocidadMax*VelocidadMod);
        
        if (CheckColisonMuro ()) 
        {
            X=oldPositionX;
            Y=oldPositionY;
            numChoquesContraMuro++;
        }
        else
        {
            numChoquesContraMuro=0;
        }
    }
}
