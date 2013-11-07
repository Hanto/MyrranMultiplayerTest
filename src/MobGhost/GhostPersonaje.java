/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MobGhost;

import Mobiles.Mob;
import java.util.ArrayList;

/**
 *
 * @author Hanto
 */
public class GhostPersonaje extends Mob
{
    protected String ID;
    
    protected int ActualHP=10000;
    protected int TotalHP;
    
    protected Boolean isCasteandoSpell=false;
    protected int ActualCastingTime;
    protected int TotalCastingTime;
    
    protected int DestinoX;
    protected int DestinoY;
    protected double DireccionVisual;
    
    protected Boolean isCerca=true;  
 
    public ArrayList <Integer> ListadeAurasPorID = new ArrayList <Integer>(10);
    public ArrayList<Integer> ListadeAurasPorID()   { return ListadeAurasPorID; }
    
    public void setID(String id)                    { ID = id; }
    public void setActualHP (int i)                 { ActualHP = i; }
    public void setTotalHP (int i)                  { TotalHP = i; }
    public void setIsCasteandoSpell (Boolean b)     { isCasteandoSpell = b; }
    public void setActualCastingTime (int i)        { ActualCastingTime = i; }
    public void setTotalCastingTime (int i)         { TotalCastingTime = i; }
    public void setDestino(int x, int y)            { DestinoX = x; DestinoY = y; }
    public void setDireccionVisual (double d)       { DireccionVisual = d; }
    public void setIsCerca (Boolean b)              { isCerca = b; }
      
    public String getID()                           { return ID; }
    public int getActualHP ()                       { return ActualHP; }
    public int getTotalHP ()                        { return TotalHP; }
    public Boolean getIsCasteandoSpell ()           { return isCasteandoSpell; }
    public int getActualCastingTime()               { return ActualCastingTime; }
    public int getTotalCastingTime()                { return TotalCastingTime; }
    public Boolean getIsCerca()                     { return isCerca; }   
    
    
    @Override
    public Boolean CheckColisonMuro ()
    {   //Los Ghost no tienen fisica, esta la regula el servidor
        return false;
    }
    
    public void setListadeAurasPorID(int[] Auras)     
    { 
        ListadeAurasPorID = new ArrayList <Integer>(10);
        for (int i=0;i<Auras.length;i++)
        {
            ListadeAurasPorID.add(Auras[i]);
        }
    }
}
