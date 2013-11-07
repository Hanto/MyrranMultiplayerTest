/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geo;

import Auras.Aura;
import Graphics.Plantilla;
import Mobiles.NPC;
import Mobiles.Proyectil;
import DATA.MiscData;
import Graphics.CombatText;
import Mobiles.PC;
import Main.Myrran;
import MobGhost.GhostNPC;
import MobGhost.GhostPepo;
import MobGhost.GhostPlayer;
import Mobiles.Player;
import Mobiles.PlayerMP;
import Skills.AuraTemplate;
import Skills.SpellTemplate;
import Skills.TipoSpell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Hanto
 */
public class Mundo 
{
    public Player player;
    public static Mapa mapa;
    
    private ArrayList <Terreno> ListadeTerrenos = new ArrayList <Terreno>(50);
    private ArrayList <Estatico> ListadeEstaticos = new ArrayList <Estatico>(50);
    private ArrayList <Mapa> ListadeMapas = new ArrayList <Mapa>(5);
    private ArrayList <Plantilla> ListadePlantillas = new ArrayList <Plantilla>(10);
    private ArrayList <SpellTemplate> ListadeSkills = new ArrayList <SpellTemplate>(50);
    private ArrayList <AuraTemplate> ListadeAuras = new ArrayList <AuraTemplate>(50);
    private ArrayList <TipoSpell> ListadeTiposSpells = new ArrayList <TipoSpell>(10);
    private ArrayList <TipoSpell> ListadeTiposAuras = new ArrayList <TipoSpell>(10);
    //En el servidor:
    private List <PlayerMP> ListadePlayers = Collections.synchronizedList (new ArrayList <PlayerMP>(10));
    //En los Clientes:
    private List <GhostPlayer> ListadeGhostPlayersCercanos = Collections.synchronizedList (new ArrayList <GhostPlayer>(10));
    private List <GhostNPC> ListadeGhostNPCsCercanos = Collections.synchronizedList (new ArrayList <GhostNPC>(30));
    private List <GhostPepo> ListadeGhostPeposCercanos = Collections.synchronizedList (new ArrayList <GhostPepo>(50));
    
    private List <CombatText> ListaSCT = Collections.synchronizedList( new ArrayList<CombatText>(50));
    
    private int MapaMaxY = MiscData.MUNDO_MAPAMAXY;
    private int MapaMaxX = MiscData.MUNDO_MAPAMAXX;
    
    public int getMapaMaxY ()                                   { return MapaMaxY;}
    public int getMapaMaxX ()                                   { return MapaMaxX;}
    public Player getPlayer()                                   { return player; }
    
    public ArrayList <Terreno> ListaTerrenos ()                 { return ListadeTerrenos;}
    public ArrayList <Estatico> ListaEstaticos ()               { return ListadeEstaticos; }
    public ArrayList <Mapa> ListaMapas ()                       { return ListadeMapas;}
    public ArrayList <Plantilla> ListaPlantillas ()             { return ListadePlantillas; }
    public ArrayList <SpellTemplate> ListadeSkills ()           { return ListadeSkills; }
    public ArrayList <AuraTemplate> ListadeAuras ()             { return ListadeAuras; }
    public ArrayList <TipoSpell> ListadeTiposSpells ()          { return ListadeTiposSpells; }
    public ArrayList <TipoSpell> ListadeTiposAuras ()           { return ListadeTiposAuras; }
    
    public List <PlayerMP> ListadePlayers ()                    { return ListadePlayers; }
    public List <GhostPlayer> ListadeGhostPlayersCercanos ()    { return ListadeGhostPlayersCercanos; }
    public List <GhostNPC> ListadeGhostNPCsCercanos ()          { return ListadeGhostNPCsCercanos; }
    public List <GhostPepo> ListadeGhostPeposCercanos ()        { return ListadeGhostPeposCercanos; }
    
    public List <CombatText> ListaSCT ()                        { return ListaSCT; }
    
    public Mundo ()
    {
       
    }
    
    public static SpellTemplate getSpell (int spellID)
    {
        for (int i=0;i<Myrran.getMundo().ListadeSkills().size();i++)
        {
            if (Myrran.getMundo().ListadeSkills().get(i).getID()==spellID) 
                {return Myrran.getMundo().ListadeSkills().get(i);}
        }
        return null;
    }
    
    public static int getSpellID (String nombreSpell)
    {
        for (int i=0;i<Myrran.getMundo().ListadeSkills().size();i++)
        {
            if (Myrran.getMundo().ListadeSkills().get(i).getNombre().equals(nombreSpell)) 
                {return i;}
        }
        return -1;
    }
    
    public static int getAuraID (String nombreAura)
    {
        for (int i=0;i<Myrran.getMundo().ListadeAuras().size();i++)
        {
            if (Myrran.getMundo().ListadeAuras().get(i).getNombre().equals(nombreAura)) 
                {return i;}
        }
        return -1;
    }
    
    public static AuraTemplate getAura (int AuraID)
    {
        for (int i=0;i<Myrran.getMundo().ListadeAuras().size();i++)
        {
            if (Myrran.getMundo().ListadeAuras().get(i).getID() == AuraID) 
                {return Myrran.getMundo().ListadeAuras().get(i);}
        }
        return null;
    }
    
    
    public Boolean BuscarTerrenoEnTodosLosMapas (int id)
    {
        for (int i=0;i<ListadeMapas.size();i++)
        {
            if (ListadeMapas.get(i).BuscarTerrenoEnMapa(id)) {return Boolean.TRUE;}
        }
        return Boolean.FALSE;
    }
    
    public Boolean BuscarEstaticoEnTodosLosMapas (int id)
    {
        for (int i=0;i<ListadeMapas.size();i++)
        {
            if (ListadeMapas.get(i).BuscarEstaticoEnMapa(id)) {return Boolean.TRUE;}
        }
        return Boolean.FALSE;
    }
    
}
