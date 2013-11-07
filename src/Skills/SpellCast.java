/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Skills;

import DATA.SpellData;
import Mobiles.Proyectil;
import DATA.MiscData;
import Geo.Estatico;
import Geo.Mundo;
import Graphics.RenderSprites;
import Mobiles.Personaje;
import Mobiles.NPC;
import Main.Myrran;
import java.awt.Color;

/**
 *
 * @author Hanto
 */
public final class SpellCast
{
    private SpellTemplate Skill;
    private Personaje Caster;
    private int TargetX;
    private int TargetY;
    
    private int getTargetCeldaX ()             { return TargetX/MiscData.MAP3D_TILESIZE; }
    private int getTargetCeldaY ()             { return TargetY/MiscData.MAP3D_TILESIZE; }
    
    public SpellCast (SpellTemplate Spell, Personaje caster, int MouseX, int MouseY)
    {
        Skill = Spell;
        SpellBeginCast (caster, MouseX, MouseY);
    }
    
    public SpellCast (int SpellID, Personaje caster, int MouseX, int MouseY)
    {
        Skill = Myrran.getMundo().ListadeSkills().get(SpellID);  
        SpellBeginCast(caster, MouseX, MouseY);
    }
    
    public SpellCast (Personaje caster, int MouseX, int MouseY)
    {
        Skill = Myrran.getMundo().ListadeSkills().get(caster.getSkillSeleccionado());
        SpellBeginCast(caster, MouseX, MouseY);
    }
    
    public SpellCast (String SpellNombre, Personaje caster, int MouseX, int MouseY)
    {
        int SpellID = Mundo.getSpellID(SpellNombre);
        if (SpellID < 0) {System.out.println("Nombre de Spell: "+SpellNombre+" ,no encontado.");return;}
        Skill = Myrran.getMundo().ListadeSkills().get(SpellID);
        SpellBeginCast(caster, MouseX, MouseY);
    }
    
    public void SpellBeginCast (Personaje caster, int MouseX, int MouseY)
    {
        if (caster.isCasteandoSpell() == true) return;
        
        Caster = caster;
               
        if (Caster.isPlayer())   //Si es un jugador, las coordenadas seran las del raton, por tanto hay que sumar el Offset de la camara
        {
            TargetX = MouseX + caster.getCamEsquinaSupIzdaX();
            TargetY = MouseY + caster.getCamEsquinaSupIzdaY();
        }
        else
        { TargetX = MouseX; TargetY=MouseY; }
        
        if ((int)Skill.Stats()[0].ValorBase >0 && !Caster.isCasteandoSpell()) Castear();
        
        switch (Skill.getTipo())
        {
            case SpellData.SPELL_TIPO_Special:         {SpellEndCastSpecial();break;}
            case SpellData.SPELL_TIPO_Proyectil:       {SpellEndCastProyectil();break;}
            case SpellData.SPELL_TIPO_SelfHeal:        {Spells.SelfHeal(Skill, Caster);break;}
        }
    }
    
    public void SpellEndCastSpecial ()
    {
        int TargetCeldaX = getTargetCeldaX();
        int TargetCeldaY = getTargetCeldaY();
        
        if (Skill.getNombre().equals(SpellData.EDITARTERRENO_NOMBRE)) { Spells.EditarTerrenoCelda (TargetCeldaX, TargetCeldaY); }
        if (Skill.getNombre().equals(SpellData.SUMMONSPECTRO_NOMBRE)) { NPC muerte = new NPC(TargetX-MiscData.MAP3D_TILESIZE/2,TargetY-MiscData.MAP3D_TILESIZE/2, MiscData.SPRITE_IMGFILENAME_GREAPER);IO.MouseInputManager.BotonesOFF();}
        if (Skill.getNombre().equals(SpellData.PLANTARARBOL_NOMBRE))  { Spells.PlantarArbol(TargetCeldaX, TargetCeldaY); }
    }
    
    public void SpellEndCastProyectil()
    {
        int OrigenX = Caster.getCentroX(); //Para que el centro de gravedad sea el centro del sprite
        int OrigenY = Caster.getCentroY(); //Para que el centro de gravedad sea el centro del sprite
        int TargetX = this.TargetX;
        int TargetY = this.TargetY;
        double direccion = Math.atan2(TargetY-OrigenY,TargetX-OrigenX);
        
        Proyectil pepo = new Proyectil (Caster.getX(), Caster.getY());  //pero en cambio salga justo de encima de nuestro char
        
        pepo.setImg(Skill.getGraficoIMG());
        pepo.setImgFilename(Skill.getGraficoIMGFilename());
        pepo.setAnimacionBase(Skill.getAnimacionBaseXY());  
        pepo.setDireccion(direccion);
        pepo.setOwner(Caster);
        
        pepo.setDaño((int)Skill.Stats()[1].getValorTotal());
        if (!Caster.isPlayer()) pepo.setVelocidadMax(Skill.Stats()[2].getValorTotal()/4);
        else pepo.setVelocidadMax(Skill.Stats()[2].getValorTotal());
        pepo.setDuracionMaxima((int)Skill.Stats()[3].getValorTotal());
        pepo.setPierces(false);
        
        if(!Skill.AurasQueAplica().isEmpty())
        {
            for (int i=0; i<Skill.AurasQueAplica().size();i++)
            {
                pepo.AurasQueAplica().add(Skill.AurasQueAplica().get(i));
            }
        }
    }
    
    public void Castear()
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                int CastingTime = 0;
                Caster.setCasteandoSpell(true);
                Caster.setTotalCastingTime((int)Skill.Stats()[0].getValorTotal());
                while (CastingTime<(int)Skill.Stats()[0].getValorTotal()) 
                {   
                    CastingTime = CastingTime + MiscData.MAP3D_REFRESH_RATE;
                    Caster.setActualCastingTime(CastingTime);
                    
                    try {Thread.sleep(MiscData.MAP3D_REFRESH_RATE);} 
                    catch (InterruptedException e) {return;}
                }
                Caster.setCasteandoSpell(false);
            }
        }).start();
    }


}
