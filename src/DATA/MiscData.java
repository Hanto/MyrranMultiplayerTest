/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

import com.badlogic.gdx.graphics.Texture;
import java.awt.Color;

/**
 *
 * @author Hanto
 */
public class MiscData 
{   
    //SCT
    public final static int SCT_TIEMPO_MENSAJE = 2000;
    public final static Color SCT_PLAYER_SELFHEAL_COLOR = Color.GREEN;
    public final static Color SCT_OTHER_SELFHEAL_COLOR = Color.GREEN;
    public final static Color SCT_PLAYER_DAMAGES_OTHER = Color.RED;
    public final static Color SCT_OTHER_DAMAGES_PLAYER = Color.ORANGE;
    
    
    //RESOURCES
    public final static String PATH_SOUND_RES = "zSound/res/";
    public final static String PATH_ICONOS_RES = "zIconos/res/";
    public final static String PATH_PCSPRITES_RES = "zPC_Sprites/res/";
    public final static String PATH_NPCSPRITES_RES = "zNPC_Sprites/res/";
    public final static String PATH_SPELLSPRITES_RES = "zSpellSprites/res/";
    public final static String PATH_UI_RES = "zUI/res/";
    public final static String PATH_TILES_RES = "zTiles/res/";
    public final static String PATH_ESTATICOS_RES = "zEstaticos/res/";
    public final static String PATH_AURAS_RES = "zAuras/res/";
    
    public final static String [] RESOURCES_SPELLIconos = { MiscData.PATH_ICONOS_RES + "Editar.png",
                                                            MiscData.PATH_ICONOS_RES + "Espectro.png",
                                                            MiscData.PATH_ICONOS_RES + "FireBall.png",
                                                            MiscData.PATH_ICONOS_RES + "IceBall.png",
                                                            MiscData.PATH_ICONOS_RES + "Plantar.png",
                                                            MiscData.PATH_ICONOS_RES + "PoisonBolt.png",
                                                            MiscData.PATH_ICONOS_RES + "Renew.png"
                                                          };
    
    public final static String [] RESOURCES_SPELLSprites= { MiscData.PATH_SPELLSPRITES_RES + "SpellBalls_01n.png",
                                                            MiscData.PATH_SPELLSPRITES_RES + "SpellBalls_02n.png",
                                                            MiscData.PATH_SPELLSPRITES_RES + "SpellBalls_03n.png",
                                                            MiscData.PATH_SPELLSPRITES_RES + "SpellBalls_04n.png"
                                                          };
    
    //Multiplayer
    public final static int MULTIPLAYER_RefreshRate = 50;
    
    //Nameplates
    public final static int NAMEPLATES_TIPO_PLAYER=0;
    public final static int NAMEPLATES_TIPO_ENEMIGO=1;
    public final static int NAMEPLATES_TIPO_PROYECTIL=2;
    public final static int NAMEPLATES_TIPO_STATIC = 3;
    
    public final static int NAMEPLATES_TIPO_BARRAVIDA = 0;
    public final static int NAMEPLATES_TIPO_BARRACASTEO = 1;
    
    public final static Color NAMEPLATE_BARRADEVIDA_PLAYER_COLORBASE = new Color (190 , 123, 0);
    public final static Color NAMEPLATE_BARRADEVIDA_PLAYER_COLORFINAL = new Color (220, 200, 0);
    public final static Color NAMEPLATE_BARRADEVIDA_ENEMIGO_COLORBASE = new Color (123 , 0, 0);
    public final static Color NAMEPLATE_BARRADEVIDA_ENEMIGO_COLORFINAL = new Color (250, 0, 0);
    
    public final static Color NAMEPLATE_BARRADECASTEO_PLAYER_COLORBASE = new Color (0 , 133, 100);
    public final static Color NAMEPLATE_BARRADECASTEO_PLAYER_COLORFINAL = new Color (0, 233, 160);
    public final static Color NAMEPLATE_BARRADECASTEO_ENEMIGO_COLORBASE = new Color (0 , 133, 100);
    public final static Color NAMEPLATE_BARRADECASTEO_ENEMIGO_COLORFINAL = new Color (0, 233, 160);
    
    public final static int NAMEPLATES_PLAYER_ALTURARESPECTOCABEZA = 4;
    public final static int NAMEPLATES_ENEMIGOS_ALTURARESPECTOCABEZA = 4;
    public final static int NAMEPLATES_PLAYER_GROSORREBORDE = 1;
    public final static int NAMEPLATES_ENEMIGOS_GROSORREBORDE = 1;
    public final static int NAMEPLATES_PLAYER_BARRADEVIDAGROSOR = 3;
    public final static int NAMEPLATES_ENEMIGOS_BARRADEVIDAGROSOR = 3;
    public final static int NAMEPLATES_PLAYER_BARRADECASTEOGROSOR = 3;
    public final static int NAMEPLATES_ENEMIGOS_BARRADECASTEOGROSOR = 3;
    
    //TERRENO
    public final static String TERRENO_NOMBRE_NEW = "Nombre";
    public final static String TERRENO_IMAGEN_NEW = "#";
    public final static String TERRENO_DIRECCIONBITMAP_NEW = "Images/BlankTile.png";
    public final static Color TERRENO_COLORTERRENO_NEW = Color.LIGHT_GRAY;
    
