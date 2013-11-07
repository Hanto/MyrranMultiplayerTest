/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GDX;

import Main.Myrran;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Hanto
 */
public abstract class AbstractScreen implements Screen
{
    protected final Myrran game;
    protected final SpriteBatch batch;
    protected final ShapeRenderer shape;
    protected final Stage stageUI;
    
    protected String getName()          { return getClass().getSimpleName(); }
    
    public AbstractScreen (Myrran game)
    {
        this.game = game;
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.stageUI = new Stage( 0, 0, true);
    }
    
    @Override
    public void render(float delta) 
    {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        stageUI.act(delta);
    }

    @Override
    public void resize(int anchura, int altura) 
    { 
        Gdx.app.log( Myrran.LOG, "Redimensionando pantalla: "+ getName() +" a: "+anchura+" x "+altura);
        stageUI.setViewport(anchura, altura, true);
    }

    @Override
    public void show() 
    { Gdx.app.log( Myrran.LOG, "Enseñando pantalla: " + getName()); 
      Gdx.input.setInputProcessor(stageUI);
    }

    @Override
    public void hide() 
    {   Gdx.app.log( Myrran.LOG, "Ocultando pantalla: " + getName()); 
        dispose();
    }

    @Override
    public void pause() 
    { Gdx.app.log( Myrran.LOG, "Pausando pantalla: " + getName()); }

    @Override
    public void resume() 
    { Gdx.app.log( Myrran.LOG, "Pantalla reanudada: " + getName()); }

    @Override
    public void dispose() 
    {
        Gdx.app.log( Myrran.LOG, "Eliminando basura de pantalla: " + getName());
        if (batch != null) batch.dispose();
        if (shape != null) shape.dispose();
        if (stageUI != null) stageUI.dispose();
    }   
}
