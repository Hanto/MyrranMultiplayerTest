package Mobiles;

import IA.BuscarBFS;
import DATA.MiscData;
import DATA.SpellData;
import Main.Myrran;
import Mobiles.Mob;
import Multiplayer.Network;
import Multiplayer.Network.actualizarGhostNPC;
import Multiplayer.Network.addGhostNPC;
import Multiplayer.Network.removeGhostNPC;
import Skills.SpellCast;
import Utils.Calculos;


/**
 *
 * @author Hanto
 */
public class NPC extends Personaje
{
    protected double DistanciaAPlayer;
    protected double DistanciaVision;
    
    
    public void getDistanciaAPlayer(PC player)  { DistanciaAPlayer = Calculos.Distancia(getPiesX(), getPiesY(), player.getPiesX(), player.getPiesY()); DistanciaAPlayer = DistanciaAPlayer/MiscData.MAP3D_TILESIZE;}
    public void getDistanciaVision()            { DistanciaVision = MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/MiscData.MAP3D_TILESIZE*1.5;}
    public void Detenerse()                     { VelocidadMax =0; UpdateRate=MiscData.SPRITE_IA_WAIT_REFRESH_RATE; }
    public void Dormir()                        { UpdateRate=MiscData.SPRITE_IA_SLEEP_REFRESH_RATE; }        
    
    public void Inicializar (int X, int Y, String ImgFilename)
    {   
        getDistanciaVision();
        setPosXY(X,Y);
        LoadImage(ImgFilename);
        isNPCEnemy=Boolean.TRUE;
        VelocidadMax=MiscData.NPC_GrimReaper_VelocidadMax;
        UpdateRate=MiscData.SPRITE_IA_WAIT_REFRESH_RATE;
        TotalHPs=1000;
        ActualHPs=1000;
    }
    
