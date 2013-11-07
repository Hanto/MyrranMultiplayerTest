/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Utils.Coordenadas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Image;

/**
 *
 * @author Hanto
 */
public class SpriteM 
{
    public int Tipo;
    public Image img;
    public TextureRegion texture;
    public Coordenadas FrameActual;
    public Coordenadas PosXY;
    public Coordenadas PiesXY;
    
    public int ActualHP;
    public int TotalHP;
    
    public Boolean isCasteandoSpell;
    public int CastedTime;
    public int TotalCastingTime;
}
