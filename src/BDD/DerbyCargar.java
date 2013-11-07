/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BDD;

import Geo.Estatico;
import zZz.MapaOLD;
import Geo.Mapa;
import Main.Myrran;
import Geo.Terreno;
import WorldBuilder.WorldEditor;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Hanto
 */
public class DerbyCargar implements Runnable
{
    private Connection con = null;
    
    @Override
    public void run ()
    {
        CargarTodo ();
    }
    
    public void CargarTodo ()
    {
        CargarTabla_Estaticos();
        CargarTabla_Terrenos();
        CargarTabla_Mapas();
        CargarTabla_MapTemplates();
    }
    
    public void CargarTabla_MapTemplates ()
    {
        Conectar ();
        try
        {
            String sSQL = "SELECT * FROM MapTemplates";
            PreparedStatement sentenciaSQL = con.prepareStatement(sSQL);
            ResultSet Rtemplate = sentenciaSQL.executeQuery();
            while (Rtemplate.next())
            {
                int numMapa, numFila;
                numMapa = Rtemplate.getInt("MapID");
                numFila = Rtemplate.getInt("Fila");
                
                for (int numColumna=0;numColumna<Myrran.getMundo().getMapaMaxX();numColumna++)
                {
                    int terrenoID = Rtemplate.getInt("R"+numColumna);
                    Myrran.getMundo().ListaMapas().get(numMapa).map()[numFila][numColumna].setTerrenoBase(terrenoID);
                }    
            }   
        }
        catch (Exception e) { System.out.println("Error Cargando Tablas de MapTemplates: "+e.getMessage()); }
        Desconectar ();
    }
    
    public void CargarTabla_Mapas()
    {
        Conectar ();
        try
        {
            String sSQL = "SELECT * FROM Mapas";
            PreparedStatement sentenciaSQL = con.prepareStatement(sSQL);
            ResultSet RTerreno = sentenciaSQL.executeQuery();
            while (RTerreno.next())
            {
                Mapa map = new Mapa();
                map.setID(RTerreno.getInt("ID"));
                map.setNombre(RTerreno.getString("Nombre"));
                Myrran.getMundo().ListaMapas().add(map);
            }   
        }
        catch (Exception e) { System.out.println("Error Cargando Tablas de Mapas: "+e.getMessage()); }
        Desconectar ();
    } 
    
    public void CargarTabla_Terrenos()
    {
        Conectar ();
        try
        {
            String sSQL = "SELECT * FROM Terrenos";
            PreparedStatement sentenciaSQL = con.prepareStatement(sSQL);
            ResultSet RTerreno = sentenciaSQL.executeQuery();
            while (RTerreno.next())
            {
                Terreno terreno = new Terreno ();
                terreno.setID(RTerreno.getInt("ID"));
                terreno.setNombre(RTerreno.getString("Nombre"));
                terreno.setCaracter(RTerreno.getString("Imagen"));
                terreno.setIMGFile(RTerreno.getString("DireccionBitmap"));
                terreno.setIMG(terreno.getIMGFile());
                int rgb = RTerreno.getInt("Color");
                terreno.setColorTerreno(new Color(rgb));
                terreno.setFlagMuro(RTerreno.getBoolean("FlagMuro"));
                terreno.setFlagPuerta(RTerreno.getBoolean("FlagPuerta"));
                Myrran.getMundo().ListaTerrenos().add(terreno);
            }   
        }
        catch (Exception e) { System.out.println("Error Cargando Tablas de Terrenos: "+e.getMessage()); }
        Desconectar ();
    }        
   
    public void CargarTabla_Estaticos()
    {
        Conectar ();
        try
        {
            String sSQL = "SELECT * FROM Estaticos";
            PreparedStatement sentenciaSQL = con.prepareStatement(sSQL);
            ResultSet REstatico = sentenciaSQL.executeQuery();
            while (REstatico.next())
            {
                Estatico estatico = new Estatico ();
                estatico.setID(REstatico.getInt("ID"));
                estatico.setNombre(REstatico.getString("Nombre"));
                estatico.setIMGFilename(REstatico.getString("DireccionBitmap"));
                estatico.setIMG(estatico.getIMGFilename());
                estatico.isSolido(REstatico.getBoolean("isSolido"));
                Myrran.getMundo().ListaEstaticos().add(estatico);
            }   
        }
        catch (Exception e) { System.out.println("Error Cargando Tablas de Estaticos: "+e.getMessage()); }
        Desconectar ();
    } 
    
    public boolean Conectar ()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            con = DriverManager.getConnection("jdbc:derby:APP;create=true;user=app;password=app");
            return true;  
        }
        catch (Exception e)
        {
            System.out.println(" + Error al conectar: "+e);
            return false;
        }   
    }
    
    public String Desconectar ()
    {
        try
        {
            con.close();
            return "Desconexion con la Base de datos Loterial del Motor SQL.";
        }
        catch (Exception e) { return "Problemas con la desconexion de la BDD"; }    
    }

}
