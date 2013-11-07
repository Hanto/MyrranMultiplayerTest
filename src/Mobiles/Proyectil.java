package Mobiles;

import DATA.SpellData;
import DATA.MiscData;
import Graphics.RenderSprites;
import Main.Myrran;
import Multiplayer.Network;
import Multiplayer.Network.removeGhostPepo;
import Skills.AuraCast;
import Skills.AuraTemplate;
import Utils.Calculos;
import java.util.ArrayList;


/**
 *
 * @author Hanto
 */
public class Proyectil extends Mob
{
    protected int Duracion;
    protected int DuracionMaxima;
    protected int Daño;
    protected Boolean Pierces;
    protected Personaje Owner;           //el mobile que ha casteado el spell
    protected double DistanciaAPlayer;
    protected double DistanciaVision;
    protected ArrayList <AuraTemplate> AurasQueAplica = new ArrayList <AuraTemplate>();
    
    public Boolean getPierces ()                { return Pierces; }
    public int getDuracion ()                   { return Duracion; }
    public int getDuracionMaxima()              { return DuracionMaxima; }
    public Mob getOwner ()                      { return Owner; }
    public ArrayList <AuraTemplate> AurasQueAplica() { return AurasQueAplica; }
    
    public void setDaño (int i)                 { Daño = i; }
    public void setPierces(Boolean b)           { Pierces = b; }
    public void setDuracionMaxima(int i)        { DuracionMaxima = i; }
    public void setOwner(Personaje mob)         { Owner = mob; }
    
    public void Inicializar (int X, int Y)
    {
        getDistanciaVision();
        setPosXY(X,Y);
        UpdateRate=MiscData.SPELL_UPDATE_RATE;
        isProyectil=Boolean.TRUE;
    }
    
