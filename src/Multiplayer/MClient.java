/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import Main.Myrran;
import MobGhost.GhostNPC;
import MobGhost.GhostPepo;
import MobGhost.GhostPlayer;
import Mobiles.Player;
import Multiplayer.Network.actualizarAurasGhostNPC;
import Multiplayer.Network.actualizarAurasGhostPlayer;
import Multiplayer.Network.actualizarGhostNPC;
import Multiplayer.Network.actualizarGhostPlayer;
import Multiplayer.Network.addGhostNPC;
import Multiplayer.Network.addGhostPepo;
import Multiplayer.Network.addGhostPlayer;
import Multiplayer.Network.removeGhostNPC;
import Multiplayer.Network.removeGhostPepo;
import Multiplayer.Network.removeGhostPlayer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Hanto
 */
public class MClient 
{
    public Client client;
    public String Host;
    
    public MClient ()
    {
        client = new Client();
        client.start();
        Network.register(client);
        //Log.TRACE();
        
        client.addListener(new Listener.ThreadedListener(new Listener()
        {
            public void connected (Connection con)
            {
                
            }
            
            public void received (Connection con, Object object)
            {
                if (object instanceof addGhostPlayer)
                {
                    addGhostPlayer ghost = (addGhostPlayer)object;
                    ClientAddGhostPlayer (ghost);
                }
                
                if (object instanceof removeGhostPlayer)
                {
                    removeGhostPlayer ghost = (removeGhostPlayer)object;
                    ClientRemoveGhostPlayer (ghost);
                }
                
                if (object instanceof actualizarGhostPlayer)
                {
                    actualizarGhostPlayer actGhost = (actualizarGhostPlayer)object;
                    ClientActualizarGhostPlayer (actGhost);
                }
                
                if (object instanceof actualizarAurasGhostPlayer)
                {
                    actualizarAurasGhostPlayer aGhost = (actualizarAurasGhostPlayer)object;
                    ClientActualizarAurasGhostPlayer (aGhost);
                }
                
                if (object instanceof addGhostPepo)
                {
                    addGhostPepo ghost = (addGhostPepo)object;
                    ClientAddGhostPepo(ghost);
                }
                
                if (object instanceof removeGhostPepo)
                {
                    removeGhostPepo ghost = (removeGhostPepo)object;
                    ClientRemoveGhostPepo (ghost);
                }
                
                if (object instanceof addGhostNPC)
                {
                    addGhostNPC ghost = (addGhostNPC)object;
                    ClientAddGhostNPC (ghost);
                }
                
                if (object instanceof removeGhostNPC)
                {
                    removeGhostNPC ghost = (removeGhostNPC)object;
                    ClientRemoveGhostNPC (ghost);
                }
                
                if (object instanceof actualizarGhostNPC)
                {
                    actualizarGhostNPC actGhost = (actualizarGhostNPC)object;
                    ClientActualizarGhostNPC (actGhost);
                }
                
                if (object instanceof actualizarAurasGhostNPC)
                {
                    actualizarAurasGhostNPC aGhost = (actualizarAurasGhostNPC)object;
                    ClientActualizarAurasGhostNPC (aGhost);
                }
            }
            
            public void disconnected (Connection con)
            {
                
            }
            
        }));
        
        InputHost();
        try 
        {
            client.connect(5000, Host, Network.port);
	} catch (IOException ex) {}
    }
    
    public void InputHost () 
    {
        String input = (String)JOptionPane.showInputDialog(null, "Host:", "Connect to server", JOptionPane.QUESTION_MESSAGE, null, null, "localhost");
	if (input == null || input.trim().length() == 0) System.exit(1);
	Host = input.trim();
    }
    
