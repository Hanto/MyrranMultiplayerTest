/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import DATA.MiscData;
import Main.Myrran;
import Utils.Calculos;
import java.awt.Color;


/**
 *
 * @author Hanto
 */
public class CombatText 
{
    private static final int UPDATE_PERIODO = MiscData.MAP3D_REFRESH_RATE;    //en milisegundos
    
    private int Duracion = 2000;             //en milisegundos;
    private int ySpeed = 1;
    private String Texto;
    private int posX;
    private int posY;
    private Color color;
    
    private int DistanciaAPlayer;
    private int DistanciaVision;
    
    public String getTexto()                {return Texto;}
    public int getX()                       {return posX;}
    public int getY()                       {return posY;}
    public int getDuracion()                {return Duracion;}
    public Color getColor()                 {return color;}
    
    public void setTexto(String string)     {Texto=string;}
    public void setPosX(int x)              {posX= x;}
    public void setPosY(int y)              {posY= y;}
    public void setPosX(double x)           {posX = (int)Math.round(x);}
    public void setPosY(double y)           {posY = (int)Math.round(y);}
    public void setDuracion(int d)          {Duracion = d;} 
    public void setColor(Color c)           {color =c;}
    
    public void getDistanciaVision()        { DistanciaVision = (int)Math.round(MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/MiscData.MAP3D_TILESIZE*1.5);}
    public void getDistanciaAPlayer()       { DistanciaAPlayer = (int)Math.round(Calculos.Distancia(posX, posY, Myrran.getMundo().getPlayer().getPiesX(), Myrran.getMundo().getPlayer().getPiesY())); DistanciaAPlayer = DistanciaAPlayer/MiscData.MAP3D_TILESIZE; }
    
    public void start ()
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                getDistanciaVision();
                while (Duracion >0) 
                {
                    update();
                    try {Thread.sleep(UPDATE_PERIODO);} 
                    catch (InterruptedException e) {return;}
                }
                Expirar();
            }
        }).start();
    }
    
    public void Expirar()          
    { 
        synchronized (Myrran.getMundo().ListaSCT()) {
        if (Myrran.getMundo().ListaSCT().contains(this)) Myrran.getMundo().ListaSCT().remove(this); }
    }
    
    public void update ()
    {
        posY = posY - ySpeed;
        Duracion = Duracion - UPDATE_PERIODO;
        
        getDistanciaAPlayer();
        synchronized (Myrran.getMundo().ListaSCT()) {
        if (DistanciaAPlayer <= DistanciaVision && !Myrran.getMundo().ListaSCT().contains(this)) Myrran.getMundo().ListaSCT().add(this);
        if (DistanciaAPlayer > DistanciaVision && Myrran.getMundo().ListaSCT().contains(this)) Myrran.getMundo().ListaSCT().remove(this); }  
    }
    
}
