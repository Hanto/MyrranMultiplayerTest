/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GDX;

import DATA.MiscData;
import Geo.Mundo;
import Graphics.SpriteM;
import Main.Myrran;
import Mobiles.Player;
import Utils.Coordenadas;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import static GDX.RenderSprites.ListadeMobiles;


/**
 *
 * @author Hanto
 */
public class GameplayScreen extends AbstractScreen
{
    private static OrthographicCamera camera;
    private static World world;
    private static RayHandler rayHandler;
    
    public static TextureAtlas atlas;
    
    private static Array<SpriteM> ListadeEstaticos = new Array<SpriteM>();
    public static Vector2 foco = new Vector2();
    
    public static Boolean reloadTexturas = false;
    
    
    
    
    public GameplayScreen (Myrran game) { super (game); }
    
    @Override
    public void show ()
    {
        super.show();
        
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        world = new World (new Vector2 (0,-9.8f), false);
        
        ConvertirImagesATexturas();
        CargarTexturas();
        
        //rayHandler.setBlurNum(1);
        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setAmbientLight(1f);
        new PointLight(rayHandler, 1000, new Color(1,1,1,0.7f), 1000, MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2, MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2);
        //light.setSoft(true);
      
    }
    
    @Override
    public void render (float delta)
    {   //como las texturas hay que cargarlas desde el hilo de render, cuando se añaden texturas en el worleditor, lo que se hace es poner al booleano de cargarlas a true
        if (reloadTexturas) { reloadTexturas = false; ConvertirImagesATexturas();CargarTexturas();}
        
        super.render(delta);
        
        foco.set(Myrran.getMundo().getPlayer().getX(), Myrran.getMundo().getPlayer().getY());
        camera.position.x=foco.x;
        camera.position.y=foco.y;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        
        batch.begin();
        renderTileMap();
        batch.end();
        
        renderGrid();
        
        batch.begin();
        RenderSprites();       
        batch.end();
        
        rayHandler.updateAndRender();
        batch.begin();
        batch.end();
        
        //pintamos el stageUI lo ultimo, puesto que es el UI, y no queremos que se vea alterado por la iluminacion, ni tapado por ningun elemento
        stageUI.draw();
    }
    
    public void renderTileMap ()
    {
        ListadeEstaticos.clear();
  
        int Ancho= MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2;
        int Alto = MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2;
        int TileSize = MiscData.MAP3D_TILESIZE;
        Sprite sprite;
        
        //Recorre el rectangulo que forma la pantalla alreddor del personaje jugador identificando y pintando cada Tile.
        for (int TileY=((int)foco.y-Alto)/TileSize; TileY<((int)foco.y+Alto)/TileSize+1; TileY++)
        {
            for (int TileX=((int)foco.x-Ancho)/TileSize; TileX<((int)foco.x+Ancho)/TileSize+1; TileX++)
            {
                try { if (TileX>= 0 && TileY>=0 && TileX<Myrran.getMundo().getMapaMaxX() && TileY<Myrran.getMundo().getMapaMaxY() && Mundo.mapa.map()[TileX][TileY].tieneTerrenoBase()) 
                {       int id = Mundo.mapa.map()[TileX][TileY].getTerrenoBase();
                        sprite = Myrran.getMundo().ListaTerrenos().get(id).getSprite();
                        sprite.setPosition(TileX*TileSize, TileY*TileSize);
                        sprite.draw(batch);
                        
                } } catch (Exception e) {}
                try { if (Mundo.mapa.map()[TileX][TileY].tieneEstatico1())
                {
                    AñadirEstatico(TileX, TileY);
                } } catch (Exception e) {}
            }
        }
    }
    
    public void renderGrid ()
    {
        camera.position.x=Myrran.getMundo().player.getX();
        camera.position.y=Myrran.getMundo().player.getY();
        camera.update();
        
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        
        Player player = Myrran.getMundo().getPlayer();
        int Ancho= MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2;
        int Alto = MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2;
        int TileSize = MiscData.MAP3D_TILESIZE;
        
        for (int TileY=(player.getY()-Alto)/TileSize; TileY<(player.getY()+Alto)/TileSize+1; TileY++)
        { 
            for (int i=(player.getX()-Ancho);i<(player.getX()+Ancho);i=i+2)
            shape.line(i-Myrran.getMundo().player.getX()%30, TileY*32, i+1-Myrran.getMundo().player.getX()%30, TileY*32); 
        }
        for (int TileX=(player.getX()-Ancho)/TileSize; TileX<(player.getX()+Ancho)/TileSize+1; TileX++)
        { 
            for (int i=(player.getY()-Alto);i<(player.getY()+Alto);i=i+2)
            { shape.line(TileX*32, i-Myrran.getMundo().player.getY()%30, TileX*32, i+1-Myrran.getMundo().player.getY()%30); }
        }
        shape.end();
    }
    
    public void DibujarSprite(SpriteM mobile)
    {
        Sprite sprite;
        if (mobile.Tipo < 3)
        {
            sprite = new Sprite (mobile.texture, mobile.FrameActual.X, mobile.FrameActual.Y, 32, 32);
            sprite.flip(false, true);
            sprite.setPosition(mobile.PosXY.X, mobile.PosXY.Y);
            sprite.draw(batch); 
        }
        if (mobile.Tipo == 3)
        {
            sprite = new Sprite (mobile.texture);
            sprite.setPosition(mobile.PosXY.getX(), mobile.PosXY.getY());
            sprite.draw(batch);
        }      
    }
    
