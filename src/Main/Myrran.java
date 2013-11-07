/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import BDD.DerbyCargar;
import DATA.MiscData;
import DATA.SpellData;
import GDX.GameplayScreen;
import Geo.Mundo;
import WorldBuilder.WorldEditor;
import Graphics.Map3D;
import Multiplayer.MClient;
import Multiplayer.MServer;
import Multiplayer.Updater;
import WorldBuilder.SpellEditor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.FPSLogger;
import org.lwjgl.LWJGLException;

/**
 *
 * @author Hanto
 */
public class Myrran extends Game
{
    public static Boolean isServer = false;
    public static Boolean isClient = false;
    
    private static Mundo mundo = new Mundo ();
    private static Map3D map3d;
    private static WorldEditor editor;
    private static SpellEditor spellEd;
    
    public static Mundo getMundo()              { return mundo;}
    public static Map3D getMap3D()              { return map3d;}
    public static WorldEditor getEditor()       { return editor; }
    
    public static MServer server;
    public static MClient client;
    
    public GameplayScreen getGameplayScreen()   { return new GameplayScreen(this); }
    public static final String LOG = Myrran.class.getSimpleName();
    private FPSLogger fpsLogger;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws LWJGLException 
    {   
        Inicializar ();   
    }
    
    public static void Inicializar ()
    {
        DerbyCargar dcargar = new DerbyCargar ();
        dcargar.CargarTodo();
        
        InicializarLibGDX();
        
        DATA.LoadData.CargarCursores();
        DATA.LoadData.CargarSpellTipos();
        DATA.LoadData.CargarAuraTipos();
        DATA.LoadData.CargarAuras();
        DATA.LoadData.CargarSkills();
        
        map3d = new Map3D ();
        map3d.setVisible(true);
        
        editor = new WorldEditor ();
        spellEd = new SpellEditor ();
        editor.setVisible(true);
        spellEd.setVisible(true);
        
        try { server = new MServer(); isServer = true;} catch (Exception e) {}
        client = new MClient(); isClient = true;
        
        Updater up = new Updater();
    }
    
    public static void InicializarLibGDX()
    {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Myrran";
        cfg.vSyncEnabled = false;
        cfg.useGL20 = true;
        cfg.width=MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION;
        cfg.height=MiscData.MAP3D_WINDOWVERTIZALRESOLUTION;
        cfg.addIcon(SpellData.FIREBALL_ICONOFICHERO, Files.FileType.Internal);
        new LwjglApplication(new Myrran(), cfg);   
    }
    
    
    @Override
    public void create() 
    { 
        fpsLogger = new FPSLogger();
        Gdx.app.log( Myrran.LOG ,"Creando GameplayScreen");
        setScreen(getGameplayScreen()); 
    }
    
    @Override
    public void render() 
    {   super.render(); 
        fpsLogger.log();}
    
    @Override
    public void setScreen (Screen screen)
    {   super.setScreen(screen);
        Gdx.app.log( Myrran.LOG, "Cambiando a pantalla: " + screen.getClass().getSimpleName());}
    
    @Override
    public void resize(int anchura, int altura) 
    {   super.resize(anchura, altura);
        Gdx.app.log( Myrran.LOG, "Redimensionando juego a: "+anchura+" x "+altura);}

    @Override
    public void pause() 
    {   super.pause(); 
        Gdx.app.log( Myrran.LOG, "Juego pausado");}

    @Override
    public void resume() 
    {   super.resume(); 
        Gdx.app.log( Myrran.LOG, "Juego reanudado");}

    @Override
    public void dispose() 
    {   super.dispose(); 
        Gdx.app.log( Myrran.LOG, "Eliminando basura");}
}
