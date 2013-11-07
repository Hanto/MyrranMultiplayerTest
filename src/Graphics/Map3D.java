/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import IO.KeyInputManager;
import DATA.MiscData;
import Auras.Aura;
import Geo.Mundo;
import IO.MouseInputManager;
import IO.UIManager;
import Main.Myrran;
import MobGhost.GhostNPC;
import MobGhost.GhostPlayer;
import Mobiles.Personaje;
import Mobiles.Player;
import WorldBuilder.WorldEditor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Cursor;
import java.util.Calendar;


/**
 *
 * @author Hanto
 */
public class Map3D extends JPanel
{
    private static final int UPDATE_PERIODO = 0;
    private static int FPS;
    private static int FramesRenderizadosUltimoSegundo=0;
    private static long TiempoRenderUltimoFrame;
    
    private static final String title = "Myrran";
    private static final int Map3d_maxX=MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION;
    private static final int Map3d_maxY=MiscData.MAP3D_WINDOWVERTIZALRESOLUTION;
    private static final int TileSize=MiscData.MAP3D_TILESIZE;
    private static Color colorGrid = MiscData.MAP3D_COLORGRID;
    
    private static int HoverX;
    private static int HoverY;
    
    private static int HoverAlpha;
    private static double HoverAlphaInc;
    private static int HoverAlphaContador = 0;
    private CamaraM camara = new CamaraM ();
    public static Plantilla cursor = new Plantilla ();
    
    public static Image TerrenoSelIcon;
    
    public CamaraM getCamara()                           { return camara; }
    public void setHoverX (int x)                       { HoverX = x; }
    public void setHoverY (int y)                       { HoverY = y; }
    
    public void addSCT (double X, double Y, String Texto, int Duracion, Color color)
    {
        CombatText sct = new CombatText();
        sct.setPosX(X);
        sct.setPosY(Y);
        sct.setTexto(Texto);
        sct.setDuracion(Duracion);
        sct.setColor(color);
        sct.start();
    }
    
    //Constructor
    public Map3D ()
    
    { 
        Myrran.getMundo().player = new Player();
        
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        
        Boolean [][] cforma = {{true,false},
                                {false,false}};
        cursor.setForma(cforma);
        cursor.setPuntero(0, 0);
        camara.setTarget(Myrran.getMundo().getPlayer());
        
        try { Mundo.mapa = Myrran.getMundo().ListaMapas().get(WorldEditor.getnumMapa());} catch (Exception e) {}
        
        setPreferredSize(new Dimension (Map3d_maxX, Map3d_maxY));
        final JFrame frame = new JFrame(title);
        
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);          // center the application window
         
        URL url = getClass().getClassLoader().getResource(MiscData.MAP3D_TERRENOSELECCIONADO_FILENAME);
        if (url == null) {System.out.println ("TargetIcon no se encuentra.");}
        else { try { TerrenoSelIcon = ImageIO.read(url);} catch (Exception e) {} }
        
        
        this.addMouseListener(new MouseInputManager());
        
        this.addMouseMotionListener(new MouseInputManager());
        
        this.addMouseWheelListener(new MouseInputManager());
        
        frame.addKeyListener(new KeyInputManager());
        