    public NPC (final int X, final int Y, final String IMGFilename)
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                Inicializar (X, Y, IMGFilename);
                while (ActualHPs>0) 
                {   
                    try 
                    {
                        Update();
                    } 
                    catch (Exception e) {}
                    //Myrran.getMap3d().repaint();
                    try {Thread.sleep(UpdateRate);} 
                    catch (InterruptedException e) {return;}
                }
                Morir ();
            }
        }).start();
    }
    
    public void Morir () 
    {
        synchronized (Myrran.getMundo().ListadePlayers()) 
        {
            for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
            {
                PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
                synchronized (player.ListadeMobsCercanos()) {
                if (player.ListadeMobsCercanos().contains(this)) player.ListadeMobsCercanos().remove(this); }
            }
        }
        
        for (int i=0; i<ListadeAuras.size();i++)
            ListadeAuras.get(i).Expirar();
        
        actualizarGhostNPC aGhost = new actualizarGhostNPC(this);
        Myrran.server.server.sendToAllTCP(aGhost);
    }
    
    public void UpdateAnimacion ()
    {   
        double angulo;
        angulo = Math.toDegrees(Direccion+2*(Math.PI));
        angulo = angulo%360;
        
        if (45d<=angulo && angulo<135d) {setFrameActualXY (0*TamañoSprite+FrameActual*TamañoSprite, 0*TamañoSprite);} //Abajo
        if (135d<=angulo && angulo<225d) {setFrameActualXY (0*TamañoSprite+FrameActual*TamañoSprite, 1*TamañoSprite);} //Izda
        if (45>angulo && angulo>=0) {setFrameActualXY (0*TamañoSprite+FrameActual*TamañoSprite, 2*TamañoSprite);} //Dcha
        if (315<angulo && angulo<=360) {setFrameActualXY (0*TamañoSprite+FrameActual*TamañoSprite, 2*TamañoSprite);} //Dcha
        if (225<=angulo && angulo<315) {setFrameActualXY (0*TamañoSprite+FrameActual*TamañoSprite, 3*TamañoSprite);} //Arriba
    }
        
    public double RumboHaciaPixelXY(double X, double Y) { return Math.atan2(X,Y); }        
    
    public void RumboRectoHaciaJugador()
    {   
        int TargetX= Myrran.getMundo().getPlayer().getPiesX();
        int TargetY= Myrran.getMundo().getPlayer().getPiesY();
        
        int PiesMobX = getPiesX();
        int PiesMobY = getPiesY();
         
        Direccion = RumboHaciaPixelXY(TargetY-PiesMobY,TargetX-PiesMobX);
        UpdateRate=MiscData.SPRITE_IA_AGGRO_REFRESH_RATE;
    }
    
    public void RumboHaciaCentroTileXY(int TileX, int TileY)
    {
        int TargetX = TileX*MiscData.MAP3D_TILESIZE+MiscData.MAP3D_TILESIZE/2;
        int TargetY = TileY*MiscData.MAP3D_TILESIZE+MiscData.MAP3D_TILESIZE/2;
        
        int PiesMobX = getPiesX();
        int PiesMobY = getPiesY();
        
        Direccion = RumboHaciaPixelXY(TargetY-PiesMobY,TargetX-PiesMobX);
        UpdateRate=MiscData.SPRITE_IA_AGGRO_REFRESH_RATE;
    }
    
    public void RumboInteligenteHaciaJugador()
    {   
        numChoquesContraMuro = 0;
        int OrigenX = this.getPiesX()/MiscData.MAP3D_TILESIZE;
        int OrigenY = this.getPiesY()/MiscData.MAP3D_TILESIZE;
        
        int TargetX = Myrran.getMundo().getPlayer().getPiesX()/MiscData.MAP3D_TILESIZE;
        int TargetY = Myrran.getMundo().getPlayer().getPiesY()/MiscData.MAP3D_TILESIZE;
        
        BuscarBFS b = new BuscarBFS ();
        MapaWaypoints=b.EncontrarCamino(OrigenX, OrigenY, TargetX, TargetY);
        AutopilotActivado=true;
        WaypointActual=MapaWaypoints.size()-1;
    }
    
    public Boolean CheckearLlegadaWaypoint()
    {   
        //Pixeles exactos del centro del Tile
        int WaypointPixelX = (MapaWaypoints.get(WaypointActual).getX())*MiscData.MAP3D_TILESIZE+MiscData.MAP3D_TILESIZE/2;
        int WaypointPixelY = (MapaWaypoints.get(WaypointActual).getY())*MiscData.MAP3D_TILESIZE+MiscData.MAP3D_TILESIZE/2;
        
        int PiesMobX = getPiesX();
        int PiesMobY = getPiesY();
        
        //por el redondeo hay que admitir un desajuste de un pixel
        if (WaypointPixelX >= PiesMobX-3 && WaypointPixelX <= PiesMobX+3 && 
            WaypointPixelY >= PiesMobY-3 && WaypointPixelY <= PiesMobY+3 ) return true;
        else return false;
    }
    
    public void RumboAutopilot()
    {   
        int TileX = MapaWaypoints.get(WaypointActual).getX();
        int TileY = MapaWaypoints.get(WaypointActual).getY();
        
        RumboHaciaCentroTileXY(TileX, TileY);
    }
    
    public void setRumbo()
    {   
        if (AutopilotActivado) 
        {
            if (WaypointActual <= 0) AutopilotActivado = false;
            if (CheckearLlegadaWaypoint()) {WaypointActual--;}
            RumboAutopilot ();
        }
        else
        {
            if (DistanciaAPlayer < 12)  
                { 
                    if (numChoquesContraMuro > 20 && !AutopilotActivado) RumboInteligenteHaciaJugador();
                    //if (!AutopilotActivado) RumboInteligenteHaciaJugador();
                    if (!AutopilotActivado) RumboRectoHaciaJugador(); 
                }
            if (DistanciaAPlayer >= 12 && !AutopilotActivado) { Detenerse(); }
            if (DistanciaAPlayer >= 50) { Dormir(); }
        }
    }
    
    public void setVelocidad()
    {
     
        if (AutopilotActivado) VelocidadMax = MiscData.NPC_GrimReaper_VelocidadMax;
        else VelocidadMax = MiscData.NPC_GrimReaper_VelocidadMax;
    }
    
    public void setRenderable()
    {
        synchronized (Myrran.getMundo().ListadePlayers()) 
        {
            for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
            {
                PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
                getDistanciaAPlayer(player);
                synchronized (player.ListadeMobsCercanos()) {
                if (DistanciaAPlayer <= DistanciaVision && !player.ListadeMobsCercanos().contains(this))
                {
                    player.ListadeMobsCercanos().add(this);
                    addGhostNPC aGhost = new addGhostNPC(this);
                    Myrran.server.server.sendToTCP(player.ConnectionID, aGhost);
                }
                if (DistanciaAPlayer > DistanciaVision && player.ListadeMobsCercanos().contains(this))
                {
                    player.ListadeMobsCercanos().remove(this);
                    removeGhostNPC rGhost = new removeGhostNPC(this);
                    Myrran.server.server.sendToTCP(player.ConnectionID, rGhost);
                } }
            } 
        }
    }
    
    public Boolean Stagger()
    {
        if (TotalTiempoStagger <= 0) {return false;}
        if (TotalTiempoStagger > 0 && ActualTiempoStagger <TotalTiempoStagger) {ActualTiempoStagger=ActualTiempoStagger+MiscData.MAP3D_REFRESH_RATE;return true;}
        if (TotalTiempoStagger > 0 && ActualTiempoStagger >= TotalTiempoStagger) {ActualTiempoStagger=0;TotalTiempoStagger=0;return false;}
        return false;
    }
    
    public void Update ()
    { 
        setRenderable();
        
        setRumbo();
        
        setVelocidad();
       
        if (!Stagger())
        {
            Moverse();
            new SpellCast(1, this, Myrran.getMundo().getPlayer(). getX(), Myrran.getMundo().getPlayer().getY());
        }
        
        UpdateAnimacion();        
    }
}