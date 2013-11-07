/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import DATA.MiscData;
import Geo.Estatico;
import Geo.Mundo;
import Main.Myrran;
import MobGhost.GhostNPC;
import MobGhost.GhostPepo;
import MobGhost.GhostPlayer;
import Mobiles.Mob;
import Mobiles.NPC;
import Mobiles.PC;
import Mobiles.Player;
import Mobiles.PlayerMP;
import Mobiles.Proyectil;
import Utils.Coordenadas;
import com.badlogic.gdx.utils.Array;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Hanto
 */
public class RenderSprites 
{
    public static SpriteM [] ListaRender;
    public static ArrayList <SpriteM> ListadeEstaticosCercanos = new ArrayList <SpriteM>();
    
    
    public static void JuntarMobilesARenderizar()
    {   
        synchronized (Myrran.getMundo().ListadeGhostPeposCercanos()){
        synchronized (Myrran.getMundo().ListadeGhostNPCsCercanos()){
        synchronized (Myrran.getMundo().ListadeGhostPlayersCercanos()){
            
            List <GhostNPC> mobs = Myrran.getMundo().ListadeGhostNPCsCercanos();
            List <GhostPepo> pepos = Myrran.getMundo().ListadeGhostPeposCercanos();
            List <GhostPlayer> Gplayers = Myrran.getMundo().ListadeGhostPlayersCercanos();  
            //List <PlayerMP> players = Myrran.getMundo().ListadePlayers();
            
            int numGhostMobs = mobs.size();
            int numGhostPepos = pepos.size();
            int numGhostPlayers = Gplayers.size();
            //int numPlayers = players.size();
            int numPlayers = 0;
        
            ListaRender = new SpriteM [numGhostMobs+numGhostPepos+numGhostPlayers+numPlayers+1]; 
         
            //GhostNPCs
            for (int i=0;i<numGhostMobs;i++)
            {   
                ListaRender[i]=new SpriteM();
                ListaRender[i].Tipo=MiscData.NAMEPLATES_TIPO_ENEMIGO;
                ListaRender[i].img=mobs.get(i).getImg();
                ListaRender[i].FrameActual=new Coordenadas();
                ListaRender[i].FrameActual.setXY(mobs.get(i).getFrameActual_Xcord(), mobs.get(i).getFrameActual_Ycord());
                ListaRender[i].PosXY=new Coordenadas();
                ListaRender[i].PosXY.setXY ( mobs.get(i).getX(), mobs.get(i).getY());
                ListaRender[i].PiesXY=new Coordenadas();
                ListaRender[i].PiesXY.setXY( mobs.get(i).getPiesX(), mobs.get(i).getPiesY());
                
                ListaRender[i].TotalHP=mobs.get(i).getTotalHP();
                ListaRender[i].ActualHP=mobs.get(i).getActualHP();
                
                ListaRender[i].isCasteandoSpell=mobs.get(i).getIsCasteandoSpell();
                ListaRender[i].CastedTime=mobs.get(i).getActualCastingTime();
                ListaRender[i].TotalCastingTime=mobs.get(i).getTotalCastingTime(); 
            }
            //GhostProyectiles
            for (int i=numGhostMobs,j=0;i<numGhostMobs+numGhostPepos;i++)
            {   
                ListaRender[i]=new SpriteM();
                ListaRender[i].Tipo=MiscData.NAMEPLATES_TIPO_PROYECTIL;
                ListaRender[i].img=pepos.get(j).getImg();
                ListaRender[i].FrameActual=new Coordenadas();
                ListaRender[i].FrameActual.setXY(pepos.get(j).getFrameActual_Xcord(), pepos.get(j).getFrameActual_Ycord());
                ListaRender[i].PosXY=new Coordenadas();
                ListaRender[i].PosXY.setXY(pepos.get(j).getX(), pepos.get(j).getY());
                ListaRender[i].PiesXY=new Coordenadas();
                ListaRender[i].PiesXY.setXY(pepos.get(j).getPiesX(), pepos.get(j).getPiesY());
                j++;     
            }
            //GhostPlayers   
            for (int i=numGhostMobs+numGhostPepos,j=0;i<numGhostMobs+numGhostPepos+numGhostPlayers;i++)
            {
                ListaRender[i]=new SpriteM();
                ListaRender[i].Tipo=MiscData.NAMEPLATES_TIPO_PLAYER;
                ListaRender[i].img=Gplayers.get(j).getImg();
                ListaRender[i].texture=Gplayers.get(j).texture;
                ListaRender[i].FrameActual=new Coordenadas();
                ListaRender[i].FrameActual.setXY(Gplayers.get(j).getFrameActual_Xcord(), Gplayers.get(j).getFrameActual_Ycord());
                ListaRender[i].PosXY=new Coordenadas();
                ListaRender[i].PosXY.setXY(Gplayers.get(j).getX(), Gplayers.get(j).getY());
                ListaRender[i].PiesXY=new Coordenadas();
                ListaRender[i].PiesXY.setXY(Gplayers.get(j).getPiesX(), Gplayers.get(j).getPiesY());
                
                ListaRender[i].TotalHP=Gplayers.get(j).getTotalHP();
                ListaRender[i].ActualHP=Gplayers.get(j).getActualHP();
                
                ListaRender[i].isCasteandoSpell=Gplayers.get(j).getIsCasteandoSpell();
                ListaRender[i].CastedTime=Gplayers.get(j).getActualCastingTime();
                ListaRender[i].TotalCastingTime=Gplayers.get(j).getTotalCastingTime();
                j++;   
            }
            //PlayersMP en el Servidor
            /*for (int i=numGhostMobs+numGhostPepos+numGhostPlayers,j=0;i<numGhostMobs+numGhostPepos+numGhostPlayers+numPlayers;i++)
            {
                ListaRender[i]=new SpriteM();
                ListaRender[i].Tipo=MiscData.NAMEPLATES_TIPO_PLAYER;
                ListaRender[i].img=players.get(j).getImg();
                ListaRender[i].FrameActual=new Coordenadas();
                ListaRender[i].FrameActual.setXY(players.get(j).getFrameActual_Xcord(), players.get(j).getFrameActual_Ycord());
                ListaRender[i].PosXY=new Coordenadas();
                ListaRender[i].PosXY.setXY(players.get(j).getX(), players.get(j).getY());
                ListaRender[i].PiesXY=new Coordenadas();
                ListaRender[i].PiesXY.setXY(players.get(j).getPiesX(), players.get(j).getPiesY());
                
                ListaRender[i].TotalHP=players.get(j).getTotalHps();
                ListaRender[i].ActualHP=players.get(j).getActualHPs();
                
                ListaRender[i].isCasteandoSpell=players.get(j).isCasteandoSpell();
                ListaRender[i].CastedTime=players.get(j).getActualCastingTime();
                ListaRender[i].TotalCastingTime=players.get(j).getTotalCastingTime();
                j++;   
            }*/
            //Player Local para que el Feeling sea de respuesta inmediata
            {
                Player player = Myrran.getMundo().getPlayer();
                int ultimaPos = ListaRender.length-1;
                ListaRender[ultimaPos] = new SpriteM();
                ListaRender[ultimaPos].Tipo=MiscData.NAMEPLATES_TIPO_PLAYER;
                ListaRender[ultimaPos].img=player.getImg();
                ListaRender[ultimaPos].texture=player.texture;
                ListaRender[ultimaPos].FrameActual=new Coordenadas();
                ListaRender[ultimaPos].FrameActual.setXY(player.getFrameActual_Xcord(), player.getFrameActual_Ycord());
                ListaRender[ultimaPos].PosXY=new Coordenadas();
                ListaRender[ultimaPos].PosXY.setXY(player.getX(), player.getY());
                ListaRender[ultimaPos].PiesXY=new Coordenadas();
                ListaRender[ultimaPos].PiesXY.setXY(player.getPiesX(), player.getPiesY());
                
                ListaRender[ultimaPos].TotalHP=player.getTotalHps();
                ListaRender[ultimaPos].ActualHP=player.getActualHPs();
                
                ListaRender[ultimaPos].isCasteandoSpell=player.isCasteandoSpell();
                ListaRender[ultimaPos].CastedTime=player.getActualCastingTime();
                ListaRender[ultimaPos].TotalCastingTime=player.getTotalCastingTime();
            }
        }}}          
    }
    
