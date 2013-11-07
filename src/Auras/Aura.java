/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auras;

import DATA.MiscData;
import DATA.SpellData;
import Main.Myrran;
import Mobiles.NPC;
import Mobiles.PC;
import Mobiles.Personaje;
import Mobiles.PlayerMP;
import Multiplayer.Network.actualizarAurasGhostNPC;
import Multiplayer.Network.actualizarAurasGhostPlayer;

/**
 *
 * @author Hanto
 */
public class Aura 
{
    protected int ID;
    protected String Nombre;
    protected int Duracion=0;
    protected int DuracionMax;
    protected Personaje Target;
    protected Personaje Owner;
    protected Boolean isDebuff;
    protected Boolean isBuff;
    
    public void setID (int i)                           { ID = i; }
    public void setNombre (String nombre)               { Nombre = nombre; }
    public void setDuracion (int duracion )             { Duracion = duracion; }
    public void setDuracionMax(int duracionMax)         { DuracionMax = duracionMax; }
    public void setTarget (Personaje target)            { Target = target; }
    public void setOwner (Personaje owner )             { Owner = owner; }
    public void setIsDebuff (Boolean isdebuff)          { isDebuff = isdebuff; isBuff = !isdebuff; }
    public void setIsBuff (Boolean isbuff)              { isBuff = isbuff; isDebuff = !isbuff; }
    
    public int getDuracion ()                           { return Duracion; }
    public int getDuracionMax ()                        { return DuracionMax; }
    public int getID ()                                 { return ID; }
    public Personaje getOwner()                         { return Owner; }
    
    
    public void Inicializar (Personaje Target)
    {   //los dos metodos Inicializar y Expirar no deben ser OVERRIDED por los Debuffs y Buffs especificos ya que contienen los metodos de sincronizacion Multiplayer
        //asi como los de visualizacion. Para eso se duplican los metodos de inicializacion y destruccion dentro de la propia clase especifica
        synchronized (Target.ListadeAuras()) {
        this.Target=Target;
        if (!this.Target.ListadeAuras().contains(this)) Target.ListadeAuras().add(this); }
        ActualizarAurasAClientes();
    }
    public void Expirar ()      
    { 
        synchronized (Target.ListadeAuras()) {
        Duracion=DuracionMax+1;
        if (Target.ListadeAuras().contains(this)) Target.ListadeAuras().remove(this);}
        ActualizarAurasAClientes();
    }
    
    public void ActualizarAurasAClientes ()
    {   //Cuando un aura se aplica o desaparece, se chequea si el mob o player que tiene ese aura esta en la lista de render de algun jugador
        //en caso afirmativo se le manda su lista de auras completa actualizada
        synchronized (Myrran.getMundo().ListadePlayers()) {
        for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
        {
            PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
            if (player.ListadeMobsCercanos().contains(Target))
            {
                actualizarAurasGhostNPC aGhost = new actualizarAurasGhostNPC((NPC)Target);
                Myrran.server.server.sendToTCP(player.getConnectionID(), aGhost);
            }
            if (player.ListadePlayersCercanos().contains(Target))
            {
                actualizarAurasGhostPlayer aGhost = new actualizarAurasGhostPlayer((PlayerMP)Target);
                Myrran.server.server.sendToTCP(player.getConnectionID(), aGhost);
            }
        } }
    }
    
    public void Consumirse()    
    { 
        Duracion=Duracion+MiscData.SPELL_UPDATE_RATE; 
    }
}
