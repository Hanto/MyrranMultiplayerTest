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
public class HOT extends Aura
{   
    private int HealPorTick;
    
    private int iteracionesparaunsegundo = (1000/MiscData.SPELL_UPDATE_RATE);
    private int contador=0;
    
    public void setHealPorTick (int i)              { HealPorTick = i; }
    public int getHealPorTick ()                    { return HealPorTick; }
    
    public HOT (final Personaje Target)
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar(Target);
                while (Duracion<DuracionMax) 
                {
                    Update();
                    try {Thread.sleep(MiscData.SPELL_UPDATE_RATE);} 
                    catch (InterruptedException e) {return;}
                } 
                Expirar();
            }
        }).start();
    }
    
    public void CurarHOT ()
    {
        contador++;
        
        if (contador>=iteracionesparaunsegundo)
        {
            contador = 0;
            if (Target.isPlayer()) {Myrran.getMap3D().addSCT(Target.getX()+MiscData.MAP3D_TILESIZE/2, Target.getY() /*+MiscData.MAP3D_TILESIZE/2*/, "+"+HealPorTick, 2000, MiscData.SCT_PLAYER_SELFHEAL_COLOR);}
            if (!Target.isPlayer()) {Myrran.getMap3D().addSCT(Target.getX()+MiscData.MAP3D_TILESIZE/2, Target.getY() /*+MiscData.MAP3D_TILESIZE/2*/, "+"+HealPorTick, 2000, MiscData.SCT_OTHER_SELFHEAL_COLOR);}
            Target.setActualHPs(Target.getActualHPs()+HealPorTick);
        }
    }
    
    public void Update ()
    {
        Consumirse();
        
        CurarHOT ();
    }
}