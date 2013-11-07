/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

import Utils.Coordenadas;

/**
 *
 * @author Hanto
 */
public class SpellData 
{      
    
    //TIPOS DE SPELLS:
    public final static int SPELL_TIPO_Special = 0;
    public final static int SPELL_TIPO_Proyectil = 1;
    public final static int SPELL_TIPO_SelfHeal =2;
    public final static int SPELL_MAXTIPOS = 3;
    
    //Editar Terreno
    public final static String EDITARTERRENO_NOMBRE="Editar";
    public final static String EDITARTERRENO_ICONOFICHERO=MiscData.PATH_ICONOS_RES+"Editar.png";
    public final static char EDITARTERRENO_KEYBIND='5';
    public final static int EDITAR_TIPO = SPELL_TIPO_Special;
    
    //Summon Arbol
    public final static String PLANTARARBOL_NOMBRE="Plantar";
    public final static String PLANTARARBOL_ICONOFICHERO=MiscData.PATH_ICONOS_RES+"Plantar.png";
    public final static char PLANTARARBOL_KEYBIND= '6';
    public final static int PLANTARARBOL_TIPO = SPELL_TIPO_Special;
    
    //Summon Spectro
    public final static String SUMMONSPECTRO_NOMBRE="Espectro";
    public final static String SUMMONGSPECTRO_ICONOFICHERO=MiscData.PATH_ICONOS_RES+"Espectro.png";
    public final static char SUMMONGSPECTRO_KEYBIND = '7';
    public final static int SUMMONGSPECTRO_TIPO = SPELL_TIPO_Special;
    public final static String SUMMONSPECTRO_Descripcion = "Invoca una criatura espectro agresiva que atacara al jugador mas cercano";
    
    //Fireball
    public final static String FIREBALL_NOMBRE="Fireball";
    public final static String FIREBALL_ICONOFICHERO=MiscData.PATH_ICONOS_RES+"FireBall.png";
    public final static char FIREBALL_KEYBIND = '1';
    public final static int FIREBALL_TIPO = SPELL_TIPO_Proyectil;
    public final static String FIREBALL_Descripcion = "Poderosa rafaga de energia concentrada en una bola de calor que abrasara a los enemigos del conjurador";
    public final static String FIREBALL_IMGFILENAME= MiscData.PATH_SPELLSPRITES_RES+"SpellBalls_01n.png";
    public final static Coordenadas FIREBALL_ANIMACIONBASE=new Coordenadas (6,1);
    public final static int FIREBALL_DAÑO = 99;
    public final static Boolean FIREBALL_PIERCES=false;
    public final static int FIREBALL_PIERCES2=0;
    public final static int FIREBALL_CASTINGTIME=500;
    public final static double FIREBALL_VELOCIDADMAXIMA=9;
    public final static int FIREBALL_DURACIONMAXIMA=10000;
    
    //Iceball
    public final static String ICEBALL_NOMBRE="Iceball";
    public final static String ICEBALL_ICONOFICHERO=MiscData.PATH_ICONOS_RES+"IceBall.png";
    public final static char ICEBALL_KEYBIND = '2';
    public final static int ICEBALL_TIPO = SPELL_TIPO_Proyectil;
    public final static String ICEBALL_IMGFILENAME= MiscData.PATH_SPELLSPRITES_RES+"SpellBalls_01n.png";
    public final static Coordenadas ICEBALL_ANIMACIONBASE=new Coordenadas (6,5);
    public final static int ICEBALL_DAÑO = 90;
    public final static Boolean ICEBALL_PIERCES=false;
    public final static int ICEBALL_CASTINGTIME=1000;
    public final static double ICEBALL_VELOCIDADMAXIMA=9;
    public final static int ICEBALL_DURACIONMAXIMA=10000;
    
    public final static String ICEBALL_APLICA_AURA = AuraData.SNARE_NOMBRE;
    
    //PoisonBolt
    public final static String POISONBOLT_NOMBRE= "Poison";
    public final static String POISONBOLT_ICONOFICHERO=MiscData.PATH_ICONOS_RES+"PoisonBolt.png";
    public final static char POISONBOLT_KEYBIND = '3';
    public final static int POISONBOLT_TIPO = SPELL_TIPO_Proyectil;
    public final static String POISONBOLT_Descripcion = "Orbe de engergia oscura que corrompe la carne de las criaturas que toca. Lento al principio pero constante, al final de su duracion el daño inflinjido acaba resultando masivo";
    public final static String POISONBOLT_IMGFILENAME = MiscData.PATH_SPELLSPRITES_RES+"SpellBalls_02n.png";
    public final static Coordenadas POISONBOLT_ANIMACIONBASE=new Coordenadas (1,2);
    public final static int POISONBOLT_DAÑO = 10;
    public final static Boolean POISONBOLT_PIERCES=false;
    public final static int POISONBOLT_CASTINGTIME=1000;
    public final static double POISONBOLT_VELOCIDADMAXIMA=12;
    public final static int POISONBOLT_DURACIONMAXIMA=4000;
    
    public final static String POISONBOLT_APLICA_AURA = AuraData.DOT_LIFEROT_NOMBRE;
    
    //Renew
    public final static String RENEW_NOMBRE="Renew";
    public final static String RENEW_ICONOFICHERO=MiscData.PATH_ICONOS_RES+"Renew.png";
    public final static char RENEW_KEYBIND = '4';
    public final static int RENEW_TIPO = SPELL_TIPO_SelfHeal;
    public final static int RENEW_CASTINGTIME=1500;
    public final static int RENEW_HEALDIRECTO = 100;
    
    public final static String RENEW_APLICA_AURA = AuraData.HOTRENEW_NOMBRE;
}
