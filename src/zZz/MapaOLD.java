/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zZz;
import Main.Myrran;
import Geo.Terreno;
import DATA.MiscData;
import Geo.Estatico;
import Geo.Terreno;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author Hanto
 */
public class MapaOLD 
{
    //Identificador del Mapa
    private int ID;
    //Nombre del Mapa
    private String Nombre;
    
    //Estructura Mapa:
    //Array getTemplate: getTemplate base del mapa, cada terreno contiene el ID del tipo de Terreno que usara. Todos los tipos de Terreno estan almacenados en Myrran.mundo.Listadeterrenos
    //Array Mapeado: Contiene la estructura completa de una terreno, y almacena cualquier cambio que queramos otorgar a esa terreno. Cualquier diferencia respecto al template, sera almacenado ahi, y siempre tendra preferencia sobre los datos del getTemplate.
    //Array getMapeadoM: Array de Booleanos que nos marca como TRUE que campos hemos modificado y por tanto deben tener siempre prioridad sobre los del template.
    //IMPORTANTE: por tanto para añadir valores en la estructura Mapeado debemos usar siempre los metodos setMAP..., puestos que estos modifican tambien la mascara getMapeadoM, indicando como TRUE que campo hemos modificado
 
    private int [][] Template = new int [Myrran.getMundo().getMapaMaxY()][Myrran.getMundo().getMapaMaxX()];
    private Terreno [][] Mapeado = new Terreno [Myrran.getMundo().getMapaMaxY()][Myrran.getMundo().getMapaMaxX()];
    private TerrenoMascara [][] MapeadoM = new TerrenoMascara [Myrran.getMundo().getMapaMaxY()][Myrran.getMundo().getMapaMaxX()];
    
    private Estatico [][] CapaEstatica1 = new Estatico [Myrran.getMundo().getMapaMaxY()][Myrran.getMundo().getMapaMaxX()];
    
    public int [][] getTemplate ()                  { return Template; }
    public Estatico [][] getCapaEstatica1 ()        { return CapaEstatica1; }
    public TerrenoMascara [][] getMapeadoM ()       { return MapeadoM;}
    
