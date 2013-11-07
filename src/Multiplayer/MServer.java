/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Main.Myrran;
import Mobiles.PlayerMP;
import Multiplayer.Network.PlayerCast;
import Multiplayer.Network.actualizarPlayerPos;
import Multiplayer.Network.removeGhostPlayer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
/**
 *
 * @author Hanto
 */
public class MServer 
{
    public Server server;
    
    public MServer () throws IOException
    {
        server = new Server();
        Network.register(server);
        //Log.TRACE();
        
        
        server.addListener(new ThreadedListener(new Listener()
        {
            public void connected (Connection con)
            {
                ServerAddPlayer(con.getID());
            }
            
            public void received (Connection con, Object object)
            {
                if (object instanceof actualizarPlayerPos)
                {
                    int X = ((actualizarPlayerPos)object).X;
                    int Y = ((actualizarPlayerPos)object).Y;
                    ServerActualizarPlayerPos (con.getID(), X, Y);
                }
                
                if (object instanceof PlayerCast)
                {
                    PlayerCast pCast = (PlayerCast)object;
                    ServerPlayerCast (con.getID(), pCast);   
                }
            }
            
            public void disconnected (Connection con)
            {
                ServerRemovePlayer(con.getID());
            }
            
        }));
        
        server.bind(Network.port);
        server.start();
    }
    
    
    public static void ServerPlayerCast (int conID, PlayerCast pCast)
    {   //le enviamos la peticion a nuestro avatar que castee tan pronto como pueda el skill seleccionado (debe hacerse asi como la ligera desincronia entre barras de casteo y cooldowns)
        for (int i=0; i<Myrran.getMundo().ListadePlayers().size();i++)
        {
            PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
            if (player.getConnectionID() == conID)
            {
                player.setSkillSeleccionado(pCast.SkillSeleccionado);
                player.MouseX=pCast.MouseX;
                player.MouseY=pCast.MouseY;
                player.castearASAP=true;
            }
        }
    }
    
    public static void ServerActualizarPlayerPos (int conID, int X, int Y)
    {   //Buscamos al jugador con ese ID, y actualizamos la posicion de su avatar en el servidor
        for (int i=0; i<Myrran.getMundo().ListadePlayers().size();i++)
        {
            PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
            if (player.getConnectionID() == conID)
            {
                player.setPosXY(X, Y);
            }
        }
    }
    
    public static void ServerAddPlayer (int conID)
    {   //Creamos un nuevo Thread PlayerMP para el jugador conectado, al que asignamos su ConnectionID como ID, y lo añadimos a la lista de players
        synchronized ( Myrran.getMundo().ListadePlayers()) 
        {
            PlayerMP player = new PlayerMP();
            player.setConnectionID(conID);
            Myrran.getMundo().ListadePlayers().add(player);
        }
    }
    
    public static void ServerRemovePlayer (int conID)
    {   //"Matamos" al player que desconecta, para que su thread muera y deje de añadirse a las listas de cercania de los jugadores proximos.
        //El propio metodo de muerte de PlayerMP se encargara de eliminarlo de todas las listas de Cercania de los jugadores proximos. asi como de la lista de Players del servidor
        synchronized ( Myrran.getMundo().ListadePlayers()) 
        {
            for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
            {
                if (Myrran.getMundo().ListadePlayers().get(i).getConnectionID() == conID) 
                {
                    Myrran.getMundo().ListadePlayers().get(i).setIsConectado(false);
                }
            }  
        }
        //Enviar peticion de eliminar Ghost del player de todos los clientes:
        removeGhostPlayer rGhost = new removeGhostPlayer ();
        rGhost.ID=Integer.toString(conID);
        Myrran.server.server.sendToAllTCP(rGhost);
    }
    
    public static void main (String[] args) throws IOException
    {
        new MServer();
    }
}
