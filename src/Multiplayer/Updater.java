/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import DATA.MiscData;
import Main.Myrran;
import Mobiles.PC;
import Mobiles.Player;
import Mobiles.PlayerMP;
import Multiplayer.Network.actualizarGhostNPC;
import Multiplayer.Network.actualizarGhostPlayer;
import Multiplayer.Network.actualizarPlayerPos;


/**
 *
 * @author Hanto
 */
public class Updater 
{

     public Updater ()
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                while (true) 
                {
                    Update();
                    try {Thread.sleep(MiscData.MULTIPLAYER_RefreshRate);} 
                    catch (InterruptedException e) {return;}
                }
            }
        }).start();
    }
    
    public void Update ()
    {
        if (Myrran.isClient) EnviarPosicionPlayerAServidor();
        if (Myrran.isServer) ActualizarPlayersAClientes();
        if (Myrran.isServer) ActualizarNPCsAClientes();
    }
     
    public void EnviarPosicionPlayerAServidor()
    {
        Player player = Myrran.getMundo().getPlayer();
        if (player.getX() != player.getLS_X() || player.getY() != player.getLS_Y())
        {
            actualizarPlayerPos playerPos = new actualizarPlayerPos(Myrran.getMundo().getPlayer());
            Myrran.client.client.sendTCP(playerPos);
        }
    }
    
    public void ActualizarPlayersAClientes()
    {   
        for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
        {
            PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);
            
            for (int j=0; j<player.ListadePlayersCercanos().size();j++)
            {
                PlayerAClientes_BuscarCamposCambiados(player.getConnectionID(), player.ListadePlayersCercanos().get(j));
            }
        } 
    }
    
    public void ActualizarNPCsAClientes()
    {
        for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
         {
             PlayerMP player = Myrran.getMundo().ListadePlayers().get(i);

             for (int j=0; j<player.ListadeMobsCercanos().size();j++)
             {
                 actualizarGhostNPC aGhost = new actualizarGhostNPC(player.ListadeMobsCercanos().get(j));
                 Myrran.server.server.sendToTCP(player.getConnectionID(), aGhost);
             } 
         }  
    }
        
    public void PlayerAClientes_BuscarCamposCambiados(int conID, PC player)
    {
        if (player.getLX() != player.getPiesX() || player.getLY() != player.getPiesY() || player.getLActualCastingTime() != player.getActualCastingTime() || 
            player.getLActualHPs() != player.getActualHPs() || player.getIsLCasteandoSpell() != player.isCasteandoSpell())
        {
            actualizarGhostPlayer aGhost = new actualizarGhostPlayer(player);
            Myrran.server.server.sendToTCP(conID, aGhost);
        }
    }
    
    
}
