/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GDX;

import DATA.MiscData;
import static Graphics.RenderSprites.ListaRender;
import Graphics.SpriteM;
import Main.Myrran;
import MobGhost.GhostNPC;
import MobGhost.GhostPepo;
import MobGhost.GhostPlayer;
import Mobiles.Mob;
import Mobiles.Player;
import Utils.Coordenadas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Hanto
 */
public class RenderSprites 
{
    public static Array<SpriteM> ListadeMobiles;
    
    
    static class ComparatorSpriteM implements Comparator<SpriteM>
    {
        @Override
        public int compare(SpriteM o1, SpriteM o2) 
            { return (o1.PiesXY.Y<o2.PiesXY.Y ? -1 : ( o1.PiesXY.Y == o2.PiesXY.Y ? 0 : 1)); }
    }; 
    
    public static void JuntarMobiles ()
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
            
            ListadeMobiles = new Array<SpriteM>(numGhostMobs+numGhostPepos+numGhostPlayers+numPlayers+1);
            
            //GhostNPCS
            for (int i=0; i<numGhostMobs;i++)
            {
                SpriteM sprite= new SpriteM();
                sprite.Tipo=MiscData.NAMEPLATES_TIPO_ENEMIGO;
                if (mobs.get(i).texture == null) mobs.get(i).texture = new TextureRegion (GameplayScreen.atlas.findRegion(MiscData.ATLAS_NPCSprites_LOC+mobs.get(i).nombreRecurso));
                sprite.texture = mobs.get(i).texture;
                sprite.FrameActual=new Coordenadas();
                sprite.FrameActual.setXY(mobs.get(i).getFrameActual_Xcord(), mobs.get(i).getFrameActual_Ycord());
                sprite.PosXY=new Coordenadas();
                sprite.PosXY.setXY ( mobs.get(i).getX(), mobs.get(i).getY());
                sprite.PiesXY=new Coordenadas();
                sprite.PiesXY.setXY( mobs.get(i).getPiesX(), mobs.get(i).getPiesY());
                //Informacion para los Nameplates
                sprite.TotalHP=mobs.get(i).getTotalHP();
                sprite.ActualHP=mobs.get(i).getActualHP();
                sprite.isCasteandoSpell=mobs.get(i).getIsCasteandoSpell();
                sprite.CastedTime=mobs.get(i).getActualCastingTime();
                sprite.TotalCastingTime=mobs.get(i).getTotalCastingTime();
                
                ListadeMobiles.add(sprite);
            }
            //GhostProyectiles
            for (int i=numGhostMobs,j=0;i<numGhostMobs+numGhostPepos;i++)
            {   
                SpriteM sprite= new SpriteM();
                sprite.Tipo=MiscData.NAMEPLATES_TIPO_PROYECTIL;
                if (pepos.get(j).texture == null) pepos.get(j).texture = new TextureRegion (GameplayScreen.atlas.findRegion(MiscData.ATLAS_SpellSprites_LOC+pepos.get(j).nombreRecurso));
                sprite.texture = pepos.get(j).texture;
                sprite.FrameActual=new Coordenadas();
                sprite.FrameActual.setXY(pepos.get(j).getFrameActual_Xcord(), pepos.get(j).getFrameActual_Ycord());
                sprite.PosXY=new Coordenadas();
                sprite.PosXY.setXY(pepos.get(j).getX(), pepos.get(j).getY());
                sprite.PiesXY=new Coordenadas();
                sprite.PiesXY.setXY(pepos.get(j).getPiesX(), pepos.get(j).getPiesY());
                
                ListadeMobiles.add(sprite);              
                j++;     
            }
            //GhostPlayers
            for (int i=numGhostMobs+numGhostPepos,j=0;i<numGhostMobs+numGhostPepos+numGhostPlayers;i++)
            {
                SpriteM sprite= new SpriteM();
                sprite.Tipo=MiscData.NAMEPLATES_TIPO_PLAYER;
                if (Gplayers.get(j).texture == null) Gplayers.get(j).texture = new TextureRegion (GameplayScreen.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+Gplayers.get(j).nombreRecurso));
                sprite.texture=Gplayers.get(j).texture;
                sprite.FrameActual=new Coordenadas();
                sprite.FrameActual.setXY(Gplayers.get(j).getFrameActual_Xcord(), Gplayers.get(j).getFrameActual_Ycord());
                sprite.PosXY=new Coordenadas();
                sprite.PosXY.setXY(Gplayers.get(j).getX(), Gplayers.get(j).getY());
                sprite.PiesXY=new Coordenadas();
                sprite.PiesXY.setXY(Gplayers.get(j).getPiesX(), Gplayers.get(j).getPiesY());
                //Informacion para los Nameplates
                sprite.TotalHP=Gplayers.get(j).getTotalHP();
                sprite.ActualHP=Gplayers.get(j).getActualHP();
                sprite.isCasteandoSpell=Gplayers.get(j).getIsCasteandoSpell();
                sprite.CastedTime=Gplayers.get(j).getActualCastingTime();
                sprite.TotalCastingTime=Gplayers.get(j).getTotalCastingTime();
                
                ListadeMobiles.add(sprite);
                j++;   
            }
            //Player
            {
                Player player = Myrran.getMundo().getPlayer();
                SpriteM sprite= new SpriteM();
                sprite.Tipo=MiscData.NAMEPLATES_TIPO_PLAYER;
                if (player.texture == null) player.texture = new TextureRegion (GameplayScreen.atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+player.nombreRecurso));
                sprite.texture=player.texture;
                sprite.FrameActual=new Coordenadas();
                sprite.FrameActual.setXY(player.getFrameActual_Xcord(), player.getFrameActual_Ycord());
                sprite.PosXY=new Coordenadas();
                sprite.PosXY.setXY((int)GameplayScreen.foco.x, (int)GameplayScreen.foco.y);
                sprite.PiesXY=new Coordenadas();
                sprite.PiesXY.setXY(player.getPiesX(), player.getPiesY());
                //Informacion para los Nameplates
                sprite.TotalHP=player.getTotalHps();
                sprite.ActualHP=player.getActualHPs();
                sprite.isCasteandoSpell=player.isCasteandoSpell();
                sprite.CastedTime=player.getActualCastingTime();
                sprite.TotalCastingTime=player.getTotalCastingTime();
                
                ListadeMobiles.add(sprite);
            }
            
        }}}
    }
    
    public static void JuntarYOrdenarMobiles ()
    {
        JuntarMobiles();
        ListadeMobiles.sort(new ComparatorSpriteM());
    }
    
}
