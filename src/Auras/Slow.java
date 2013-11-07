/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auras;

import DATA.MiscData;
import DATA.SpellData;
import Mobiles.Personaje;

/**
 *
 * @author Hanto
 */
public class Slow extends Aura
{   
    protected double PenalizadorMovimiento;
    public void setPenalizadorMovimiento(double i)     { PenalizadorMovimiento = i ;}
    
    
    public void ExpirarSlow ()      
    { 
        Target.setVelocidadMod(1); 
    }
    
    public void InicializarSlow()
    {
        
    }
    
    public Slow (final Personaje Target)
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar(Target);
                InicializarSlow();
                while (Duracion<DuracionMax) 
                {
                    Update();
                    try {Thread.sleep(MiscData.SPELL_UPDATE_RATE);} 
                    catch (InterruptedException e) {return;}
                } 
                Expirar();
                ExpirarSlow();
            }
        }).start();
    }
    
    public void Update ()
    {
        Consumirse();
        
        Target.setVelocidadMod(PenalizadorMovimiento);
    }
}
