/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

import Graphics.Plantilla;
import Main.Myrran;
import Skills.AuraTemplate;
import Skills.StatSpell;
import Skills.SpellTemplate;
import Skills.TipoSpell;
import Utils.Coordenadas;

/**
 *
 * @author Hanto
 */
public class LoadData 
{
    public static void CargarCursores ()
    {   
        Plantilla c1 = new Plantilla();
        c1.setForma(MiscData.CURSOR_FORMA_001);
        c1.setPuntero(MiscData.CURSOR_PUNTERO_001[0], MiscData.CURSOR_PUNTERO_001[1]);
        c1.LoadIMGicono(MiscData.PLANTILLA_IMGicono_1x1);
        Myrran.getMundo().ListaPlantillas().add(c1);
        Plantilla c2 = new Plantilla();
        c2.setForma(MiscData.CURSOR_FORMA_002);
        c2.setPuntero(MiscData.CURSOR_PUNTERO_002[0], MiscData.CURSOR_PUNTERO_002[1]);
        c2.LoadIMGicono(MiscData.PLANTILLA_IMGicono_2x2);
        Myrran.getMundo().ListaPlantillas().add(c2);
        Plantilla c3 = new Plantilla();
        c3.setForma(MiscData.CURSOR_FORMA_003);
        c3.setPuntero(MiscData.CURSOR_PUNTERO_003[0], MiscData.CURSOR_PUNTERO_003[1]);
        c3.LoadIMGicono(MiscData.PLANTILLA_IMGicono_3x3);
        Myrran.getMundo().ListaPlantillas().add(c3);
        Plantilla c4 = new Plantilla();
        c4.setForma(MiscData.CURSOR_FORMA_004);
        c4.setPuntero(MiscData.CURSOR_PUNTERO_004[0], MiscData.CURSOR_PUNTERO_004[1]);
        c4.LoadIMGicono(MiscData.PLANTILLA_IMGicono_4x4);
        Myrran.getMundo().ListaPlantillas().add(c4);
        Plantilla c5 = new Plantilla();
        c5.setForma(MiscData.CURSOR_FORMA_005);
        c5.setPuntero(MiscData.CURSOR_PUNTERO_005[0], MiscData.CURSOR_PUNTERO_005[1]);
        c5.LoadIMGicono(MiscData.PLANTILLA_IMGicono_5x5);
        Myrran.getMundo().ListaPlantillas().add(c5);
    }
    