    public static void CrearListaEstaticos ()
    {
        ListadeEstaticosCercanos.removeAll(ListadeEstaticosCercanos);
        CamaraM camara = Myrran.getMap3D().getCamara();
        int TileSize = MiscData.MAP3D_TILESIZE;
        
        for (int pixelY=camara.getEsquinaSupIzdaY();pixelY<camara.getEsquinaInfDchaY()+TileSize*3;pixelY=pixelY+TileSize)
        {
            for (int pixelX=camara.getEsquinaSupIzdaX()-TileSize;pixelX<camara.getEsquinaInfDchaX()+TileSize*2;pixelX=pixelX+TileSize)
            { 
                int TileY = pixelY/TileSize;
                int TileX = pixelX/TileSize;
                
                try 
                {
                    if (Mundo.mapa.map()[TileX][TileY].tieneEstatico1())
                    {
                        SpriteM sprite = new SpriteM ();
                        sprite.img = Myrran.getMundo().ListaEstaticos().get(Mundo.mapa.map()[TileX][TileY].getEstatico1()).getIMG();
                        sprite.texture = Myrran.getMundo().ListaEstaticos().get(Mundo.mapa.map()[TileX][TileY].getEstatico1()).getTexture();
                        sprite.Tipo = MiscData.NAMEPLATES_TIPO_STATIC;
                        
                        int imgAlto = sprite.img.getHeight(null);
                        int imgAncho = sprite.img.getWidth(null);
                        
                        sprite.PosXY = new Coordenadas ();
                        sprite.PosXY.setX(pixelX-camara.getEsquinaSupIzdaX()-pixelX%TileSize - ((imgAncho/2)-TileSize/2));
                        sprite.PosXY.setY(pixelY-camara.getEsquinaSupIzdaY()-pixelY%TileSize - (imgAlto-TileSize));
                        sprite.PiesXY = new Coordenadas ();
                        sprite.PiesXY.setX(pixelX-pixelX%TileSize - ((imgAncho/2)-TileSize/2));
                        sprite.PiesXY.setY(pixelY-pixelY%TileSize + TileSize-1);
                        
                        ListadeEstaticosCercanos.add(sprite);
                    }
                } catch (Exception e) {}
            }
        }
    }
    
