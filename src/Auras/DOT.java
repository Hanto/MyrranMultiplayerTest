/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auras;

import DATA.MiscData;
import DATA.SpellData;
import Main.Myrran;
import Mobiles.Personaje;
import java.awt.Color;

/**
 *
 * @author Hanto
 */
public class DOT extends Aura
{   
    private int DañoPorTick;
    private int iteracionesparaunsegundo = (500/MiscData.SPELL_UPDATE_RATE);       //Para que tickee el doble de rapido de lo normal pero en cambio el daño por segundo sea el mismo
    private int contador=0;
    public void setDañoPorTick (int i)              { DañoPorTick = i; }
    public int getDañoPorTick ()                    { return DañoPorTick; }
    
    
    public void ExpirarDOT ()
    {
        
    }
    
    public void InicializarDOT ()
    {   //Dura el doble por que tickea el doble de rapido para que el feeling sea mas de metralleta
        DuracionMax=DuracionMax*2;
    }
    
    public DOT (final Personaje Target)
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar(Target);
                InicializarDOT();
                while (Duracion<DuracionMax) 
                {
                    Update();
                    try {Thread.sleep(MiscData.SPELL_UPDATE_RATE);} 
                    catch (InterruptedException e) {return;}
                } 
                Expirar();
                ExpirarDOT();
            }
        }).start();
    }
    
    public void TickearDOT ()
    {
        contador++;
        
        if (contador>=iteracionesparaunsegundo)
        {
            contador = 0;
            if (Target.isPlayer()) {Myrran.getMap3D().addSCT(Target.getX()+MiscData.MAP3D_TILESIZE/2, Target.getY() /*+MiscData.MAP3D_TILESIZE/2*/, "-"+DañoPorTick, 1000, MiscData.SCT_PLAYER_DAMAGES_OTHER);}
            if (!Target.isPlayer()) {Myrran.getMap3D().addSCT(Target.getX()+MiscData.MAP3D_TILESIZE/2, Target.getY() /*+MiscData.MAP3D_TILESIZE/2*/, "-"+DañoPorTick, 1000, MiscData.SCT_OTHER_DAMAGES_PLAYER);}
            Target.setActualHPs(Target.getActualHPs()-DañoPorTick);
        }
    }
    
    public void Update ()
    {
        Consumirse();
        
        TickearDOT ();
    }
}