    //ESTATICO
    public final static String ESTATICO_NOMBRE_NEW = "Nombre";
    public final static String ESTATICO_DIRECCIONBITMAP_NEW = "Images/BlankEstatico.png";
    
    //MUNDO
    public final static int MUNDO_MAPAMAXY = 300;
    public final static int MUNDO_MAPAMAXX = 300;
    
    //MAP3D
    public final static String MAP3D_TARGET_FILENAME = MiscData.PATH_UI_RES+"Target.png";
    public final static String MAP3D_TERRENOSELECCIONADO_FILENAME = MiscData.PATH_UI_RES+"TerrenoSeleccionado.png";
    
    public final static int MAP3D_TILESIZE = 32;
    public final static int MAP3D_WIDOWHORIZONTALRESOLUTION=1024;
    public final static int MAP3D_WINDOWVERTIZALRESOLUTION=768;
    public final static Color MAP3D_COLORGRID = Color.BLACK;
    
    //Buscador
    public final static int BUSCADOR_ALCANCE = 20;
    
    //Sprite
    public final static String SPRITE_IMGFILENAME_PLAYER = MiscData.PATH_PCSPRITES_RES+"Player.png";
    public final static String SPRITE_IMGFILENAME_GREAPER = MiscData.PATH_NPCSPRITES_RES+"GrimReaper.png";

    
    public final static double SPRITE_VELOCIDADANIMACIONES = 200/MiscData.MAP3D_REFRESH_RATE;
    public final static double SPRITE_MAXVELOCIDADPLAYER = 0.8;
    public final static int SPRITE_IA_SLEEP_REFRESH_RATE=1000;
    public final static int SPRITE_IA_WAIT_REFRESH_RATE=500;
    public final static int SPRITE_IA_AGGRO_REFRESH_RATE=15;
    public static final int SPELL_UPDATE_RATE = 15;
    public final static int MAP3D_REFRESH_RATE = 15; //Frecuencia de actualizacion de la pantalla
    public final static int SPRITE_HITBOX_Modifier = 5;
    
    //GrimReaper
    public final static double NPC_GrimReaper_VelocidadMax = 0.3;
    
    //Player
    public final static int PC_BARRASKILLS_MaxSkills = 30;
    
    //Plantillas
    public final static String CURSOR_DIRECCIONBITMAP_CURSOR001 = MiscData.PATH_UI_RES+"Cursor_01.png";
    public final static String CURSOR_DIRECCIONBITMAP_CURSOR002 = MiscData.PATH_UI_RES+"Cursor_02.png";
    public final static String CURSOR_DIRECCIONBITMAP_CURSOR003 = MiscData.PATH_UI_RES+"Cursor_03.png";
    public final static String CURSOR_DIRECCIONBITMAP_CURSOR004 = MiscData.PATH_UI_RES+"Cursor_04.png";
    public final static String CURSOR_DIRECCIONBITMAP_CURSOR005 = MiscData.PATH_UI_RES+"Cursor_05.png";
    
    public final static String PLANTILLA_IMGicono_1x1 = MiscData.PATH_UI_RES+"Plantilla1x1.png";
    public final static String PLANTILLA_IMGicono_2x2 = MiscData.PATH_UI_RES+"Plantilla2x2.png";
    public final static String PLANTILLA_IMGicono_3x3 = MiscData.PATH_UI_RES+"Plantilla3x3.png";
    public final static String PLANTILLA_IMGicono_4x4 = MiscData.PATH_UI_RES+"Plantilla4x4.png";
    public final static String PLANTILLA_IMGicono_5x5 = MiscData.PATH_UI_RES+"Plantilla5x5.png";
    
    public final static int[] CURSOR_PUNTERO_001 = {0,0};
    public final static Boolean [][] CURSOR_FORMA_001 = {{true,false},{false,false}};
    public final static int[] CURSOR_PUNTERO_002 = {0,0};
    public final static Boolean [][] CURSOR_FORMA_002 = {{true,true},
                                                        {true,true}};
    public final static int[] CURSOR_PUNTERO_003 = {0,0};
    public final static Boolean [][] CURSOR_FORMA_003 = {{true,true,true},
                                                        {true,true,true},
                                                        {true,true,true}};
    public final static int[] CURSOR_PUNTERO_004 = {0,0};
    public final static Boolean [][] CURSOR_FORMA_004 = {{true,true,true,true},
                                                        {true,true,true,true},
                                                        {true,true,true,true},
                                                        {true,true,true,true}};
    public final static int[] CURSOR_PUNTERO_005 = {0,0};
    public final static Boolean [][] CURSOR_FORMA_005 = {{true,true,true,true,true},
                                                         {true,true,true,true,true},
                                                         {true,true,true,true,true},
                                                         {true,true,true,true,true},
                                                         {true,true,true,true,true}};
    //General
    
    //TexturePacker
    public final static String TEXPACK_Carpeta_Imagenes_Origen = "images/";
    public final static String TEXPACK_Carpeta_Imagenes_Destino = "textures/";
    public final static String TEXPACK_Atlas_Nombre = "atlas";
    
    public final static String ATLAS_NPCSprites_LOC = "NPC Sprites/";
    public final static String ATLAS_SpellSprites_LOC = "Spell Sprites/";
    public final static String ATLAS_PlayerSprites_LOC = "Player Sprites/";
    public final static String ATLAS_SpellIcons_LOC = "Spell Icons/";
}

    

