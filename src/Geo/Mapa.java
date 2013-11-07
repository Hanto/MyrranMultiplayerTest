/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geo;

import Main.Myrran;

/**
 *
 * @author Hanto
 */
public class Mapa 
{
    private int ID;
    private String Nombre;
    private Celda [][] map = new Celda [Myrran.getMundo().getMapaMaxX()][Myrran.getMundo().getMapaMaxY()];
    
    public void setID (int i)                   { ID = i; }
    public void setNombre (String s)            { Nombre = s; }
    public int getID()                          { return ID; }
    public String getNombre()                   { return Nombre; }
    
    public Celda [][] map()                     { return map; }
    
    
    
    
    
    public Mapa ()
    {   
        if (Myrran.getMundo().ListaMapas().isEmpty()) ID = 0;
        else {ID = Myrran.getMundo().ListaMapas().get(Myrran.getMundo().ListaMapas().size()-1).getID()+1;}
        
        Nombre = "Mapa_"+ID;
        
        for (int i=0;i<map.length;i++) { for (int j=0;j<map[i].length;j++) { map[i][j] = new Celda (); } }
    }
    
    public Boolean BuscarTerrenoEnMapa (int ID)
    {
        for (int i=0;i<map.length;i++) 
        { 
            for (int j=0;j<map[i].length;j++) 
                { if ( (map[i][j].getTerrenoBase() == ID && map[i][j].tieneTerrenoBase()) || 
                       (map[i][j].getTerrenoModificado().getID() == ID && map[i][j].tieneTerrenoModificado())) {return Boolean.TRUE;} } 
        }
        return Boolean.FALSE;
    }
    
    public Boolean BuscarEstaticoEnMapa (int ID)
    {
        for (int i=0;i<map.length;i++)
        {
            for (int j=0;j<map[i].length;j++)
            { if ( map[i][j].getEstatico1() == ID) return Boolean.TRUE; }
        }
        return Boolean.FALSE;
    }
            
    
}