    public void ClientAddGhostPlayer (addGhostPlayer ghost)
    {   //Creamos un Ghost en el cliente, del nuevo player conectado, y asignamos su Connection ID como ID.
        //Como los Ghosts son siempre Entidades cercanas al jugador, lo añadimos a la lista de players cercanos. (es mas, la peticion de añadir el ghost habra sido de su Avatar en el servidor, al acercarse a nuestro Jugador)
        GhostPlayer GP = new GhostPlayer(ghost.PosXY.X,ghost.PosXY.Y);
        GP.setID(ghost.ID);
        GP.LoadImage(ghost.imgFilename);
        GP.setTotalHP(ghost.TotalHP);
        GP.setActualHP(ghost.ActualHP);
        GP.setListadeAurasPorID(ghost.ListadeAurasPorID);
        Myrran.getMundo().ListadeGhostPlayersCercanos().add(GP);
    }
    
    public void ClientRemoveGhostPlayer (removeGhostPlayer ghost)
    {   //Buscamos en nuestra lista de GhostPlayers aquel cuyo ID coincida con el que deseamos eliminar, y simplemente Matamos al GhostPlayer. El metodo de muerte, ya incluye el borrado de todas las listas de cercania.
        for (int i=0;i<Myrran.getMundo().ListadeGhostPlayersCercanos().size();i++)
        {
            if (Myrran.getMundo().ListadeGhostPlayersCercanos().get(i).getID().equals(ghost.ID))
                Myrran.getMundo().ListadeGhostPlayersCercanos().get(i).setIsCerca(false);
        }
    }
    
    public void ClientActualizarGhostPlayer (actualizarGhostPlayer actGhost)
    {   //de entre todos los GhostPlayers buscamos aquel cuyo ID coincide, y actualizamos sus Datos.
        for (int i=0;i<Myrran.getMundo().ListadeGhostPlayersCercanos().size();i++)
        {
            if (Myrran.getMundo().ListadeGhostPlayersCercanos().get(i).getID().equals(actGhost.ID)) 
            {
                GhostPlayer ghost = Myrran.getMundo().ListadeGhostPlayersCercanos().get(i);

                ghost.setDestino(actGhost.PosXY.X, actGhost.PosXY.Y);
                ghost.setActualHP(actGhost.ActualHP);
                ghost.setIsCasteandoSpell(actGhost.isCasteandoSpell);
                ghost.setActualCastingTime(actGhost.ActualCastingTime);
                ghost.setTotalCastingTime(actGhost.TotalCastingTime);
            }
        }
    }
    
    public void ClientActualizarAurasGhostPlayer (actualizarAurasGhostPlayer aGhost)
    {   //Buscamos al GhostNPC cuyo ID coincide y actualizamos su vector de Auras
        for (int i=0;i<Myrran.getMundo().ListadeGhostPlayersCercanos().size();i++)
        {
            if (Myrran.getMundo().ListadeGhostPlayersCercanos().get(i).getID().equals(aGhost.ID)) 
            {
                GhostPlayer ghost = Myrran.getMundo().ListadeGhostPlayersCercanos().get(i);
                ghost.setListadeAurasPorID(aGhost.ListadeAurasPorID);
            }
        }   
    }
    
    public void ClientAddGhostPepo (addGhostPepo ghost)
    {   //Creamos un nuevo GhostPepo y lo lanzamos, no necesitamos actualizar su posicion, ya que es como una bala, una vez sabemos su trayectoria y velocidad, sabemos que camino va a seguir.
        //La unica actualizacion que le daremos sera para indicarle un choque con un muro, en cuyo caso le daremos la orden de impacto/colisision, en esas coordenadas.
        GhostPepo GP = new GhostPepo (ghost.PosXY.X,ghost.PosXY.Y);
        GP.LoadImage(ghost.imgFilename);
        GP.setAnimacionBase(ghost.AnimacionBase);
        GP.setID(ghost.ID);
        GP.setDireccion(ghost.Direccion);
        GP.setVelocidadMax(ghost.velocidadMax);
        GP.setDuracion(ghost.Duracion);
        GP.setDuracionMax(ghost.DuracionMax);
        Myrran.getMundo().ListadeGhostPeposCercanos().add(GP);
    }
    
