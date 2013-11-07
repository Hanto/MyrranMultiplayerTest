/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;
import DATA.MiscData;
import Main.Myrran;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author Hanto
 */
public class MouseInputManager implements MouseListener, MouseMotionListener, MouseWheelListener {
	     
    public static boolean Boton1 = false;
    public static boolean Boton2 = false;
    public static boolean Boton3 = false;
    public static boolean Rueda = false;
   
    public static int ScrollRueda = 0;
    
    public static int MouseX;
    public static int MouseY;
    
    public static int cursorX;
    public static int cursorY;
    
    public static void BotonesOFF ()
    {
        Boton1=false;
        Boton2=false;
        Boton3=false;
    }
    
    public void setEstado (int KeyCode, boolean estado)
    {
        switch (KeyCode)
        {
            case MouseEvent.BUTTON1:        {Boton1 = estado;break;}
            case MouseEvent.BUTTON2:        {Boton2 = estado;break;}
            case MouseEvent.BUTTON3:        {Boton3 = estado;break;}
            case MouseEvent.MOUSE_WHEEL:    {Rueda = estado;break;}
        }
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        ScrollRueda = e.getWheelRotation();
        UIManager.UIMouseClick(-1, false);
    }
    
    
      @Override
    public void mouseReleased(MouseEvent e)
    {
        int MouseButton = e.getButton();
        setEstado(MouseButton, false);
        
        UIManager.MouseIsDragged = false;
    }
    
    @Override
    public void mousePressed(MouseEvent e) 
    {
        MouseX = e.getX();
        MouseY = e.getY();
  
        cursorX = (MouseX+Myrran.getMap3D().getCamara().getEsquinaSupIzdaX())/MiscData.MAP3D_TILESIZE;
        cursorY = (MouseY+Myrran.getMap3D().getCamara().getEsquinaSupIzdaX())/MiscData.MAP3D_TILESIZE;
        
        int MouseButton = e.getButton();
        setEstado(MouseButton,UIManager.UIMouseClick(MouseButton, false));    //Si se clicka en alguna parte de algun UI, devolvera false, y no contara como activacion de spell ni skill, interceptando la accion
    }
   
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        MouseX = e.getX();
        MouseY = e.getY();
        
        cursorX = (MouseX+Myrran.getMap3D().getCamara().getEsquinaSupIzdaX())/MiscData.MAP3D_TILESIZE;
        cursorY = (MouseY+Myrran.getMap3D().getCamara().getEsquinaSupIzdaY())/MiscData.MAP3D_TILESIZE;
        
        Myrran.getMap3D().setHoverX(cursorX);
        Myrran.getMap3D().setHoverY(cursorY);
        
        int MouseButton = e.getButton();
        UIManager.UIMouseClick(MouseButton, true);
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        MouseX = e.getX();
        MouseY = e.getY();
        
        cursorX = (MouseX+Myrran.getMap3D().getCamara().getEsquinaSupIzdaX())/MiscData.MAP3D_TILESIZE;
        cursorY = (MouseY+Myrran.getMap3D().getCamara().getEsquinaSupIzdaY())/MiscData.MAP3D_TILESIZE;
        
        Myrran.getMap3D().setHoverX(cursorX);
        Myrran.getMap3D().setHoverY(cursorY);
        
        UIManager.UIMouseMove();
    }

    
}
