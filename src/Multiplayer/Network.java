/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Mobiles.NPC;
import Mobiles.PC;
import Mobiles.Player;
import Mobiles.PlayerMP;
import Mobiles.Proyectil;
import Utils.Coordenadas;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import java.util.ArrayList;


/**
 *
 * @author Hanto
 */
public class Network 
{
    static public final int port = 54555;
    
    static public void register (EndPoint endPoint)
    {
        Kryo kryo = endPoint.getKryo();      
        kryo.register(Coordenadas.class);
        kryo.register(ArrayList.class);
        kryo.register(Integer.class);
        kryo.register(int[].class);
        
        kryo.register(addGhostPlayer.class);
        kryo.register(removeGhostPlayer.class);
        kryo.register(actualizarGhostPlayer.class);
        kryo.register(actualizarAurasGhostPlayer.class);
        
        kryo.register(addGhostNPC.class);
        kryo.register(removeGhostNPC.class);
        kryo.register(actualizarGhostNPC.class);
        kryo.register(actualizarAurasGhostNPC.class);
        
        kryo.register(addGhostPepo.class);
        kryo.register(removeGhostPepo.class);
        
        kryo.register(actualizarPlayerPos.class);
        
        kryo.register(PlayerCast.class);
    }
    
    static public class addGhostPlayer
    {
        public String ID;
        public String imgFilename;
        public Coordenadas PosXY = new Coordenadas ();
            
        public int ActualHP;
        public int TotalHP;
        
        public int[] ListadeAurasPorID;
        
        public addGhostPlayer () {}
        public addGhostPlayer (PlayerMP player)
        {
            ID = Integer.toString(player.getConnectionID());
            imgFilename = player.getImgFilename();
            PosXY.setXY(player.getX(), player.getY());
            ActualHP = player.getActualHPs();
            TotalHP = player.getTotalHps();
            
            ListadeAurasPorID = new int [player.ListadeAuras().size()];
            for (int i=0;i<player.ListadeAuras().size();i++)
            {
                ListadeAurasPorID[i]=(player.ListadeAuras().get(i).getID());
            }
        }      
    }
    
    static public class addGhostNPC
    {
        public String ID;
        public String imgFilename;
        public Coordenadas PosXY = new Coordenadas ();
            
        public int ActualHP;
        public int TotalHP;
        
        public int[] ListadeAurasPorID;
        
        public addGhostNPC () {}
        public addGhostNPC (NPC npc)
        {
            ID = npc.toString();
            imgFilename = npc.getImgFilename();
            PosXY.setXY(npc.getX(), npc.getY());
            ActualHP = npc.getActualHPs();
            TotalHP = npc.getTotalHps();
            
            ListadeAurasPorID = new int [npc.ListadeAuras().size()];
            for (int i=0;i<npc.ListadeAuras().size();i++)
            {
                ListadeAurasPorID[i]=(npc.ListadeAuras().get(i).getID());
            }
        }      
    }
    
    static public class addGhostPepo
    {
        public String ID;
        public String imgFilename;
        public Coordenadas AnimacionBase = new Coordenadas ();
        public Coordenadas PosXY = new Coordenadas ();
        public double Direccion;
        public double velocidadMax;
        public int Duracion;
        public int DuracionMax;
        
        public addGhostPepo () {}
        public addGhostPepo (Proyectil pepo)
        {
            ID = pepo.toString();
            imgFilename = pepo.getImgFilename();
            AnimacionBase = pepo.getAnimacionBase();
            PosXY.setXY(pepo.getX(), pepo.getY());
            Direccion = pepo.getDireccion();
            velocidadMax = pepo.getVelocidadMax();
            Duracion = pepo.getDuracion();
            DuracionMax = pepo.getDuracionMaxima();
        }      
    }
    
    static public class removeGhostPlayer
    {
        public String ID;
        