    public void RenderSprites()
    {
        RenderSprites.JuntarYOrdenarMobiles();
        
        int ultimoEstaticoDibujado = 0;
        
        for (int i=0;i<ListadeMobiles.size;i++)
        {   
            if (ListadeEstaticos.size>0)
            {
                for (int j=ultimoEstaticoDibujado;j<ListadeEstaticos.size;j++)
                {
                    if (ListadeEstaticos.get(j).PiesXY.getY()<ListadeMobiles.get(i).PiesXY.getY()) { DibujarSprite(ListadeEstaticos.get(j)); ultimoEstaticoDibujado++; }
                    else {break;}
                }
            }
            DibujarSprite (ListadeMobiles.get(i));
        }
        //Si el vector de Estaticos es mas grande que el de mobiles, hay que terminar de dibujarlo
        if (ultimoEstaticoDibujado < ListadeEstaticos.size)
        {
            for (int j=ultimoEstaticoDibujado;j<ListadeEstaticos.size;j++)
            {
                DibujarSprite(ListadeEstaticos.get(j));
            }
        }
    }
    
    
    public static void AñadirEstatico (int TileX, int TileY)
    {
        SpriteM sprite = new SpriteM ();
        sprite.texture = Myrran.getMundo().ListaEstaticos().get(Mundo.mapa.map()[TileX][TileY].getEstatico1()).getTexture();
        sprite.Tipo = MiscData.NAMEPLATES_TIPO_STATIC;
        
        int imgAlto = sprite.texture.getRegionHeight();
        int imgAncho = sprite.texture.getRegionWidth();
        int TileSize = MiscData.MAP3D_TILESIZE;
        
        sprite.PosXY = new Coordenadas ();
        sprite.PosXY.setX(TileX*32- ((imgAncho/2)-TileSize/2));
        sprite.PosXY.setY(TileY*32- (imgAlto-TileSize));
        sprite.PiesXY = new Coordenadas ();
        sprite.PiesXY.setX(TileX*32 - ((imgAncho/2)-TileSize/2));
        sprite.PiesXY.setY(TileY*32 + TileSize-1);
        
        ListadeEstaticos.add(sprite);
    }
    
    @Override
    public void dispose ()
    {
        super.dispose();
        
        if (world != null) world.dispose();
        if (rayHandler != null) rayHandler.dispose();
        if (atlas != null) atlas.dispose();        
    }
    
    public static void ConvertirImagesATexturas()
    {   //Creamos un Atlas de todos los terrenos
        TexturePacker2.process(MiscData.TEXPACK_Carpeta_Imagenes_Origen, MiscData.TEXPACK_Carpeta_Imagenes_Destino, MiscData.TEXPACK_Atlas_Nombre);
    }
    
    public static void CargarTexturas()
    {   //Cargamos ese Atlas en memoria
        atlas = new TextureAtlas(Gdx.files.internal(MiscData.TEXPACK_Carpeta_Imagenes_Destino+MiscData.TEXPACK_Atlas_Nombre+".atlas"));
        //Cargamos en cada uno de los terrenos el Sprite correpondiente a su tipo de terreno en el Atlas, para asi no tener que invocar el findRegion cada vez, puesto que es muy lento
        for (int i=0; i<Myrran.getMundo().ListaTerrenos().size();i++)
        {   //el nombre de cada terreno aparece sin extension, por eso se eliminan los ultimos 4 caracteres
            String nombreRecurso = Myrran.getMundo().ListaTerrenos().get(i).getIMGFile();
            nombreRecurso = nombreRecurso.substring(0, nombreRecurso.length()-4);
            Sprite sprite = new Sprite(atlas.findRegion(nombreRecurso));
            sprite.flip(false, true);
            Myrran.getMundo().ListaTerrenos().get(i).setSprite(sprite);
        }

        for (int i=0; i<Myrran.getMundo().ListaEstaticos().size();i++)
        { 
            String nombreRecurso = Myrran.getMundo().ListaEstaticos().get(i).getIMGFilename();
            nombreRecurso = nombreRecurso.substring(0, nombreRecurso.length()-4);
            Myrran.getMundo().ListaEstaticos().get(i).setTexture(new TextureRegion (atlas.findRegion(nombreRecurso)));
            Myrran.getMundo().ListaEstaticos().get(i).getTexture().flip(false, true);
        }
       
        Myrran.getMundo().getPlayer().texture = new TextureRegion (atlas.findRegion(MiscData.ATLAS_PlayerSprites_LOC+Myrran.getMundo().getPlayer().getNombreRecurso()));        
    }
    
}


/*
 *      Pruebas de actores con el Fireball
 * 
 *      Texture tex = new Texture(Gdx.files.internal(SpellData.FIREBALL_ICONOFICHERO), true);
        tex.setFilter(Texture.TextureFilter.MipMapNearestNearest, Texture.TextureFilter.MipMapNearestNearest);
        Image Fireball = new Image (tex);
        Fireball.getColor().a = 1f;
                
        Action fade = new Action () 
        {

            @Override
            public boolean act(float delta) 
            {
                return true;
            }
        };
        
        Fireball.setTouchable(Touchable.enabled);
        
        Fireball.addListener (new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
            {
                event.getTarget().addAction( Actions.sequence(  Actions.scaleTo(1.3f, 1.3f, 0.5f, Interpolation.sine),Actions.scaleTo(1f, 1f, 0.5f, Interpolation.sine)    ));
                System.out.println("down");
                return true;
            }
        });
        
        Fireball.setWidth(Fireball.getWidth());
        Fireball.setHeight(Fireball.getHeight());
        
        Fireball.setOrigin(16, 16);
        Fireball.setBounds(200, 200, Fireball.getWidth(), Fireball.getHeight());
        
        stageUI.addActor(Fireball);
 */