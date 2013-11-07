/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobiles;

import DATA.MiscData;
import Main.Myrran;
import MobGhost.GhostPlayer;
import Multiplayer.Network;
import Multiplayer.Network.addGhostPlayer;
import Multiplayer.Network.removeGhostPlayer;
import Skills.SpellCast;
import Skills.SpellTemplate;
import Utils.Calculos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Hanto
 */
public class PlayerMP extends PC
{
    public int MouseX;
    public int MouseY;
    public boolean castearASAP=false;
    
    protected List <Proyectil> ListadePeposCercanos = Collections.synchronizedList (new ArrayList <Proyectil>());
    protected List <NPC> ListadeMobsCercanos = Collections.synchronizedList (new ArrayList <NPC>());
    protected List <PC> ListadePlayersCercanos = Collections.synchronizedList (new ArrayList <PC>());
        
    public List <Proyectil> ListadePeposCercanos ()         { return ListadePeposCercanos; }
    public List <NPC> ListadeMobsCercanos ()                { return ListadeMobsCercanos; }
    public List <PC> ListadePlayersCercanos ()              { return ListadePlayersCercanos; }
    
    protected double DistanciaAPlayer;
    protected double DistanciaVision;
    
    protected Boolean isConectado=true;
    
    public void setIsConectado (Boolean b)      { isConectado = b; }
    
    public void getDistanciaAPlayer(PC player)  { DistanciaAPlayer = Calculos.Distancia(getPiesX(), getPiesY(), player.getPiesX(), player.getPiesY()); DistanciaAPlayer = DistanciaAPlayer/MiscData.MAP3D_TILESIZE;}
    public void getDistanciaVision()            { DistanciaVision = MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/MiscData.MAP3D_TILESIZE*1.5;}
    
    public void Inicializar ()
    {   
        getDistanciaVision();
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
    
    public PlayerMP ()
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar();
                while (isConectado) 
                {
                    Update();
                    try {Thread.sleep(MiscData.MAP3D_REFRESH_RATE);} 
                    catch (InterruptedException e) {return;}
                }
                Deslogear();
            }
        }).start();
    }
    
    public void setRenderable()
    {
        synchronized (Myrran.getMundo().ListadePlayers()) 
        {
            for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
            {
                PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
                getDistanciaAPlayer(player);
                synchronized (player.ListadePlayersCercanos()) {
                if (DistanciaAPlayer <= DistanciaVision && !player.ListadePlayersCercanos().contains(this) && ConnectionID != player.getConnectionID())
                {
                    player.ListadePlayersCercanos().add(this);
                    addGhostPlayer aGhost = new addGhostPlayer(this);
                    Myrran.server.server.sendToTCP(player.ConnectionID, aGhost);
                }
                if (DistanciaAPlayer > DistanciaVision && player.ListadePlayersCercanos().contains(this) && ConnectionID != player.getConnectionID()) 
                { 
                    player.ListadePlayersCercanos().remove(this);
                    removeGhostPlayer rGhost = new removeGhostPlayer (this);
                    Myrran.server.server.sendToTCP(player.ConnectionID, rGhost);
                } }
            } 
        }
    }
    
    public void Deslogear()
    {   //Se elimina de las listas de cercania de todos los jugadores cercanos, asi como de la lista de jugadores del propio servidor
        synchronized ( Myrran.getMundo().ListadePlayers()) {
        for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
        {
            PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
            synchronized (player.ListadePlayersCercanos()) {
            if (player.ListadePlayersCercanos().contains(this)) player.ListadePlayersCercanos().remove(this); }
        } 
        if (Myrran.getMundo().ListadePlayers().contains(this)) Myrran.getMundo().ListadePlayers().remove(this); }
    }
    
    public void Update ()
    {
        if (castearASAP && !isCasteandoSpell)
        {
            castearASAP=false;
            new SpellCast(ListadeSkills().get(SkillSeleccionado), this, MouseX, MouseY);
        }
        setRenderable();
        UpdateContadoresAnimacion();
        oldPositionX=X;oldPositionY=Y;
        Moverse();
        if (CheckColisonMuro ()) {X=oldPositionX;Y=oldPositionY;}    
    }
}