    public Proyectil (final int X, final int Y)
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar (X, Y);
                while (Duracion<DuracionMaxima) 
                {  
                    Update();
                    try {Thread.sleep(UpdateRate);} 
                    catch (InterruptedException e) {return;}
                }
                Expirar();
            }
        }).start();
    }
    
    public void getDistanciaAPlayer(PC player)  { DistanciaAPlayer = Calculos.Distancia(getPiesX(), getPiesY(), player.getPiesX(), player.getPiesY()); DistanciaAPlayer = DistanciaAPlayer/MiscData.MAP3D_TILESIZE;}
    public void getDistanciaVision()            { DistanciaVision = MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/MiscData.MAP3D_TILESIZE*1.5;}
    public void Consumirse()                    { Duracion=Duracion+(1000/MiscData.MAP3D_REFRESH_RATE); }
    
    public void Expirar()          
    { 
        synchronized (Myrran.getMundo().ListadePlayers()) 
        {
            for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
            {
                PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
                synchronized (player.ListadePeposCercanos()) {
                if (player.ListadePeposCercanos().contains(this)) player.ListadePeposCercanos().remove(this); }
            }
        }
    }
    
    @Override
    public Boolean CheckColisonMuro ()
    {   
        try 
        {
            int TileX = getCentroX()/MiscData.MAP3D_TILESIZE;
            int TileY = getCentroY()/MiscData.MAP3D_TILESIZE;
            
            if (Myrran.getMundo().ListaMapas().get(0).map()[TileX][TileY].isSolido()) return Boolean.TRUE;
            else return Boolean.FALSE;
        } catch (Exception e) {return Boolean.FALSE;}
    }
    
    public void checkColisionMob()
    {   
        if (Owner.isNPCEnemy == true) return; //Ignora los golpes de un NPC enemigo a otro NPC enemigo
        
        synchronized (((PlayerMP)Owner).ListadeMobsCercanos()) {
        for (int i=0;i<((PlayerMP)Owner).ListadeMobsCercanos().size();i++)
        {   
            Personaje target = ((PlayerMP)Owner).ListadeMobsCercanos().get(i);
            
            double targetX=target.getX();
            double targetY=target.getY();
            
            //el HITBOX del pepo esta centrado justo a partir del centro de este
            double PepoX = getCentroX();
            double PepoY = getCentroY();
            
            if ((PepoX>targetX) && (PepoY>targetY) && PepoX<(targetX+MiscData.MAP3D_TILESIZE-10) && PepoY<(targetY+MiscData.MAP3D_TILESIZE-10)) 
                { Impacto (X, Y, Owner, target); }}
        }
    }
    
    public void checkColisionPlayer()
    {
        //if (Owner.isPlayer == true) return;
        
        for (int i=0; i<Myrran.getMundo().ListadePlayers().size();i++)
        {
            PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
            
            if (Owner != player)
            {
                double targetX = player.getX();
                double targetY = player.getY();

                //el HITBOX del pepo esta centrado justo a partir del centro de este
                double PepoX = getCentroX();
                double PepoY = getCentroY();
                
                int HitBox = MiscData.SPRITE_HITBOX_Modifier;
                
                if ((PepoX>targetX+HitBox) && (PepoY>targetY+HitBox) && PepoX<(targetX+MiscData.MAP3D_TILESIZE-HitBox) && PepoY<(targetY+MiscData.MAP3D_TILESIZE-HitBox)) 
                    { Impacto (X, Y, Owner, (Personaje)player); }
            }
        }
    }
    
    public void Impacto (double X, double Y, Personaje Owner, Personaje target)
    {   
        if (target.isPlayer)
            Myrran.getMap3D().addSCT(X+MiscData.MAP3D_TILESIZE/2, Y /*+MiscData.MAP3D_TILESIZE/2*/, "-"+Daño, 2000, MiscData.SCT_PLAYER_DAMAGES_OTHER);
        if (!target.isPlayer)
            Myrran.getMap3D().addSCT(X+MiscData.MAP3D_TILESIZE/2, Y /*+MiscData.MAP3D_TILESIZE/2*/, "-"+Daño, 2000, MiscData.SCT_OTHER_DAMAGES_PLAYER);
        
        target.setActualHPs(target.getActualHPs()-Daño);
        
        if (!AurasQueAplica.isEmpty())
        {
            for (int i=0;i<AurasQueAplica.size();i++)
            {
                new AuraCast(AurasQueAplica.get(i), Owner, target);
            }
        }
        
        if (!Pierces) 
        {
            Duracion=DuracionMaxima;
            removeGhostPepo rGhost = new removeGhostPepo(this);
            Myrran.server.server.sendToAllTCP(rGhost);
        }
        
        if (!target.isPlayer) {target.TotalTiempoStagger=600;target.ActualTiempoStagger=0;}
        if (target.isPlayer) {target.TotalTiempoStagger=0;target.ActualTiempoStagger=0;}
        
        double X_KnockBack = Math.cos(Direccion)*3;
        double Y_KnockBack = Math.sin(Direccion)*3;
        
        target.setX(target.getX()+(int)X_KnockBack);
        target.setY(target.getY()+(int)Y_KnockBack);
    }
    
    
    public synchronized void setRenderable()
    {        
        synchronized (Myrran.getMundo().ListadePlayers()) 
        {
            for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
            {
                PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
                synchronized (player.ListadePeposCercanos()) {
                getDistanciaAPlayer(player);
                if (DistanciaAPlayer <= DistanciaVision && !player.ListadePeposCercanos().contains(this))
                {
                    player.ListadePeposCercanos().add(this);
                    Network.addGhostPepo aGhost = new Network.addGhostPepo(this);
                    Myrran.server.server.sendToTCP(player.ConnectionID, aGhost);
                }
                if (DistanciaAPlayer > DistanciaVision && player.ListadePeposCercanos().contains(this)) 
                { 
                    player.ListadePeposCercanos().remove(this);
                    //TODAVIA FALTA ESTO, pero no hace falta creo  yo, la vida de los pepos es de segundos, acaban muriendo, y eso solo satura mas la red
                    //Network.removeGhostPepo rGhost = new Network.removeGhostPepo (this);
                    //Myrran.server.server.sendToTCP(player.ConnectionID, rGhost);
                } }
            } 
        }
    }
    
    
    public void Update ()
    {
        setRenderable();
        Consumirse();
        UpdateContadoresAnimacion();
        checkColisionMob();
        checkColisionPlayer();
        setFrameActualXY (AnimacionBase.getX()*MiscData.MAP3D_TILESIZE+FrameActual*MiscData.MAP3D_TILESIZE, AnimacionBase.getY()*MiscData.MAP3D_TILESIZE); 
        Moverse();  
    }
}