    public int getID ()                             { return ID; }
    public String getNombre ()                      { return Nombre; }
    public void setID (int id)                      { ID = id; }
    public void setNombre (String nombre)           { Nombre = nombre; }
   
    
    //Constructor que inicializa el Array del mapaeado
    public MapaOLD ()
    {   
        //if (Myrran.getMundo().ListaMapas().isEmpty()) ID = 0;
        //else {ID = Myrran.getMundo().ListaMapas().get(Myrran.getMundo().ListaMapas().size()-1).getID()+1;}
        
        Nombre = "Mapa";
        
        for (int i=0;i<Template.length;i++) 
        { 
            for (int j=0;j<Template[i].length;j++) { Template[i][j] = -1; } 
        }
        
        for (int i=0;i<Mapeado.length;i++)
        {
            for (int j=0;j<Mapeado[i].length;j++)  { Terreno terreno = new Terreno(); Mapeado[i][j] = terreno; }
        }
        
        for (int i=0;i<MapeadoM.length;i++)
        {
            for (int j=0;j<MapeadoM[i].length;j++)  
            { 
                TerrenoMascara terrenom = new TerrenoMascara();
                terrenom.setID(Boolean.FALSE);
                terrenom.setNombre(Boolean.FALSE);
                terrenom.setImagen(Boolean.FALSE);
                terrenom.setColorTerreno(Boolean.FALSE);
                terrenom.setFlagMuro(Boolean.FALSE);
                MapeadoM[i][j] = terrenom; 
            }
        }
        
        for (int i=0;i<CapaEstatica1.length;i++)
        {
            for (int j=0;j<CapaEstatica1[i].length;j++) { CapaEstatica1[i][j] = new Estatico(); }
        }
    }
    
    
    public void setTemplate (int x, int y, int id)
    {
        Template[x][y]= id;
    }
    public void setMapID (int x, int y, int id)
    {
        Mapeado[x][y].setID(id);
        MapeadoM[x][y].setID(Boolean.TRUE);
    }
    public void setMapIDReset (int x, int y)
    {
        Mapeado[x][y].setID(-1);
        MapeadoM[x][y].setID(Boolean.FALSE);
    }
    public void setMapNombre (int x, int y, String nombre)
    {
        Mapeado[x][y].setNombre(nombre);
        MapeadoM[x][y].setNombre(Boolean.TRUE);
    }
    public void setMapNombreReset (int x, int y)
    {
        Mapeado[x][y].setNombre("");
        MapeadoM[x][y].setNombre(Boolean.FALSE);
    }
    public void setMapImagen (int x, int y, String imagen)
    {
        Mapeado[x][y].setCaracter(imagen.substring(0, 1));
        MapeadoM[x][y].setImagen(Boolean.TRUE);
    }
    public void setMapImagenReset (int x, int y)
    {
        Mapeado[x][y].setCaracter(".");
        MapeadoM[x][y].setImagen(Boolean.FALSE);
    }
    public void setMapBitmap (int x, int y, Image bitmap)
    {
        Mapeado[x][y].setIMG(bitmap);
        MapeadoM[x][y].setBitmap(Boolean.TRUE);
    }
    public void setMapBitmapReset (int x, int y)
    {
        Mapeado[x][y].setIMG(MiscData.TERRENO_DIRECCIONBITMAP_NEW);
        MapeadoM[x][y].setImagen(Boolean.FALSE);
    }
    public void setMapDireccionBitmap (int x, int y, String string)
    {
        Mapeado[x][y].setIMGFile(string);
        MapeadoM[x][y].setDireccionBitmap(Boolean.TRUE);
    }
    public void setMapDireccionBitmapReset (int x, int y)
    {
        Mapeado[x][y].setIMGFile(MiscData.TERRENO_DIRECCIONBITMAP_NEW);
        MapeadoM[x][y].setDireccionBitmap(Boolean.FALSE);
    }
    public void setMapColor (int x, int y, Color color)
    {
        Mapeado[x][y].setColorTerreno(color);
        MapeadoM[x][y].setColorTerreno(Boolean.TRUE);
    }
    public void setMapColorReset (int x, int y)
    {
        Mapeado[x][y].setColorTerreno(Color.LIGHT_GRAY);
        MapeadoM[x][y].setColorTerreno(Boolean.FALSE);
    }
    public void setMapFlagMuro (int x, int y, Color color)
    {
        Mapeado[x][y].setFlagMuro(Boolean.TRUE);
        MapeadoM[x][y].setFlagMuro(Boolean.TRUE);
    }
    public void setMapFlagMuroReset (int x, int y)
    {
        Mapeado[x][y].setFlagMuro(Boolean.FALSE);
        MapeadoM[x][y].setFlagMuro(Boolean.FALSE);
    }
    //Resetea todos los flags Mascara
    public void setMapMReset (int x, int y)
    {
        MapeadoM[x][y].setID(Boolean.FALSE);
        MapeadoM[x][y].setNombre(Boolean.FALSE);
        MapeadoM[x][y].setImagen(Boolean.FALSE);
        MapeadoM[x][y].setBitmap(Boolean.FALSE);
        MapeadoM[x][y].setDireccionBitmap(Boolean.FALSE);
        MapeadoM[x][y].setColorTerreno(Boolean.FALSE);
        MapeadoM[x][y].setFlagMuro(Boolean.FALSE);
        MapeadoM[x][y].setFlagPuerta(Boolean.FALSE);
        MapeadoM[x][y].setFlagLlave(Boolean.FALSE);   
    }
    
