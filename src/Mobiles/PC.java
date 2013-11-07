/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobiles;

import IO.KeyInputManager;
import DATA.MiscData;
import IO.MouseInputManager;
import static IO.MouseInputManager.MouseX;
import static IO.MouseInputManager.MouseY;
import Main.Myrran;
import Skills.SpellCast;
import Skills.SpellTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Hanto
 */
public class PC extends Personaje
{   
    protected int ConnectionID;
    
    protected int [] BarraSkills = new int [MiscData.PC_BARRASKILLS_MaxSkills];
    protected char [] BarraKeybinds = new char [MiscData.PC_BARRASKILLS_MaxSkills];
    
    public int[] BarraSkills()                              { return BarraSkills; }
    public char[] BarraKeybinds()                           { return BarraKeybinds; }
    public void setConnectionID(int cID)                    { ConnectionID = cID; }
    public int getConnectionID ()                           { return ConnectionID; }
    
    public void Inicializar ()
    {   
        LoadImage(MiscData.SPRITE_IMGFILENAME_PLAYER);
        setPosXY(MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2, MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2);
        //Myrran.getMundo().ListadePlayers().add(this);
        VelocidadMax=MiscData.SPRITE_MAXVELOCIDADPLAYER;
        isPlayerFriend=Boolean.TRUE;
        isPlayer=Boolean.TRUE;
        
        TotalHPs=2000;
        ActualHPs=2000;
        
        for (int i=0; i<Myrran.getMundo().ListadeSkills().size();i++)
        {
            SpellTemplate spellOrigen = Myrran.getMundo().ListadeSkills().get(i);
            SpellTemplate spellDestino = new SpellTemplate();
            SpellTemplate.copiarSpell(spellOrigen, spellDestino, true);
            ListadeSkills.add(spellDestino);
        }
        
        for (int i=0; i<ListadeSkills.size();i++) { BarraSkills()[i] = ListadeSkills.get(i).getID(); }
        for (int i=ListadeSkills.size(); i<BarraSkills.length;i++) BarraSkills[i]=-1;
        
        for (int i=0; i<ListadeSkills.size();i++) { BarraKeybinds()[i] = ListadeSkills.get(i).getKeybind(); }
        for (int i=ListadeSkills.size(); i<BarraKeybinds().length;i++) BarraKeybinds()[i]='Ø';
    }
    
    @Override
    public void Moverse()
    {
        
    }        
    
    
    public void Update ()
    {
        UpdateContadoresAnimacion();
        oldPositionX=X;oldPositionY=Y;
        Moverse();
        if (CheckColisonMuro ()) {X=oldPositionX;Y=oldPositionY;}    
    }
    
}
