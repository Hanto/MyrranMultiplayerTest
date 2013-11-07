/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobiles;

import DATA.MiscData;
import IO.KeyInputManager;
import IO.MouseInputManager;
import static IO.MouseInputManager.MouseX;
import static IO.MouseInputManager.MouseY;
import Main.Myrran;
import Multiplayer.Network.PlayerCast;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;


/**
 *
 * @author Hanto
 */
public class Player extends PC
{
    //Last Sent X,Y, ultimos valores X,Y enviados acerca de la posicion del player. Para no mandar repetidamente una posicion ya conocida.
    protected int LS_X;
    protected int LS_Y;
    
    public void setLS_X(int X)           { LS_X = X; }
    public void setLS_Y(int Y)           { LS_Y = Y; }
    
    public int getLS_X()                 { return LS_X; }
    public int getLS_Y()                 { return LS_Y; }
    
    /*
    protected int [] BarraSkills = new int [MiscData.PC_BARRASKILLS_MaxSkills];
    protected char [] BarraKeybinds = new char [MiscData.PC_BARRASKILLS_MaxSkills];
    
    public int[] BarraSkills()                              { return BarraSkills; }
    public char[] BarraKeybinds()                           { return BarraKeybinds; }*/
    
    public Player ()
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar();
                while (true) 
                {
                    Update();
                    try {Thread.sleep(MiscData.MAP3D_REFRESH_RATE);} 
                    catch (InterruptedException e) {return;}
                }
            }
        }).start();
    }
    
    
    @Override
    public void Moverse()
    {
        //if (KeyInputManager.Nada())  {Velocidad=0; FrameActual =0;}
        if (Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))      
        {
            Y=  (Y+ (Math.sin(Math.toRadians(90d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (FrameActual*TamañoSprite,0);
        }
        if (Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))
        {
            Y=  (Y+ (Math.sin(Math.toRadians(270d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (FrameActual*TamañoSprite,3*TamañoSprite);
        }
        if (Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))       
        {   
            X=  (X+ (Math.cos(Math.toRadians(0d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (FrameActual*TamañoSprite,2*TamañoSprite);
        }
        if (Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))
        {  
            X=  (X+ (Math.cos(Math.toRadians(180d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (FrameActual*TamañoSprite,1*TamañoSprite);
        }
        if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.A))   
        {
            Y=  (Y+ (Math.sin(Math.toRadians(135d))*VelocidadMax)*VelocidadMod);
            X=  (X+ (Math.cos(Math.toRadians(135d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (3*TamañoSprite+FrameActual*TamañoSprite,0*TamañoSprite);
        }
        if (Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.D))   
        {
            Y=  (Y+ (Math.sin(Math.toRadians(45d))*VelocidadMax)*VelocidadMod);
            X=  (X+ (Math.cos(Math.toRadians(45d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (3*TamañoSprite+FrameActual*TamañoSprite,2*TamañoSprite);
        }
        if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.A))   
        {
            Y=  (Y+ (Math.sin(Math.toRadians(225d))*VelocidadMax)*VelocidadMod);
            X=  (X+ (Math.cos(Math.toRadians(225d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (3*TamañoSprite+FrameActual*TamañoSprite,1*TamañoSprite);
        }
        if (Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D))   
        {
            Y=  (Y+ (Math.sin(Math.toRadians(315d))*VelocidadMax)*VelocidadMod);
            X=  (X+ (Math.cos(Math.toRadians(315d))*VelocidadMax)*VelocidadMod);
            setFrameActualXY (3*TamañoSprite+FrameActual*TamañoSprite,3*TamañoSprite);
        }
        if (KeyInputManager.Espacio) {}
    }
    
    public void CambiarSkillActivo()
    {  
        for (int i=0;i<BarraKeybinds().length;i++)
        {
            if (BarraKeybinds()[i] =='1' && KeyInputManager.k1) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='2' && KeyInputManager.k2) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='3' && KeyInputManager.k3) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='4' && KeyInputManager.k4) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='5' && KeyInputManager.k5) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='6' && KeyInputManager.k6) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='7' && KeyInputManager.k7) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='8' && KeyInputManager.k8) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='9' && KeyInputManager.k9) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='0' && KeyInputManager.k0) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='q' && KeyInputManager.kQ) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='Q' && KeyInputManager.kQ) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='e' && KeyInputManager.kE) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='E' && KeyInputManager.kE) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='r' && KeyInputManager.kR) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='R' && KeyInputManager.kR) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='t' && KeyInputManager.kT) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='T' && KeyInputManager.kT) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='f' && KeyInputManager.kF) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='F' && KeyInputManager.kF) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='g' && KeyInputManager.kG) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='G' && KeyInputManager.kG) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='z' && KeyInputManager.kZ) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='Z' && KeyInputManager.kZ) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='x' && KeyInputManager.kX) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='X' && KeyInputManager.kX) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='c' && KeyInputManager.kC) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='C' && KeyInputManager.kC) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='v' && KeyInputManager.kV) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='V' && KeyInputManager.kV) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='b' && KeyInputManager.kB) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='B' && KeyInputManager.kB) SkillSeleccionado = BarraSkills()[i];
            
            if (BarraKeybinds()[i] =='1' && Gdx.input.isKeyPressed(Keys.NUM_1)) SkillSeleccionado = BarraSkills()[i];
            if (BarraKeybinds()[i] =='1' && Gdx.input.isKeyPressed(Keys.NUM_1)) SkillSeleccionado = BarraSkills()[i];
        }
    }
    
    public void Castear()
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                int CastingTime = 0;
                setCasteandoSpell(true);
                setTotalCastingTime((int)ListadeSkills.get(SkillSeleccionado).Stats()[0].getValorTotal());
                while (CastingTime<(int)ListadeSkills.get(SkillSeleccionado).Stats()[0].getValorTotal()) 
                {   
                    CastingTime = CastingTime + MiscData.MAP3D_REFRESH_RATE;
                    setActualCastingTime(CastingTime);
                    
                    try {Thread.sleep(MiscData.MAP3D_REFRESH_RATE);} 
                    catch (InterruptedException e) {return;}
                }
                setCasteandoSpell(false);
            }
        }).start();
    }
    
    
    //@Override
    public void Update ()
    {
        UpdateContadoresAnimacion();
        oldPositionX=X;oldPositionY=Y;
        Moverse();
        //Hay que hacer que hacer que se active el metodo CambiarSkillActivo solo cuando se detecte la pulsacion de una tecla:
        /*if (KeyInputManager.CambiarSkillActivo())*/ CambiarSkillActivo();
        if (Gdx.input.isButtonPressed(Buttons.LEFT) && SkillSeleccionado >= 0 && !isCasteandoSpell()) 
        {
            //new SpellCast(ListadeSkills.get(SkillSeleccionado), Myrran.getMundo().getPlayer(), MouseX, MouseY);
            Castear();
            PlayerCast pCast = new PlayerCast();
            pCast.SkillSeleccionado=SkillSeleccionado;
            pCast.MouseX=Gdx.input.getX();
            pCast.MouseY=Gdx.input.getY();
            Myrran.client.client.sendTCP(pCast);
        }
        //if (MouseInputManager.Boton3 && SkillSeleccionado >= 0) {new SpellCast(ListadeSkills.get(SkillSeleccionado), Myrran.getMundo().getPlayer(), MouseX, MouseY);}
        if (CheckColisonMuro ()) {X=oldPositionX;Y=oldPositionY;}
        
        /*if (oldPositionX != X)
        {
        Ghost ghost = new Ghost();
        ghost.X=(int)X;
        ghost.Y=(int)Y;
        if (cliente.client.isConnected()) { cliente.client.sendTCP(ghost); }
        else try {cliente.client.reconnect();} catch (Exception e) {};
        }*/
         
    }
}