    public int getMapID (int x, int y)
    {   
        try {
            if (MapeadoM[x][y].getID()) return Mapeado[x][y].getID();
            else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getID();
        } catch (Exception e) {return -1;}
    }
    public String getMapNombre (int x, int y)
    {
        try {
        if (MapeadoM[x][y].getNombre()) return Mapeado[x][y].getNombre();
        else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getNombre();
        } catch (Exception e) {return "";}
    }
    public String getMapImagen (int x, int y)
    {   
        try {
        if (MapeadoM[x][y].getImagen()) return Mapeado[x][y].getCaracter();
        else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getCaracter();
        } catch (Exception e) {return ".";}
    }
    public Image getMapBitmap (int x, int y)
    {   
        try {
        if (MapeadoM[x][y].getBitmap()) return Mapeado[x][y].getIMG();
        else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getIMG();
        } catch (Exception e) {return null;}
    }
    public String getMapDireccionBitmap (int x, int y)
    {   
        try {
        if (MapeadoM[x][y].getDireccionBitmap()) return Mapeado[x][y].getIMGFile();
        else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getIMGFile();
        } catch (Exception e) {return MiscData.TERRENO_DIRECCIONBITMAP_NEW;}
    }
    public Color getMapColor (int x, int y)
    {
        try {
        if (MapeadoM[x][y].getColorTerreno()) return Mapeado[x][y].getColorTerreno();
        else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getColorTerreno();
        } catch (Exception e) {return Color.LIGHT_GRAY;}
    }
    public Boolean getMapFlagMuro (int x, int y)
    {   try {
        if (MapeadoM[x][y].getFlagMuro()) return Mapeado[x][y].getFlagMuro();
        else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getFlagMuro();
        } catch (Exception e) {return Boolean.FALSE;}
    }
    public Boolean getMapFlagPuerta (int x, int y)
    {   try {
        if (MapeadoM[x][y].getFlagPuerta()) return Mapeado[x][y].getFlagPuerta();
        else return Myrran.getMundo().ListaTerrenos().get(Template[x][y]).getFlagPuerta();
        } catch (Exception e) {return Boolean.FALSE;}
    }       
    
    
    public int getMapeadoID (int x, int y)                  { return Mapeado[x][y].getID(); }
    public String getMapeadoNombre (int x, int y)           { return Mapeado[x][y].getNombre(); }
    public String getMapeadoImagen (int x, int y)           { return Mapeado[x][y].getCaracter(); }
    public Image getMapeadoBitmap (int x, int y)            { return Mapeado[x][y].getIMG(); }
    public String getMapeadoDireccionBitmap (int x, int y)  { return Mapeado[x][y].getIMGFile();}
    public Color getMapeadoColor (int x, int y)             { return Mapeado[x][y].getColorTerreno(); }
    public Boolean getMapeadoFlagMuro (int x, int y)        { return Mapeado[x][y].getFlagMuro(); }    
    public int getMapeadoLenghti ()                         { return Mapeado.length;}
    public int getMapeadoLenghtj ()                         { return Mapeado[0].length;}
    
    
    //Busca si un ID Terreno esta en uso en el mapa
    public Boolean BuscarTerrenoEnMapa (int id)
    {
        for (int i=0;i<Template.length;i++) 
        { 
            for (int j=0;j<Template[i].length;j++) { if (Template[i][j] == id) {return Boolean.TRUE;} } 
        }
        return Boolean.FALSE;
    }        
    
    
    
    //Devuelve verdadero si la posicion y,x esta flageada como Muro
    public Boolean isMurallable (int x, int y)
    {
        return (getMapFlagMuro(x,y) || getMapFlagPuerta (x,y));
    }  
     
    //Une los muros adyacentes
    public String Enmurallar (int y, int x)
    {   
         //═
        if ( isMurallable (y,x+1) &&
            !isMurallable (y,x-1) &&
            !isMurallable (y+1,x) &&
            !isMurallable (y-1,x))
            {   if (getMapFlagPuerta(y,x)) {return("─");}
                if (getMapFlagMuro (y,x)) {return("═");}
            }
        if (!isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
            !isMurallable (y+1,x) &&
            !isMurallable (y-1,x))
            {   if (getMapFlagPuerta(y,x)) {return("─");}
                if (getMapFlagMuro (y,x)) {return("═");}
            }
        if ( isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
            !isMurallable (y+1,x) &&
            !isMurallable (y-1,x))
            {   if (getMapFlagPuerta(y,x)) {return("─");}
                if (getMapFlagMuro (y,x)) {return("═");}
            }
        //║
        if (!isMurallable (y,x+1) &&
            !isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {   if (getMapFlagPuerta(y,x)) {return("│");}
                if (getMapFlagMuro (y,x)) {return("║");}
            }
        if (!isMurallable (y,x+1) &&
            !isMurallable (y,x-1) &&
            !isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {   if (getMapFlagPuerta(y,x)) {return("│");}
                if (getMapFlagMuro (y,x)) {return("║");}
            }
        if (!isMurallable (y,x+1) &&
            !isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
            !isMurallable (y-1,x))
            {   if (getMapFlagPuerta(y,x)) {return("│");}
                if (getMapFlagMuro (y,x)) {return("║");}
            }
        //╚
        if ( isMurallable (y,x+1) &&
            !isMurallable (y,x-1) &&
            !isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╚");}}
        //╝
        if (!isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
            !isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╝");}}
        //╗
        if (!isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
            !isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╗");}}
        //╔
        if ( isMurallable (y,x+1) &&
            !isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
            !isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╔");}}
        //╠
        if ( isMurallable (y,x+1) &&
            !isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╠");}}
        //╣
        if (!isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╣");}}
        //╦
        if ( isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
            !isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╦");}}
        //╩
        if ( isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
            !isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╩");}}
        //╬
        if ( isMurallable (y,x+1) &&
             isMurallable (y,x-1) &&
             isMurallable (y+1,x) &&
             isMurallable (y-1,x))
            {if (getMapFlagMuro (y,x)) {return("╬");}}
        return "═";
    } 

}