    public void ClientRemoveGhostPepo (removeGhostPepo ghost)
    {
        for (int i=0;i<Myrran.getMundo().ListadeGhostPeposCercanos().size();i++)
        {   //Buscamos al pepo y para eliminarlo, simplemente reducimos su velocidad a cero para que de la sensacion que ha chocado contra algo, 
            //sera el mismo pepo el que ejecutara su metodo de muerte al acabar su duracion y se borrara de todas las listas de cercania
            if (Myrran.getMundo().ListadeGhostPeposCercanos().get(i).getID().equals(ghost.ID))
                Myrran.getMundo().ListadeGhostPeposCercanos().get(i).setVelocidadMax(0);
        }
    }
    
    public void ClientAddGhostNPC (addGhostNPC ghost)
    {   //Creamos un nuevo GhostNPC con los datos suministrados, y lo añadimos a la lista de GhostNPCcercanos para que sea renderizado
        GhostNPC GP = new GhostNPC(ghost.PosXY.X,ghost.PosXY.Y);
        GP.setID(ghost.ID);
        GP.LoadImage(ghost.imgFilename);
        GP.setTotalHP(ghost.TotalHP);
        GP.setActualHP(ghost.ActualHP);
        GP.setListadeAurasPorID(ghost.ListadeAurasPorID);
        Myrran.getMundo().ListadeGhostNPCsCercanos().add(GP);
    }
    
    public void ClientRemoveGhostNPC (removeGhostNPC ghost)
    {   //Cuando tenemos que eliminar un Fantasma de un Mob, probablemente por lejania, puesto que la muerte ya tiene su propia secuencia de escape
        //simplemente marcamos el campo isCerca como falso, y el bucle de vida principal del thread que lo anima acaba, su secuencia de muerte, lo borra de todas las listas de cercania.
        for (int i=0;i<Myrran.getMundo().ListadeGhostNPCsCercanos().size();i++)
        {
            if (Myrran.getMundo().ListadeGhostNPCsCercanos().get(i).getID().equals(ghost.ID))
                Myrran.getMundo().ListadeGhostNPCsCercanos().get(i).setIsCerca(false);
        }
    }
    
    public void ClientActualizarGhostNPC (actualizarGhostNPC actGhost)
    {   //Buscamos al GhostNPC cuyo ID Coincide y actualizamos sus datos
        for (int i=0;i<Myrran.getMundo().ListadeGhostNPCsCercanos().size();i++)
        {
            if (Myrran.getMundo().ListadeGhostNPCsCercanos().get(i).getID().equals(actGhost.ID)) 
            {
                GhostNPC ghost = Myrran.getMundo().ListadeGhostNPCsCercanos().get(i);

                ghost.setDestino(actGhost.PosXY.X, actGhost.PosXY.Y);
                ghost.setDireccionVisual(actGhost.DireccionVisual);
                ghost.setActualHP(actGhost.ActualHP);
                ghost.setIsCasteandoSpell(actGhost.isCasteandoSpell);
                ghost.setActualCastingTime(actGhost.ActualCastingTime);
                ghost.setTotalCastingTime(actGhost.TotalCastingTime);
            }
        }
    }
    
    public void ClientActualizarAurasGhostNPC (actualizarAurasGhostNPC aGhost)
    {   //Buscamos al GhostNPC cuyo ID coincide y actualizamos su vector de Auras
        for (int i=0;i<Myrran.getMundo().ListadeGhostNPCsCercanos().size();i++)
        {
            if (Myrran.getMundo().ListadeGhostNPCsCercanos().get(i).getID().equals(aGhost.ID)) 
            {
                GhostNPC ghost = Myrran.getMundo().ListadeGhostNPCsCercanos().get(i);
                ghost.setListadeAurasPorID(aGhost.ListadeAurasPorID);
            }
        }   
    }
    
}