    public static void CargarSkills()
    {   
        //FIREBALL
        SpellTemplate skill = new SpellTemplate();
        skill.setNombre(SpellData.FIREBALL_NOMBRE);
        skill.setDescripcion(SpellData.FIREBALL_Descripcion);
        skill.setIconoIMG(SpellData.FIREBALL_ICONOFICHERO);
        skill.setGraficoIMG(SpellData.FIREBALL_IMGFILENAME);
        skill.setAnimacionBaseXY(new Coordenadas(6,1));
        skill.setKeybind(SpellData.FIREBALL_KEYBIND);
        
        skill.setTipo(SpellData.FIREBALL_TIPO);
        skill.Stats()[1].setValorBase(SpellData.FIREBALL_DAÑO);
        skill.Stats()[1].CosteTalento = 3;
        skill.Stats()[1].BonoTalento = 3.5;
        skill.Stats()[0].setValorBase(SpellData.FIREBALL_CASTINGTIME);
        skill.Stats()[0].CosteTalento = 3;
        skill.Stats()[0].BonoTalento = -10;
        skill.Stats()[2].setValorBase(SpellData.FIREBALL_VELOCIDADMAXIMA);
        skill.Stats()[2].CosteTalento = 2;
        skill.Stats()[2].BonoTalento = 0.25;
        skill.Stats()[3].setValorBase(SpellData.FIREBALL_DURACIONMAXIMA);
               
        Myrran.getMundo().ListadeSkills().add(skill);
        
        //ICEBALL
        skill = new SpellTemplate();
        skill.setNombre(SpellData.ICEBALL_NOMBRE);
        skill.setIconoIMG(SpellData.ICEBALL_ICONOFICHERO);
        skill.setGraficoIMG(SpellData.ICEBALL_IMGFILENAME);
        skill.setAnimacionBaseXY(SpellData.ICEBALL_ANIMACIONBASE);
        skill.setKeybind(SpellData.ICEBALL_KEYBIND);
        
        skill.setTipo(SpellData.ICEBALL_TIPO);
        skill.Stats()[1].setValorBase(SpellData.ICEBALL_DAÑO);
        skill.Stats()[1].CosteTalento = 3;
        skill.Stats()[1].BonoTalento = 3.5;
        skill.Stats()[0].setValorBase(SpellData.ICEBALL_CASTINGTIME);
        skill.Stats()[0].CosteTalento = 3;
        skill.Stats()[0].BonoTalento = -10;
        skill.Stats()[2].setValorBase(SpellData.ICEBALL_VELOCIDADMAXIMA);
        skill.Stats()[2].CosteTalento = 2;
        skill.Stats()[2].BonoTalento = 0.25;
        skill.Stats()[3].setValorBase(SpellData.ICEBALL_DURACIONMAXIMA);
        
        skill.addAura(0, true);
        Myrran.getMundo().ListadeSkills().add(skill);
        
        //POISONBOLT
        skill = new SpellTemplate();
        skill.setNombre(SpellData.POISONBOLT_NOMBRE);
        skill.setDescripcion(SpellData.POISONBOLT_Descripcion);
        skill.setIconoIMG(SpellData.POISONBOLT_ICONOFICHERO);
        skill.setGraficoIMG(SpellData.POISONBOLT_IMGFILENAME);
        skill.setAnimacionBaseXY(SpellData.POISONBOLT_ANIMACIONBASE);
        skill.setKeybind(SpellData.POISONBOLT_KEYBIND);
        
        skill.setTipo(SpellData.POISONBOLT_TIPO);
        skill.Stats()[1].setValorBase(SpellData.POISONBOLT_DAÑO);
        skill.Stats()[1].CosteTalento = 3;
        skill.Stats()[1].BonoTalento = 3.5;
        skill.Stats()[0].setValorBase(SpellData.POISONBOLT_CASTINGTIME);
        skill.Stats()[0].CosteTalento = 3;
        skill.Stats()[0].BonoTalento = -10;
        skill.Stats()[2].setValorBase(SpellData.POISONBOLT_VELOCIDADMAXIMA);
        skill.Stats()[2].CosteTalento = 2;
        skill.Stats()[2].BonoTalento = 0.25;
        skill.Stats()[3].setValorBase(SpellData.POISONBOLT_DURACIONMAXIMA);
        
        skill.addAura(2, true);
        Myrran.getMundo().ListadeSkills().add(skill);
        
        //HOT: RENEW
        skill = new SpellTemplate();
        skill.setNombre(SpellData.RENEW_NOMBRE);
        skill.setIconoIMG(SpellData.RENEW_ICONOFICHERO);
        skill.setKeybind(SpellData.RENEW_KEYBIND);
        
        skill.setTipo(SpellData.RENEW_TIPO);
        skill.Stats()[1].setValorBase(SpellData.RENEW_HEALDIRECTO);
        skill.Stats()[1].CosteTalento = 3;
        skill.Stats()[1].BonoTalento = 3.5;
        skill.Stats()[0].setValorBase(SpellData.RENEW_CASTINGTIME);
        skill.Stats()[0].CosteTalento = 3;
        skill.Stats()[0].BonoTalento = -10;
  
        skill.addAura(1, true);
        Myrran.getMundo().ListadeSkills().add(skill);
       
        //EDITAR TERRENO
        skill = new SpellTemplate();
        skill.setNombre(SpellData.EDITARTERRENO_NOMBRE);
        skill.setIconoIMG(SpellData.EDITARTERRENO_ICONOFICHERO);
        skill.setKeybind(SpellData.EDITARTERRENO_KEYBIND);
        skill.setTipo(SpellData.SPELL_TIPO_Special);
        Myrran.getMundo().ListadeSkills().add(skill);
        
        //EDITAR TERRENO
        skill = new SpellTemplate();
        skill.setNombre(SpellData.PLANTARARBOL_NOMBRE);
        skill.setIconoIMG(SpellData.PLANTARARBOL_ICONOFICHERO);
        skill.setKeybind(SpellData.PLANTARARBOL_KEYBIND);
        skill.setTipo(SpellData.SPELL_TIPO_Special);
        Myrran.getMundo().ListadeSkills().add(skill);  
        
        //SUMMON NPC: ESPECTRO
        skill = new SpellTemplate();
        skill.setNombre(SpellData.SUMMONSPECTRO_NOMBRE);
        skill.setDescripcion(SpellData.SUMMONSPECTRO_Descripcion);
        skill.setIconoIMG(SpellData.SUMMONGSPECTRO_ICONOFICHERO);
        skill.setKeybind(SpellData.SUMMONGSPECTRO_KEYBIND);
        skill.setTipo(SpellData.SPELL_TIPO_Special);
        Myrran.getMundo().ListadeSkills().add(skill);
    }
    