        new Thread ( new Runnable ()
        {
            @Override
            public void run() 
            {   
                while (true) 
                {
                    repaint();
                    try {Thread.sleep(UPDATE_PERIODO);} 
                    catch (InterruptedException e) {return;}
                }
            }
        }).start(); // called back run()
    }
    
    public void printCursor (Graphics g, int x, int y, Color color)
    {   
        //http://www.kodejava.org/examples/893.html
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color (color.getRed(),color.getGreen(),color.getBlue(),HoverAlpha));
        
        Rectangle2D.Double r = new Rectangle2D.Double(x*TileSize-camara.getEsquinaSupIzdaX(),y*TileSize-camara.getEsquinaSupIzdaY(),TileSize,TileSize);
     
        g2.fill(r);
    }
    
    public void DibujarCursor (Graphics g)
    {
                
        if (HoverX >=0 && HoverY >= 0 && HoverX <= Myrran.getMundo().getMapaMaxX() && HoverY <= Myrran.getMundo().getMapaMaxY())
        {
            for (int i=0;i<cursor.getAltura();i++)
            {
                for (int j=0;j<cursor.getAnchura();j++)
                {
                    if (cursor.getForma(i, j))
                    {
                        printCursor(g,HoverX+i-cursor.getX(),HoverY+j-cursor.getY(),Color.GREEN);
                    }
                }
            }
        }
    }
    
    public void printBorder (Graphics g, String string, int x, int y, Color borde, Color interior)
    {
        g.setColor(borde);
        g.drawString(string, x+1, y-1);
        g.drawString(string, x+1, y+1);
        g.drawString(string, x-1, y-1);
        g.drawString(string, x-1, y+1);
        g.drawString(string, x,   y-1);
        g.drawString(string, x,   y+1);
        g.drawString(string, x-1,   y);
        g.drawString(string, x+1,   y);
        g.setColor(interior);
        g.drawString(string, x, y);
    }
    
    public void printSCT (Graphics g, int x, int y, String string, int duracion, Color color)
    {
        //g.setFont(new Font("Verdana", Font.plain, 12));
        
        g.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();
        int msgWidth = fm.stringWidth(string);  //nos da el ancho del texto
        int msgAscent = fm.getAscent();         //nos da el alto del texto
        
        x = x -msgWidth /2;                     //de esta forma podemos centrar el texto justo en el centro
        y = y +msgAscent /2;
        
        float alpha;
        if (duracion>(300)) {alpha=250;}
        else {alpha= duracion/300f*250f;}
        
        if (alpha > 254 || alpha <=0) alpha=0;    //Para los casos en los que por estar sincronizado el thread, tarda un poco en quitar el elemento, y este aparece con la duracion negativa
        
        Color cborde = new Color (Color.BLACK.getRed(),Color.BLACK.getGreen(),Color.ORANGE.getBlue(),Math.round(alpha)+1);
        Color cinterior = new Color (color.getRed(),color.getGreen(),color.getBlue(),Math.round(alpha)+1);
        printBorder (g, string, x-camara.getEsquinaSupIzdaX(), y-camara.getEsquinaSupIzdaY(), cborde, cinterior);
        
    } 
    
    public void DibujarGrid (Graphics g)
    {
        g.setColor(colorGrid);
        for (int i=0;i<Map3d_maxX+TileSize;i=i+TileSize) {g.drawLine(i-camara.getEsquinaSupIzdaX()%TileSize, 0, i-camara.getEsquinaSupIzdaX()%TileSize, Map3d_maxY);}
        for (int i=0;i<Map3d_maxY+TileSize;i=i+TileSize) {g.drawLine(0, i-camara.getEsquinaSupIzdaY()%TileSize, Map3d_maxX, i-camara.getEsquinaSupIzdaY()%TileSize);} 
    } 
    
    public void DibujarSCT (Graphics g)
    {
        synchronized (Myrran.getMundo().ListaSCT()) {
        for (int i=0;i<Myrran.getMundo().ListaSCT().size();i++)
        {   
            printSCT (g, Myrran.getMundo().ListaSCT().get(i).getX(), Myrran.getMundo().ListaSCT().get(i).getY(), 
                         Myrran.getMundo().ListaSCT().get(i).getTexto(), Myrran.getMundo().ListaSCT().get(i).getDuracion(), Myrran.getMundo().ListaSCT().get(i).getColor());
        }}
    } 
    
    public void DibujarTileMap (Graphics g)
    {
        for (int pixelY=camara.getEsquinaSupIzdaY();pixelY<camara.getEsquinaInfDchaY()+TileSize;pixelY=pixelY+TileSize)
        {
            for (int pixelX=camara.getEsquinaSupIzdaX();pixelX<camara.getEsquinaInfDchaX()+TileSize;pixelX=pixelX+TileSize)
            { 
                int TileY = pixelY/TileSize;int TileX = pixelX/TileSize;
                try 
                {
                    if (TileX>= 0 && TileY>=0 && TileX<Myrran.getMundo().getMapaMaxX() && TileY<Myrran.getMundo().getMapaMaxY() && Mundo.mapa.map()[TileX][TileY].tieneTerrenoBase()) 
                    {
                        int id = Mundo.mapa.map()[TileX][TileY].getTerrenoBase();
                        Image img = Myrran.getMundo().ListaTerrenos().get(id).getIMG();
                        g.drawImage(img,pixelX-camara.getEsquinaSupIzdaX()-pixelX%TileSize, pixelY-camara.getEsquinaSupIzdaY()-pixelY%TileSize, null);
                    }
                } catch (Exception e) {} 
            }
        }
    }
       
    public void DibujarAurasMobsCercanos (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        for (int i=0;i<Myrran.getMundo().ListadeGhostNPCsCercanos().size();i++)
        { 
            GhostNPC mob = Myrran.getMundo().ListadeGhostNPCsCercanos().get(i);
            if (!mob.ListadeAurasPorID().isEmpty())
            {
                for (int j=0;j<mob.ListadeAurasPorID().size();j++)
                {             
                    Image IMG = Mundo.getAura(mob.ListadeAurasPorID().get(j)).getIMG();
                
                    g2.drawImage(IMG, mob.getX()+j*9-camara.getEsquinaSupIzdaX(), mob.getY()-camara.getEsquinaSupIzdaY(), null);       
                }
            }
        }
    }
    
    public void DibujarAurasPlayersCercanos (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        for (int i=0;i<Myrran.getMundo().ListadeGhostPlayersCercanos().size();i++)
        { 
            GhostPlayer player = Myrran.getMundo().ListadeGhostPlayersCercanos().get(i);
            if (!player.ListadeAurasPorID().isEmpty())
            {
                for (int j=0;j<player.ListadeAurasPorID().size();j++)
                {             
                    Image IMG = Mundo.getAura(player.ListadeAurasPorID().get(j)).getIMG();
                
                    g2.drawImage(IMG, player.getX()+j*9-camara.getEsquinaSupIzdaX(), player.getY()-camara.getEsquinaSupIzdaY(), null);       
                }
            }
        }
    }      
    
    public void DibujarAurasPlayer (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Personaje Player = Myrran.getMundo().getPlayer();
        
        synchronized (Player.ListadeAuras()) {
        if (!Player.ListadeAuras().isEmpty())
        {
            for (int j=0;j<Player.ListadeAuras().size();j++)
            {             
                Aura aura = Player.ListadeAuras().get(j);
                if (aura.getDuracion()>=aura.getDuracionMax()) {Player.ListadeAuras().remove(j);break;}                     //Arregla Auras bugeados
                Image IMG = Myrran.getMundo().ListadeAuras().get(aura.getID()).getIMG();    
                g2.drawImage(IMG, Player.getX()+j*9-camara.getEsquinaSupIzdaX(), Player.getY()-camara.getEsquinaSupIzdaY(), null);       
            }
        }}
    }
    
    public void DibujarMinimapa (Graphics g, int miniMap_maxX, int miniMap_maxY)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Rectangle2D.Double f = new Rectangle2D.Double(10,10,miniMap_maxX*4,miniMap_maxY*4);
        g2.setColor(Color.BLACK);
        g2.fill(f);
        
        for (int y=0;y<miniMap_maxY;y++)
        {
            for (int x=0;x<miniMap_maxX;x++)
            {                          
                try 
                {   
                    int ZoomX = (miniMap_maxX-Map3d_maxX/TileSize)/2;
                    int ZoomY = (miniMap_maxY-Map3d_maxY/TileSize)/2;
                
                    int TileX = x + (camara.getEsquinaSupIzdaX())/TileSize -ZoomX;
                    int TileY = y + (camara.getEsquinaSupIzdaY())/TileSize -ZoomY;
                    
                    int TerrenoID;
                    
                    Color c = Mundo.mapa.map()[TileX][TileY].getColor();
                    if (TileX==(camara.getTarget().getX()/TileSize) && TileY==(camara.getTarget().getY()/TileSize)) {c = Color.RED;}
                    
                    Rectangle2D.Double r = new Rectangle2D.Double(x*4+10,y*4+10,3,3);
                    g2.setColor(c);
                    g2.fill(r);
                    
                } catch (Exception e) {}
            }
        }   
    }        
            
    
    public void DibujarFPS (Graphics g)
    {
            FramesRenderizadosUltimoSegundo++;
            Calendar lCDateTime = Calendar.getInstance();
            long TiempoActual = lCDateTime.getTimeInMillis();
            if (TiempoActual-TiempoRenderUltimoFrame > 1000)
            {
                FPS = FramesRenderizadosUltimoSegundo;
                TiempoRenderUltimoFrame = TiempoActual;
                FramesRenderizadosUltimoSegundo=0;
            }
            printText(g, MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION-38+camara.getEsquinaSupIzdaX(), 15+camara.getEsquinaSupIzdaY(), Integer.toString(Math.round(FPS))+" fps" , 18,Color.ORANGE, false);
    }        
    
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);  // paint background
        setBackground(Color.BLACK);
              
        DibujarTileMap (g);
        
        DibujarGrid (g);
        
        RenderSprites.RenderSprites(g);
        
        if (Myrran.getMundo().getPlayer().getSkillSeleccionado() == Mundo.getSpellID("Editar") || Myrran.getMundo().getPlayer().getSkillSeleccionado() == Mundo.getSpellID("Plantar")) DibujarCursor (g);
        
        DibujarAurasMobsCercanos (g);
        
        DibujarAurasPlayersCercanos (g);
        
        DibujarAurasPlayer (g);
        
        DibujarMinimapa (g, 30, 30);
        
        UIManager.DibujarBarraSkills1 (g);
        
        UIManager.DibujarBarraTiposPlantilla(g);
        
        UIManager.DibujarBarraTiposTerreno(g);
        
        UIManager.UIDibujarSpellTooltip(g);
        
        UIManager.UIDibujarSpellDetails(g);
        
        DibujarSCT (g);
        
        DibujarFPS (g);
        
        UpdateCursor();
    }
    
    public static void main(String[] args) 
    {
      // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
        public void run() 
        {
            new Map3D ();
        }
      });
   }
       
    public void UpdateCursor ()
    {   
        HoverAlphaContador++;
        if (HoverAlphaContador >= (FPS/60))
        {
            if (HoverAlpha > 130) HoverAlphaInc = -2;
            if (HoverAlpha < 50) HoverAlphaInc = 2;
            HoverAlpha = HoverAlpha + (int)HoverAlphaInc;
            HoverAlphaContador = 0;
        }
    }
    
    public void printTextBorder (Graphics g, String string, int x, int y, Color interior)
    {
        g.setColor(Color.BLACK);
        g.drawString(string, x+1, y-1);
        g.drawString(string, x+1, y+1);
        g.drawString(string, x-1, y-1);
        g.drawString(string, x-1, y+1);
        g.drawString(string, x,   y-1);
        g.drawString(string, x,   y+1);
        g.drawString(string, x-1,   y);
        g.drawString(string, x+1,   y);
        g.setColor(interior);
        g.drawString(string, x, y);
    }
    
    public void printText (Graphics g, int x, int y, String string, int size, Color color, Boolean pulse)
    {
        g.setFont(new Font("Arial", Font.BOLD, size));
        FontMetrics fm = g.getFontMetrics();
        int msgWidth = fm.stringWidth(string);  //nos da el ancho del texto
        int msgAscent = fm.getAscent();         //nos da el alto del texto
        
        x = x -msgWidth /2;                     //de esta forma podemos centrar el texto justo en el centro
        y = y +msgAscent /2;
        
        float alpha;
        
        if (pulse) 
        {   
            Color cinterior = new Color (color.getRed(),color.getGreen(),color.getBlue(),HoverAlpha/2+160);
            printTextBorder (g, string, x-camara.getEsquinaSupIzdaX(), y-camara.getEsquinaSupIzdaY(), cinterior);
        }
        else 
        {
            Color cinterior = new Color (color.getRed(),color.getGreen(),color.getBlue(),255);
            printTextBorder (g, string, x-camara.getEsquinaSupIzdaX(), y-camara.getEsquinaSupIzdaY(), cinterior);
        }
    
    }    
    
}

