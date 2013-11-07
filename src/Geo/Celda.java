/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Geo;

import Main.Myrran;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Hanto
 */
public class Celda 
{ 
    private int terrenoBase;
    private Terreno terrenoModificado;
    private int Estatico1;  
    
    private Boolean tieneTerrenoBase;
    private Boolean tieneTerrenoModificado;
    private Boolean tieneEstatico1;
    
    public int getTerrenoBase()                         { return terrenoBase; }
    public Terreno getTerrenoModificado()               { return terrenoModificado; }
    public int getEstatico1()                           { return Estatico1; }
    
    public void setTieneTerrenoBase(Boolean b)          { tieneTerrenoBase = b; }
    public void setTieneTerrenoModificado(Boolean b)    { tieneTerrenoModificado = b; }
    public void setTieneEstatico1(Boolean b)            { tieneEstatico1 = b; }
    
    public void setTerrenoBase (int i)                  { terrenoBase = i; tieneTerrenoBase=true;}
    public void setEstatico1 (int e)                    { Estatico1 = e; tieneEstatico1=true;}
    
    public Boolean tieneTerrenoBase()                   { return tieneTerrenoBase; }
    public Boolean tieneTerrenoModificado()             { return tieneTerrenoModificado; }
    public Boolean tieneEstatico1()                     { return tieneEstatico1; }
    
    public Celda ()
    {
        terrenoBase = -1;
        terrenoModificado = new Terreno();
        Estatico1 = -1;
        
        tieneTerrenoBase = false;
        tieneTerrenoModificado = false;
        tieneEstatico1 = false;
    }
    
    public Boolean isSolido()
    {   
        if (tieneEstatico1) if (Myrran.getMundo().ListaEstaticos().get(Estatico1).isSolido()) return true;
        if (tieneTerrenoModificado) return terrenoModificado.getFlagMuro();
        else { if (tieneTerrenoBase) return Myrran.getMundo().ListaTerrenos().get(terrenoBase).getFlagMuro();}   
        return false;
    }
    
    public Boolean isPuerta()
    {
        if (tieneTerrenoModificado) return terrenoModificado.getFlagPuerta();
        else { if (tieneTerrenoBase) return Myrran.getMundo().ListaTerrenos().get(terrenoBase).getFlagPuerta();}
        return false;
    }
    
    public Color getColor()
    {
        if (tieneTerrenoModificado) return terrenoModificado.getColorTerreno();
        else { if (tieneTerrenoBase) return Myrran.getMundo().ListaTerrenos().get(terrenoBase).getColorTerreno();}
        return Color.BLACK;
    }
    
    public Image getIMG()
    {
        if (tieneTerrenoModificado) return terrenoModificado.getIMG();
        else { if (tieneTerrenoBase) return Myrran.getMundo().ListaTerrenos().get(terrenoBase).getIMG();}
        return null;
    }
    
    public ArrayList <Integer> getLlaves()
    {
        if (tieneTerrenoModificado) return terrenoModificado.getLlaves();
        else { if (tieneTerrenoBase) return Myrran.getMundo().ListaTerrenos().get(terrenoBase).getLlaves();}
        return null;
    }
    
    public String getNombre()
    {
        if (tieneTerrenoModificado) return terrenoModificado.getNombre();
        else { if (tieneTerrenoBase) return Myrran.getMundo().ListaTerrenos().get(terrenoBase).getNombre();}
        return null;
    }
    
    public String getCaracter()
    {
        if (tieneTerrenoModificado) return terrenoModificado.getCaracter();
        else { if (tieneTerrenoBase) return Myrran.getMundo().ListaTerrenos().get(terrenoBase).getCaracter();}
        return null;
    }
            
}
