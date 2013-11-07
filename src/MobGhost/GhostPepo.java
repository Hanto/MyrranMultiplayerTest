/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MobGhost;

import DATA.MiscData;
import Main.Myrran;
import Mobiles.Mob;

/**
 *
 * @author Hanto
 */
public class GhostPepo extends Mob
{
    protected String ID;
    
    protected int Duracion;
    protected int DuracionMax=10000;
    
    public void setID(String id)                { ID = id; }
    public void setDuracion (int duracion)      { Duracion = duracion; }
    public void setDuracionMax (int duracionm)  { DuracionMax = duracionm; }
    public int getDuracionMax ()                { return DuracionMax; }
    public String getID()                       { return ID; }

    
    public void Inicializar (int X, int Y)
    {
        setPosXY(X,Y);
    }
    
    
    public GhostPepo (final int X, final int Y)
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar (X, Y);
                while (Duracion<DuracionMax) 
                {   
                    try 
                    {
                        Update();
                    } 
                    catch (Exception e) {}
                    try {Thread.sleep(MiscData.MAP3D_REFRESH_RATE);} 
                    catch (InterruptedException e) {return;}
                }
                Expirar();
            }
        }).start();
    }
    
       
    public void UpdateAnimacion ()
    {   
        setFrameActualXY (AnimacionBase.getX()*MiscData.MAP3D_TILESIZE+FrameActual*MiscData.MAP3D_TILESIZE, AnimacionBase.getY()*MiscData.MAP3D_TILESIZE); 
    }
    
    public void Consumirse()                    
    { 
        Duracion=Duracion+(1000/MiscData.MAP3D_REFRESH_RATE); 
    }
    
    public void Expirar()          
    { 
        synchronized (Myrran.getMundo().ListadeGhostPeposCercanos()) {
        if (Myrran.getMundo().ListadeGhostPeposCercanos().contains(this)) Myrran.getMundo().ListadeGhostPeposCercanos().remove(this); }
    }
    
    public void Update ()
    { 
        Moverse ();
        Consumirse();
        UpdateAnimacion();        
    }
}