    public static void CargarAuras() 
    {
        AuraTemplate aura = new AuraTemplate();
        aura.setNombre(AuraData.SNARE_NOMBRE);
        aura.setIMG(AuraData.SNARE_IMG);
        aura.setTipo(AuraData.SNARE_TIPO);
        aura.setIsDebuff(AuraData.SNARE_ISDEBUFF);
        aura.Stats()[1].setValorBase(AuraData.SNARE_DURACIONMAXIMA);
        aura.Stats()[1].setTalento(0, 50, 3, 50);
        aura.Stats()[0].setValorBase(AuraData.SNARE_PENALIZADORMOVIMIENTO);
        aura.Stats()[0].setTalento(0, 50, 5, 0.005);
        Myrran.getMundo().ListadeAuras().add(aura);
        
        aura = new AuraTemplate();
        aura.setNombre(AuraData.HOTRENEW_NOMBRE);
        aura.setIMG(AuraData.HOTRENEW_IMG);
        aura.setTipo(AuraData.HOTRENEW_TIPO);
        aura.setIsDebuff(AuraData.HOTRENEW_ISDEBUFF);
        aura.Stats()[1].setValorBase(AuraData.HOTRENEW_DURACIONMAXIMA);
        aura.Stats()[1].setTalento(0, 50, 3, 50);
        aura.Stats()[0].setValorBase(AuraData.HOTRENEW_HEALPORTICK);
        aura.Stats()[0].setTalento(0, 50, 5, 2.5);
        Myrran.getMundo().ListadeAuras().add(aura);
        
        aura = new AuraTemplate();
        aura.setNombre(AuraData.DOT_LIFEROT_NOMBRE);
        aura.setIMG(AuraData.DOT_LIFEROT_IMG);
        aura.setTipo(AuraData.DOT_LIFEROT_TIPO);
        aura.setIsDebuff(AuraData.DOT_LIFEROT_ISDEBUFF);
        aura.Stats()[1].setValorBase(AuraData.DOT_LIFEROT_DURACIONMAXIMA);
        aura.Stats()[1].setTalento(0, 50, 3, 50);
        aura.Stats()[0].setValorBase(AuraData.DOT_LIFEROT_DAÑOPORTICK);
        aura.Stats()[0].setTalento(0, 50, 5, 2.5);
        Myrran.getMundo().ListadeAuras().add(aura);
    }
    
    public static void CargarSpellTipos ()
    {
        TipoSpell stipo = new TipoSpell();
        stipo.setNombre("Especial");
        StatSpell stat= new StatSpell();
        stat.Nombre = "Casting Time";
        stat.setValorBase(50);
        stat.setTalento(0, 0);
        stipo.Stats().add(stat);
        Myrran.getMundo().ListadeTiposSpells().add(stipo);
        
        stipo = new TipoSpell();
        stipo.setNombre("Proyectil");
        stat = new StatSpell ();
        stat.Nombre = "Casting Time";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        stat= new StatSpell();
        stat.Nombre = "Daño";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        stat= new StatSpell();
        stat.Nombre = "Velocidad";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        stat= new StatSpell();
        stat.Nombre = "Duracion";
        stat.setTalento(0, 0);
        stipo.Stats().add(stat);
        Myrran.getMundo().ListadeTiposSpells().add(stipo);
        
        stipo = new TipoSpell();
        stipo.setNombre("SelfHeal");
        stat= new StatSpell();
        stat.Nombre = "Casting Time";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        stat = new StatSpell ();
        stat.Nombre = "Curacion";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        
        Myrran.getMundo().ListadeTiposSpells().add(stipo);          
    }
    
    public static void CargarAuraTipos ()
    {
        TipoSpell stipo = new TipoSpell();
        stipo.setNombre("Snare");
        StatSpell stat= new StatSpell();
        stat.Nombre = "Ralentiza";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        stat= new StatSpell();
        stat.Nombre = "Duracion";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        Myrran.getMundo().ListadeTiposAuras().add(stipo);
        
        stipo = new TipoSpell();
        stipo.setNombre("HOT");
        stat= new StatSpell();
        stat.Nombre = "Curacion/Tick";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        stat= new StatSpell();
        stat.Nombre = "Duracion";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        Myrran.getMundo().ListadeTiposAuras().add(stipo);
        
        stipo = new TipoSpell();
        stipo.setNombre("DOT");
        stat= new StatSpell();
        stat.Nombre = "Daño/Tick";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        stat = new StatSpell ();
        stat.Nombre = "Duracion";
        stat.setTalento(0, 50);
        stipo.Stats().add(stat);
        Myrran.getMundo().ListadeTiposAuras().add(stipo);
    }
    
}
