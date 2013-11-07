/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MobGhost;

import DATA.MiscData;
import Main.Myrran;


/**
 *
 * @author Hanto
 */
public class GhostNPC extends GhostPersonaje
{
    public void Inicializar (int X, int Y)
    {
        //Al crear el Mob, Hay que igualar el destino a la posicion inicial, o si no, el mob se nos movera hacia la posicion O,O
        setPosXY(X,Y);
        DestinoX=X;DestinoY=Y;
    }
    
    public GhostNPC (final int X, final int Y)
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar (X, Y);
                while (ActualHP>0 && isCerca)
                {   
                    try 
                    {
                        Update();
                    } 
                    catch (Exception e) {}
                    try {Thread.sleep(MiscData.MAP3D_REFRESH_RATE);} 
                    catch (InterruptedException e) {return;}
                }
                Morir ();
            }
        }).start();
    }
    
    public double RumboHaciaPixelXY(double X, double Y) { return Math.atan2(X,Y); }  
    
    public void setRumbo ()
    {
        int TargetX = DestinoX;
        int TargetY = DestinoY;
        
        int OrigenX = getPiesX();
        int OrigenY = getPiesY();
        
        Direccion = RumboHaciaPixelXY (TargetY-OrigenY, TargetX-OrigenX);
    }
    
   public void setVelocidad()    
    {   //Como mayor sea el desfase entra la posicion real y la posicion aparente, mas tiene que correr la ilusion para sincronizarse, pero tiene que ser proporcional, para que no haya saltos
        double Distancia = Utils.Calculos.Distancia(getPiesX(), getPiesY(), DestinoX, DestinoY);
        if (Distancia>1) VelocidadMax = MiscData.SPRITE_MAXVELOCIDADPLAYER*(1+Distancia/20);
    }
    
    public void UpdateAnimacion ()
    {   
        double angulo;
        angulo = Math.toDegrees(DireccionVisual+2*(Math.PI));
        angulo = angulo%360;
        
        if (45d<=angulo && angulo<135d) {setFrameActualXY (0*MiscData.MAP3D_TILESIZE+FrameActual*MiscData.MAP3D_TILESIZE, 0*MiscData.MAP3D_TILESIZE);} //Abajo
        if (135d<=angulo && angulo<225d) {setFrameActualXY (0*MiscData.MAP3D_TILESIZE+FrameActual*MiscData.MAP3D_TILESIZE, 1*MiscData.MAP3D_TILESIZE);} //Izda
        if (45>angulo && angulo>=0) {setFrameActualXY (0*MiscData.MAP3D_TILESIZE+FrameActual*MiscData.MAP3D_TILESIZE, 2*MiscData.MAP3D_TILESIZE);} //Dcha
        if (315<angulo && angulo<=360) {setFrameActualXY (0*MiscData.MAP3D_TILESIZE+FrameActual*MiscData.MAP3D_TILESIZE, 2*MiscData.MAP3D_TILESIZE);} //Dcha
        if (225<=angulo && angulo<315) {setFrameActualXY (0*MiscData.MAP3D_TILESIZE+FrameActual*MiscData.MAP3D_TILESIZE, 3*MiscData.MAP3D_TILESIZE);} //Arriba
    }
    
    public void Morir () 
    { 
        synchronized (Myrran.getMundo().ListadeGhostNPCsCercanos()) {
        if (Myrran.getMundo().ListadeGhostNPCsCercanos().contains(this)) Myrran.getMundo().ListadeGhostNPCsCercanos().remove(this); }
    }
    
    public void Update ()
    { 
        
        if (Utils.Calculos.Distancia(getPiesX(), getPiesY(), DestinoX, DestinoY) > 1) setRumbo();
        else  VelocidadMax = 0;
        
        setVelocidad();
       
        Moverse ();
        
        UpdateAnimacion();        
    }
}