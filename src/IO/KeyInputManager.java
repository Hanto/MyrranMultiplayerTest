/*
 * http://www.dreamincode.net/forums/blog/867/entry-2210-an-input-manager-class/
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import static IO.MouseInputManager.MouseX;
import static IO.MouseInputManager.MouseY;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Hanto
 */
public class KeyInputManager implements KeyListener {
	     
    public static boolean Izda = false;
    public static boolean Dcha = false;
    public static boolean Arriba = false;
    public static boolean Abajo = false;
    public static boolean Espacio = false;
    
    public static boolean k1 = false;
    public static boolean k2 = false;
    public static boolean k3 = false;
    public static boolean k4 = false;
    public static boolean k5 = false;
    public static boolean k6 = false;
    public static boolean k7 = false;
    public static boolean k8 = false;
    public static boolean k9 = false;
    public static boolean k0 = false;
    
    public static boolean kQ = false;
    public static boolean kE = false;
    public static boolean kR = false;
    public static boolean kT = false;
    public static boolean kF = false;
    public static boolean kG = false;
    public static boolean kZ = false;
    public static boolean kX = false;
    public static boolean kC = false;
    public static boolean kV = false;
    public static boolean kB = false;
    
    
    public static boolean Nada ()
    {
        return (!Izda && !Dcha && !Arriba && !Abajo);
    }
    
    public static boolean CambiarSkillActivo ()
    {
        return (k1 || k2 || k3 || k4 || k5 || k6 || k7 || k8 || k9 || k0 || kQ || kE || kR || kT || kF || kG || kZ || kX || kC || kV || kB);
    }        
    
    @Override
    public void keyPressed (KeyEvent evt)
    {
        int keyCode = evt.getKeyCode();
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {}
        else setEstado(keyCode, UIManager.UIKeyboard(keyCode));
    }        
    
    @Override
    public void keyReleased(KeyEvent evt)
    {
        int keyCode = evt.getKeyCode();
        setEstado(keyCode, false);       
    }
    
    @Override
    public void keyTyped (KeyEvent evt) {}
    
    void setEstado (int KeyCode, boolean estado)
    {
        switch (KeyCode)
        {
            case KeyEvent.VK_LEFT:  {Izda = estado;break;}
            case KeyEvent.VK_A:     {Izda = estado;break;}
            case KeyEvent.VK_RIGHT: {Dcha = estado;break;}
            case KeyEvent.VK_D:     {Dcha = estado;break;}
            case KeyEvent.VK_DOWN:  {Abajo = estado;break;}
            case KeyEvent.VK_S:     {Abajo = estado;break;}
            case KeyEvent.VK_UP:    {Arriba = estado;break;}
            case KeyEvent.VK_W:     {Arriba = estado;break;}    
            case KeyEvent.VK_SPACE: {Espacio = estado;break;}
                
            case KeyEvent.VK_1:     {k1= estado; break;}
            case KeyEvent.VK_2:     {k2= estado; break;}
            case KeyEvent.VK_3:     {k3= estado; break;}
            case KeyEvent.VK_4:     {k4= estado; break;}
            case KeyEvent.VK_5:     {k5= estado; break;}
            case KeyEvent.VK_6:     {k6= estado; break;}
            case KeyEvent.VK_7:     {k7= estado; break;}
            case KeyEvent.VK_8:     {k8= estado; break;}
            case KeyEvent.VK_9:     {k9= estado; break;}
            case KeyEvent.VK_0:     {k0= estado; break;}
                
            case KeyEvent.VK_Q:     {kQ= estado; break;}
            case KeyEvent.VK_E:     {kE= estado; break;}
            case KeyEvent.VK_R:     {kR= estado; break;}
            case KeyEvent.VK_T:     {kT= estado; break;}
            case KeyEvent.VK_F:     {kF= estado; break;}
            case KeyEvent.VK_G:     {kG= estado; break;}
            case KeyEvent.VK_Z:     {kZ= estado; break;}
            case KeyEvent.VK_X:     {kX= estado; break;}
            case KeyEvent.VK_C:     {kC= estado; break;}
            case KeyEvent.VK_V:     {kV= estado; break;}
            case KeyEvent.VK_B:     {kB= estado; break;}
        }
    }
}
