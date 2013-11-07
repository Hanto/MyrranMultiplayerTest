/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobiles;

import Auras.Aura;
import Main.Myrran;
import Skills.SpellTemplate;
import java.util.ArrayList;

/**
 *
 * @author Hanto
 */
public abstract class Personaje extends Mob
{
    protected int Nivel;
    protected int TotalHPs;
    protected int ActualHPs;
    protected int HPRegen;
    
    protected int Razonamiento;
    protected int Memoria;
    protected int Voluntad;
    protected int Destreza;
    protected int Constitucion;
    
    protected Boolean isCasteandoSpell=false;
    protected int TotalCastingTime=0;
    protected int ActualCastingTime=0;
    
    protected int TotalTiempoStagger=0;
    protected int ActualTiempoStagger=0;
 
    protected int SkillSeleccionado=0;
    //LAST X,Y... ultima posicion enviada en Multiplayer.
    protected int LX;
    protected int LY;
    protected int LTotalHPs;
    protected int LActualHPs;
    protected Boolean LCasteandoSpell=false;
    protected int LTotalCastingTime=0;
    protected int LActualCastingTime=0;
    
    protected ArrayList <SpellTemplate> ListadeSkills = new ArrayList <SpellTemplate>(10);
    protected ArrayList <Aura> ListadeAuras = new ArrayList <Aura>(10);
    
    public ArrayList <SpellTemplate> ListadeSkills()        { return ListadeSkills; }
    public ArrayList <Aura> ListadeAuras()                  { return ListadeAuras; }
    public void setSkillSeleccionado(int i)                 { SkillSeleccionado = i; }
    public int getSkillSeleccionado()                       { return SkillSeleccionado; }
    
    public void setTotalHPs (int x)                         { TotalHPs = x; }
    public void setActualHPs (int x)                        { ActualHPs = x; }
    public void setCasteandoSpell (Boolean b)               { isCasteandoSpell = b; }
    public void setTotalCastingTime (int i)                 { TotalCastingTime = i; }
    public void setActualCastingTime (int i)                { ActualCastingTime = i; }
    
    public void setLX (int X)                               { LX = X; }
    public void setLY (int Y)                               { LY = Y; }
    public void setLTotalHPs (int i)                        { LTotalHPs = i; }
    public void setLActualHPs (int i)                       { LActualHPs = i; }
    public void setIsLCasteandoSpell(Boolean b)             { LCasteandoSpell = b; }
    public void setLTotalCastingTime (int i)                { LTotalCastingTime = i; }
    public void setLActualCastingTime (int i)               { LActualCastingTime = i; }
    
    public int getTotalHps()                                { return TotalHPs; }
    public int getActualHPs()                               { return ActualHPs; }
    public Boolean isCasteandoSpell ()                      { return isCasteandoSpell; }
    public int getTotalCastingTime ()                       { return TotalCastingTime; }
    public int getActualCastingTime ()                      { return ActualCastingTime; } 
    
    public int getLX()                                      { return LX; }
    public int getLY()                                      { return LY; }
    public int getLActualHPs()                              { return LActualHPs; }
    public Boolean getIsLCasteandoSpell()                   { return LCasteandoSpell; }
    public int getLTotalCastingTime()                       { return LTotalCastingTime; }
    public int getLActualCastingTime()                      { return LActualCastingTime; }
    
    
    public void sincronizaSpell (SpellTemplate spell)
    {
        for (int i=0; i<ListadeSkills.size();i++)
        {
            if (ListadeSkills.get(i).getID() == spell.getID()) 
            {
                SpellTemplate.copiarSpell(spell, ListadeSkills.get(i), false);
            }    
        }
    }
    
    public SpellTemplate getSpell (int spellID)
    {
        for (int i=0;i<ListadeSkills().size();i++)
        {
            if (ListadeSkills().get(i).getID()==spellID) 
                {return ListadeSkills().get(i);}
        }
        return null;
    }
    
    public void addSpell (int spellID)
    {
        for (int i=0; i<ListadeSkills.size();i++)
        {
            if (ListadeSkills.get(i).getID() == spellID) return;
        }
        SpellTemplate spellOrigen = Myrran.getMundo().ListadeSkills().get(spellID);
        SpellTemplate spellDestino = new SpellTemplate();
        SpellTemplate.copiarSpell(spellOrigen, spellDestino, true);
        ListadeSkills.add(spellDestino);
    }
    
    public void removeSpell (int spellID)
    {
        for (int i=0; i<ListadeSkills.size();i++)
        {
            if (ListadeSkills.get(i).getID() == spellID) ListadeSkills.remove(i);
        }
    }
    
    public void addAuradeSpell (int spellID, int numAura)
    {
        for (int i=0; i<ListadeSkills.size();i++)
        {
            if (ListadeSkills.get(i).getID() == spellID) ListadeSkills.get(i).addAura(numAura, true);
        }
    }
    
    public void removeAuradeSpell (int spellID, int numAura)
    {
        for (int i=0; i<ListadeSkills.size();i++)
        {
            if (ListadeSkills.get(i).getID() == spellID) ListadeSkills.get(i).AurasQueAplica().remove(numAura);
        }  
    }        
}
