/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Skills;

import Main.Myrran;
import java.util.ArrayList;

/**
 *
 * @author Hanto
 */
public class TipoSpell 
{
    private int ID;
    private String Nombre;
    
    ArrayList <StatSpell> Stats = new ArrayList<StatSpell>();
        
    public int getID()                      { return ID; }
    public String getNombre()               { return Nombre; }
    public ArrayList <StatSpell> Stats()    { return Stats; }
    
    public void setNombre (String s)        { Nombre = s; }
    
    
    public TipoSpell ()
    {
        if (Myrran.getMundo().ListadeTiposSpells().isEmpty()) ID = 0;
        else {ID = Myrran.getMundo().ListadeTiposSpells().get(Myrran.getMundo().ListadeTiposSpells().size()-1).getID()+1;}
    }
}