        public removeGhostPlayer () {}
        public removeGhostPlayer (PlayerMP player)
        {
            ID = Integer.toString(player.getConnectionID());
        }
    }
    
    static public class removeGhostNPC
    {
        public String ID;
        
        public removeGhostNPC () {}
        public removeGhostNPC (NPC npc)
        {
            ID = npc.toString();
        }
    }
    
    static public class removeGhostPepo
    {
        public String ID;
        
        public removeGhostPepo () {}
        public removeGhostPepo (Proyectil pepo)
        {
            ID = pepo.toString();
        }
    }
    
    static public class actualizarGhostPlayer 
    {
        public String ID;
        public Coordenadas PosXY = new Coordenadas ();
        public int ActualHP;
        public Boolean isCasteandoSpell;
        public int ActualCastingTime;
        public int TotalCastingTime;
        
        public actualizarGhostPlayer () {}
        public actualizarGhostPlayer (PC player)
        {
            ID=Integer.toString(player.getConnectionID());
            PosXY.X = player.getPiesX();
            PosXY.Y = player.getPiesY();
            ActualHP = player.getActualHPs();
            isCasteandoSpell = player.isCasteandoSpell();
            ActualCastingTime = player.getActualCastingTime();
            TotalCastingTime = player.getTotalCastingTime();
            
            player.setLX(PosXY.X);
            player.setLY(PosXY.Y);
            player.setLActualHPs(ActualHP);
            player.setIsLCasteandoSpell(isCasteandoSpell);
            player.setLActualCastingTime(ActualCastingTime);
            player.setLTotalCastingTime(TotalCastingTime);
        }
    }
    
    static public class actualizarGhostNPC 
    {
        public String ID;
        public Coordenadas PosXY = new Coordenadas ();
        public int ActualHP;
        public Boolean isCasteandoSpell;
        public int ActualCastingTime;
        public int TotalCastingTime;
        
        public double DireccionVisual;
        
        public actualizarGhostNPC () {}
        public actualizarGhostNPC (NPC npc)
        {
            ID=npc.toString();
            PosXY.X = npc.getPiesX();
            PosXY.Y = npc.getPiesY();
            ActualHP = npc.getActualHPs();
            isCasteandoSpell = npc.isCasteandoSpell();
            ActualCastingTime = npc.getActualCastingTime();
            TotalCastingTime = npc.getTotalCastingTime();
            
            DireccionVisual = npc.getDireccion();
        }
    }
    
    static public class actualizarAurasGhostNPC
    {
        public String ID;
        public int[] ListadeAurasPorID;
        
        public actualizarAurasGhostNPC () {}
        public actualizarAurasGhostNPC (NPC npc)
        {   
            ID = npc.toString();
            ListadeAurasPorID = new int [npc.ListadeAuras().size()];
            for (int i=0;i<npc.ListadeAuras().size();i++)
            {
                ListadeAurasPorID[i]=(npc.ListadeAuras().get(i).getID());
            }
        }      
    }
    
    static public class actualizarAurasGhostPlayer
    {
        public String ID;
        public int[] ListadeAurasPorID;
        
        public actualizarAurasGhostPlayer () {}
        public actualizarAurasGhostPlayer (PlayerMP player)
        {   
            ID = Integer.toString(player.getConnectionID());
            ListadeAurasPorID = new int [player.ListadeAuras().size()];
            for (int i=0;i<player.ListadeAuras().size();i++)
            {
                ListadeAurasPorID[i]=(player.ListadeAuras().get(i).getID());
            }
        }      
    }
    
    static public class actualizarPlayerPos
    {
        public int X;
        public int Y;
        
        public actualizarPlayerPos () {} 
        public actualizarPlayerPos (Player player)
        {
            X=player.getX();
            Y=player.getY();
            
            player.setLS_X(X);
            player.setLS_Y(Y);
        }
    }
    
    static public class PlayerCast
    {
        public int SkillSeleccionado;
        public int MouseX;
        public int MouseY;
    }
    
}