    //Ordena la lista de Unidades a renderizar segun la cercania (eje-Y, y a misma cercania, segun el eje-X)
    public static void OrdenarListaMobiles ()
    {
        for (int i=0;i<ListaRender.length-1;i++)
        {
            for (int j=i+1;j<ListaRender.length;j++)
            {
                if (ListaRender[j].PiesXY.getY()<ListaRender[i].PiesXY.getY())
                {
                    SpriteM aux;
                    aux = ListaRender[i];
                    ListaRender[i]=ListaRender[j];
                    ListaRender[j]=aux;    
                }
                if (ListaRender[j].PiesXY.getY()==ListaRender[i].PiesXY.getY())
                {
                    if (ListaRender[j].PiesXY.getX()>ListaRender[i].PiesXY.getX())
                    {
                        SpriteM aux;
                        aux = ListaRender[i];
                        ListaRender[i]=ListaRender[j];
                        ListaRender[j]=aux;
                    }
                }
            }
        }
    }
    
    public static void DibujarSprite (Graphics g, SpriteM sprite)
    {
        Graphics2D g2 = (Graphics2D) g;
        int CamaraX = Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
        int CamaraY = Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();
        
        //Mobiles
        if (sprite.Tipo<3)
        {
            g2.drawImage(sprite.img,
                         sprite.PosXY.getX()-CamaraX, sprite.PosXY.getY()-CamaraY,
                         sprite.PosXY.getX()-CamaraX+32, sprite.PosXY.getY()-CamaraY+32,
                         sprite.FrameActual.getX(), sprite.FrameActual.getY(),
                         sprite.FrameActual.getX()+32, sprite.FrameActual.getY()+32, null);
        }
            
        //EStaticos
        if (sprite.Tipo==3)
        {    
            g2.drawImage(sprite.img,
                         sprite.PosXY.getX(), sprite.PosXY.getY(), null);
        }
             
        //Barras de Vida
        Nameplate.DibujarNameplate(g, sprite.Tipo, MiscData.NAMEPLATES_TIPO_BARRAVIDA, 
                                   sprite.PosXY.getX(), sprite.PosXY.getY(), 
                                   sprite.ActualHP, sprite.TotalHP, 
                                   true, true, 2);
        //Barras de Casteo
        Nameplate.DibujarNameplate(g, sprite.Tipo, MiscData.NAMEPLATES_TIPO_BARRACASTEO, 
                                   sprite.PosXY.getX(), sprite.PosXY.getY(), 
                                   sprite.CastedTime, sprite.TotalCastingTime, 
                                   sprite.isCasteandoSpell, true, 2);  
    }
    
    
    static class ComparatorMob implements Comparator<Mob>
    {
        @Override
        public int compare(Mob o1, Mob o2) 
            { return (o1.getPiesY()<o2.getPiesY() ? -1 : ( o1.getPiesY() == o2.getPiesY() ? 0 : 1)); }
    };
    
    public static void RenderSprites (Graphics g)
    {   
        Array <Mob> ListaMobs = new Array <Mob>();
        
        for (int i=0; i<Myrran.getMundo().ListadeGhostNPCsCercanos().size();i++)
        {
            ListaMobs.add(Myrran.getMundo().ListadeGhostNPCsCercanos().get(i));
        }
        for (int i=0;i<Myrran.getMundo().ListadeGhostPeposCercanos().size();i++)
        {
            ListaMobs.add(Myrran.getMundo().ListadeGhostPeposCercanos().get(i));
        }
        ListaMobs.sort( new ComparatorMob());
        
       
         
        
        CrearListaEstaticos();
        JuntarMobilesARenderizar();
        OrdenarListaMobiles();
        
        int ultimoEstaticoDibujado=0;
        
        for (int i=0;i<ListaRender.length;i++)
        {   
            if (ListadeEstaticosCercanos.size()>0)
            {
                for (int j=ultimoEstaticoDibujado;j<ListadeEstaticosCercanos.size();j++)
                {
                    if (ListadeEstaticosCercanos.get(j).PiesXY.getY()<ListaRender[i].PiesXY.getY()) { DibujarSprite(g, ListadeEstaticosCercanos.get(j)); ultimoEstaticoDibujado++; }
                    else {break;}
                }
            }
            DibujarSprite (g, ListaRender[i]);
        }
        //Si el vector de Estaticos es mas grande que el de mobiles, hay que terminar de dibujarlo
        if (ultimoEstaticoDibujado < ListadeEstaticosCercanos.size())
        {
            for (int j=ultimoEstaticoDibujado;j<ListadeEstaticosCercanos.size();j++)
            {
                DibujarSprite(g, ListadeEstaticosCercanos.get(j));
            }
        }
    } 
    
    
    
}