/*
    public void DibujarTileMap (Graphics g)
    {
        for (int pixelY=0;pixelY<Constantes.MAP3D_WINDOWVERTIZALRESOLUTION;pixelY++)
        {
            for (int pixelX=0;pixelX<Constantes.MAP3D_WIDOWHORIZONTALRESOLUTION;pixelX++)
            {      
                int TileY = (pixelY+camara.getEsquinaSupIzdaY())/TileSize;
                int TileX = (pixelX+camara.getEsquinaSupIzdaX())/TileSize;
                
                if ((pixelX+player.getX()-20)%TileSize == 0 && (pixelY+player.getY()-20)%TileSize == 0) {
                try 
                {
                    int id = mapa.getTemplate()[TileX][TileY];
                    Image img = Myrran.getMundo().ListaTerrenos().get(id).getBitmap();
                    g.drawImage(img,pixelX,pixelY, null);
                    ;}
                catch (Exception e) {;}}   
            }
        }
    }*/
/*
    public void DibujarTileMap (Graphics g)
    {
        for (int pixelY=player.getY()-Map3d_maxY/2;pixelY<Map3d_maxY+player.getY();pixelY=pixelY+TileSize)
        {
            for (int pixelX=player.getX()-Map3d_maxY/2;pixelX<Map3d_maxX+player.getX();pixelX=pixelX+TileSize)
            { 
                if (pixelX/TileSize==cursorX && pixelY/TileSize == cursorY) {;} 
                {   
                    int TileY = pixelY/TileSize;
                    int TileX = pixelX/TileSize;
                    try 
                    {
                        int id = Myrran.getMundo().ListaMapas().get(WorldEditor.getnumMapa()).getTemplate()[TileX][TileY];
                        g.drawImage(Myrran.getMundo().ListaTerrenos().get(id).getBitmap(), pixelX-player.getX()+Map3d_maxY/2, pixelY-player.getY()+Map3d_maxY/2, null);}
                    catch (Exception e) {;}   
                }
            }
        }
    }      
 
    public void DibujarTileMap (Graphics g)
    {
        for (int pixelY=0;pixelY<Constantes.MAP3D_WINDOWVERTIZALRESOLUTION;pixelY=pixelY+TileSize)
        {
            for (int pixelX=0;pixelX<Constantes.MAP3D_WIDOWHORIZONTALRESOLUTION;pixelX=pixelX+TileSize)
            { 
                int TileY = (pixelY+camara.getEsquinaSupIzdaY())/TileSize;int TileX = (pixelX+camara.getEsquinaSupIzdaX())/TileSize;
                if (TileY>0&&TileX>0) {
                try 
                {   
                    int id = mapa.getTemplate()[TileX][TileY];
                    Image img = Myrran.getMundo().ListaTerrenos().get(id).getBitmap();
                    g.drawImage(img,pixelX-camara.getEsquinaSupIzdaX()%TileSize, pixelY-camara.getEsquinaSupIzdaY()%TileSize, null);}
                catch (Exception e) {;}}
            }
        }
    }
    
    public void DibujarTarget (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(camara.getTarget().getImg(),
        MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2,MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2,                    //Vertice superior izdo del destino de la imagen
        MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2+TileSize,MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2+TileSize,  //Vertice inferior derecho del destino de la imagen, por si queremos hacer Zoom
        camara.getTarget().getFrameActual_Xcord(),camara.getTarget().getFrameActual_Ycord(),                                                    //vertice superior izdo de la imagen origen que copiaremos
        camara.getTarget().getFrameActual_Xcord()+MiscData.MAP3D_TILESIZE,camara.getTarget().getFrameActual_Ycord()+MiscData.MAP3D_TILESIZE,null);      // vertice inferior derecho de la imagen origen que copiaremos
    }
    
    public void DibujarMobsCercanos (Graphics g)
    {
        for (int i=0;i<Myrran.getMundo().ListadeMobsCercanos().size();i++)
        { 
            Personaje mob = Myrran.getMundo().ListadeMobsCercanos().get(i);
            if (mob.getActualHPs()<=0) {Myrran.getMundo().ListadeMobsCercanos().remove(i);break;}                   //Arregla Mobs Muertos bugeados
            Graphics2D g2 = (Graphics2D) g;
            
            g2.drawImage(mob.getImg(),
            mob.getX()-camara.getEsquinaSupIzdaX(),mob.getY()-camara.getEsquinaSupIzdaY(),                          //Vertice superior izdo del destino de la imagen
            mob.getX()-camara.getEsquinaSupIzdaX()+TileSize,mob.getY()-camara.getEsquinaSupIzdaY()+TileSize,        //Vertice inferior derecho del destino de la imagen, por si queremos hacer Zoom
            mob.getFrameActual_Xcord(),mob.getFrameActual_Ycord(),                                                  //vertice superior izdo de la imagen origen que copiaremos
            mob.getFrameActual_Xcord()+TileSize,mob.getFrameActual_Ycord()+TileSize,null);                          // vertice inferior derecho de la imagen origen que copiaremos
        }  
    }        
    
    
    public synchronized void DibujarPepos (Graphics g)
    {   
        for (int i=0;i<Myrran.getMundo().ListadePeposCercanos().size();i++)
        {
            Proyectil pepo = Myrran.getMundo().ListadePeposCercanos().get(i);
            if (pepo.getDuracion()>=pepo.getDuracionMaxima()) {Myrran.getMundo().ListadePeposCercanos().remove(i);break;}             //Arregla pepos bugeados
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(pepo.getImg(),
                         pepo.getX()-camara.getEsquinaSupIzdaX(),pepo.getY()-camara.getEsquinaSupIzdaY(),                   //Vertice superior izdo del destino de la imagen
                         pepo.getX()-camara.getEsquinaSupIzdaX()+TileSize,pepo.getY()-camara.getEsquinaSupIzdaY()+TileSize, //Vertice inferior derecho del destino de la imagen, por si queremos hacer Zoom
                         pepo.getFrameActual_Xcord(),pepo.getFrameActual_Ycord(),                                           //vertice superior izdo de la imagen origen que copiaremos
                         pepo.getFrameActual_Xcord()+TileSize,pepo.getFrameActual_Ycord()+TileSize,null);
        }
    }
    
    
    */