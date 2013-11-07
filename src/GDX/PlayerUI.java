/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GDX;

import DATA.MiscData;
import Main.Myrran;
import Mobiles.PC;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 *
 * @author Hanto
 */
public class PlayerUI 
{
    public Group BarraSkills;
    
    public static void DibujarBarraSkills1 ()
    {
        PC player = Myrran.getMundo().getPlayer();
        
        for (int i=0; i<player.BarraSkills().length;i++)
        {
            int IDSkill = player.BarraSkills()[i];
            
            if (IDSkill>0)
            {
                if (player.getSpell(IDSkill).texture == null) player.getSpell(IDSkill).texture = new TextureRegion (GameplayScreen.atlas.findRegion(MiscData.ATLAS_SpellIcons_LOC+player.getSpell(IDSkill).getNombreRecursoIcono()));
            }
        }
        
    }
    
}
