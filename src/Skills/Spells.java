/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Skills;

import DATA.MiscData;
import DATA.SpellData;
import Geo.Estatico;
import Geo.Mundo;
import static Graphics.Map3D.cursor;
import Main.Myrran;
import Mobiles.Personaje;
import WorldBuilder.WorldEditor;
import java.awt.Color;

/**
 *
 * @author Hanto
 */
public class Spells
{
        
    public static void SelfHeal (SpellTemplate Spell, Personaje Caster)
    {
        //Componente DirectHEAL
        Caster.setActualHPs(Caster.getActualHPs()+(int)Spell.Stats()[1].getValorTotal());      
        Myrran.getMap3D().addSCT(Caster.getX()+MiscData.MAP3D_TILESIZE/2, Caster.getY()+MiscData.MAP3D_TILESIZE/2, 
                                 "+"+(int)Spell.Stats()[1].getValorTotal(), MiscData.SCT_TIEMPO_MENSAJE, MiscData.SCT_PLAYER_SELFHEAL_COLOR);
        
        //Componente HealOverTime
        if(!Spell.AurasQueAplica().isEmpty())
        {
            for (int i=0; i<Spell.AurasQueAplica().size();i++)
            {   
                new AuraCast(Spell.AurasQueAplica().get(i), Myrran.getMundo().getPlayer(), Myrran.getMundo().getPlayer());
            }
        }  
    }
    
    public static void PlantarArbol (int TargetCeldaX, int TargetCeldaY)
    {
        if (!Myrran.getMundo().ListaEstaticos().isEmpty() && 
                TargetCeldaX < MiscData.MUNDO_MAPAMAXX && TargetCeldaY < MiscData.MUNDO_MAPAMAXY &&
                TargetCeldaX >=0 && TargetCeldaY >= 0)
        {
            for (int i=0;i<cursor.getAltura();i++)
            {
                for (int j=0;j<cursor.getAnchura();j++)
                {
                    if (cursor.getForma(i, j))
                    {
                        try 
                        { 
                            //Mundo.mapa.setTemplate(TargetCeldaX+i-cursor.getX(), TargetCeldaY+j-cursor.getY(), WorldEditor.getbrochaIDTerreno());
                            Mundo.mapa.map()[TargetCeldaX+i-cursor.getX()][TargetCeldaY+j-cursor.getY()].setEstatico1(WorldEditor.getbrochaIDEstatico());
                                
                        } catch (Exception e) {;}
                    }
                }
            }
        }
    }
    
    
    public static void EditarTerrenoCelda (int TargetCeldaX, int TargetCeldaY)
    {
        if (/*WorldEditor.getInsertarCelda() && */!Myrran.getMundo().ListaTerrenos().isEmpty() && 
                TargetCeldaX < MiscData.MUNDO_MAPAMAXX && TargetCeldaY < MiscData.MUNDO_MAPAMAXY &&
                TargetCeldaX >=0 && TargetCeldaY >= 0)
        {
            for (int i=0;i<cursor.getAltura();i++)
            {
                for (int j=0;j<cursor.getAnchura();j++)
                {
                    if (cursor.getForma(i, j))
                    {
                        try 
                        { 
                            //Mundo.mapa.setTemplate(TargetCeldaX+i-cursor.getX(), TargetCeldaY+j-cursor.getY(), WorldEditor.getbrochaIDTerreno());
                            Mundo.mapa.map()[TargetCeldaX+i-cursor.getX()][TargetCeldaY+j-cursor.getY()].setTerrenoBase(WorldEditor.getbrochaIDTerreno());
                                
                        } catch (Exception e) {;}
                    }
                }
            }
        }
    }




}
