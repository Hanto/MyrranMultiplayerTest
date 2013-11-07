/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BDD;

import Main.Myrran;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hanto
 */
public class DerbySalvar implements Runnable
{   
    private Connection con = null;
    
    @Override
    public void run ()
    {
        SalvarTodo ();
    }
    
    public void SalvarTodo ()
    {
        BorrarTabla("Estaticos");
        CrearTabla_Estaticos();
        SalvarTabla_Estaticos();
        
        BorrarTabla("Terrenos");
        CrearTabla_Terrenos();
        SalvarTabla_Terrenos();
        
        BorrarTabla("Mapas");
        CrearTabla_Mapas();
        SalvarTabla_Mapas();
        
        BorrarTabla("MapTemplates");
        CrearTabla_MapTemplates ();
        SalvarTabla_MapTemplates ();
    }        
        
    public void SalvarTabla_Estaticos ()
    {   
        Conectar ();
        try
        {
            for (int i=0;i<Myrran.getMundo().ListaEstaticos().size();i++)
            {
                String stringSQL = "INSERT INTO Estaticos (id, Nombre, DireccionBitmap, isSolido) VALUES (?,?,?,?)";
                PreparedStatement SentenciaSQL = con.prepareStatement(stringSQL);
                SentenciaSQL.setInt(1, Myrran.getMundo().ListaEstaticos().get(i).getID());
                SentenciaSQL.setString(2, Myrran.getMundo().ListaEstaticos().get(i).getNombre());
                SentenciaSQL.setString(3, Myrran.getMundo().ListaEstaticos().get(i).getIMGFilename());
                SentenciaSQL.setBoolean(4, Myrran.getMundo().ListaEstaticos().get(i).isSolido());
                SentenciaSQL.executeUpdate();
            }    
        }    
        catch (Exception e) { System.out.println(" + ERROR: salvando la tabla [Estaticos]: "+e.getMessage()); }          
        Desconectar();        
    }        
    
    public void SalvarTabla_Terrenos ()
    {   
        Conectar ();
        try
        {
            for (int i=0;i<Myrran.getMundo().ListaTerrenos().size();i++)
            {
                String stringSQL = "INSERT INTO Terrenos (id, Nombre, Imagen, DireccionBitmap, Color, FlagMuro, FlagPuerta) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement SentenciaSQL = con.prepareStatement(stringSQL);
                SentenciaSQL.setInt(1, Myrran.getMundo().ListaTerrenos().get(i).getID());
                SentenciaSQL.setString(2, Myrran.getMundo().ListaTerrenos().get(i).getNombre());
                SentenciaSQL.setString(3, Myrran.getMundo().ListaTerrenos().get(i).getCaracter());
                SentenciaSQL.setString(4, Myrran.getMundo().ListaTerrenos().get(i).getIMGFile());
                SentenciaSQL.setInt(5, Myrran.getMundo().ListaTerrenos().get(i).getColorTerreno().getRGB());
                SentenciaSQL.setBoolean(6, Myrran.getMundo().ListaTerrenos().get(i).getFlagMuro());
                SentenciaSQL.setBoolean(7, Myrran.getMundo().ListaTerrenos().get(i).getFlagPuerta());
                SentenciaSQL.executeUpdate();
            }    
        }    
        catch (Exception e) { System.out.println(" + ERROR: salvando la tabla [Terrenos]: "+e.getMessage()); }          
        Desconectar();        
    }        
    
    public void SalvarTabla_Mapas ()
    {   
        Conectar ();
        try
        {
            for (int i=0;i<Myrran.getMundo().ListaMapas().size();i++)
            {
                String stringSQL = "INSERT INTO Mapas (id, Nombre) VALUES (?,?)";
                PreparedStatement SentenciaSQL = con.prepareStatement(stringSQL);
                SentenciaSQL.setInt(1, Myrran.getMundo().ListaMapas().get(i).getID());
                SentenciaSQL.setString(2, Myrran.getMundo().ListaMapas().get(i).getNombre());
                SentenciaSQL.executeUpdate();
            }    
        }    
        catch (Exception e) { System.out.println(" + ERROR: salvando la tabla [Mapas]: "+e.getMessage()); }          
        Desconectar();        
    }  
    
    public void SalvarTabla_MapTemplates ()
    {
        Conectar ();
        try
        {
            String SSQL1 ="", SSQL2="";
            for (int i=0; i<Myrran.getMundo().getMapaMaxX();i++)
            {
                SSQL1 = SSQL1 + "R"+i+",";
                SSQL2 = SSQL2 + "?,";
            }
            SSQL1 = SSQL1.substring(0, SSQL1.length()-1);
            SSQL2 = SSQL2.substring(0, SSQL2.length()-1);
            
            for (int numMapa=0;numMapa<Myrran.getMundo().ListaMapas().size();numMapa++)
            {
                for (int fila=0;fila<Myrran.getMundo().getMapaMaxY();fila++)
                {
                    String stringSQL = "INSERT INTO MapTemplates (Mapid, Fila,"+SSQL1+") VALUES (?,?,"+SSQL2+")";
                    
                    PreparedStatement SentenciaSQL = con.prepareStatement(stringSQL);
                    SentenciaSQL.setInt(1, Myrran.getMundo().ListaMapas().get(numMapa).getID());
                    SentenciaSQL.setInt(2, fila);
                    
                    for (int columna=3;columna<Myrran.getMundo().getMapaMaxX()+3;columna++)
                        { SentenciaSQL.setInt(columna, Myrran.getMundo().ListaMapas().get(numMapa).map()[fila][columna-3].getTerrenoBase()); }
                    SentenciaSQL.executeUpdate(); 
                }    
            }    
        }    
        catch (Exception e) { System.out.println(" + ERROR: salvando la tabla [MapaTemplates]: "+e.getMessage()); }          
        Desconectar();          
    }        
    
    
    public void CrearTabla_MapTemplates ()
    {
        Conectar();
        try
        {
            String stringSQL = "CREATE TABLE MapTemplates ("
                    + "MapID INTEGER,"
                    + "Fila INTEGER,";
            for (int i=0; i<Myrran.getMundo().getMapaMaxX();i++)
                stringSQL = stringSQL + "R"+i+" INTEGER,";
            
            stringSQL = stringSQL + "PRIMARY KEY (MapID, Fila))";
            
            Statement SentenciaSQL = con.createStatement();
            SentenciaSQL.execute(stringSQL);
            System.out.println("   TABLA [MapTemplates] creada con exito");     
        }
        catch (Exception e) { System.out.println(" + ERROR: creando la tabla [MapTemplates]: "+e.getMessage()); }
        Desconectar();
    }        
    
    
    public void CrearTabla_Mapas ()
    {
        Conectar ();
        try 
        {
            Statement SentenciaSQL = con.createStatement();
            String stringSQL = "CREATE TABLE Mapas ("
                    + "id INTEGER PRIMARY KEY,"
                    + "Nombre VARCHAR(50))";
            SentenciaSQL.execute(stringSQL);
            System.out.println("   TABLA [Mapas] creada con exito");
        }
        catch (Exception e) { System.out.println(" + ERROR: creando la tabla [Mapas]: "+e.getMessage()); }
        Desconectar();
    }
    
    public void CrearTabla_Terrenos ()
    {
        Conectar ();
        try 
        {
            Statement SentenciaSQL = con.createStatement();
            String stringSQL = "CREATE TABLE Terrenos ("
                    + "id INTEGER PRIMARY KEY,"
                    + "Nombre VARCHAR(50),"
                    + "Imagen VARCHAR(1),"
                    + "DireccionBitmap VARCHAR(50),"
                    + "Color INTEGER,"
                    + "FlagMuro BOOLEAN,"
                    + "FlagPuerta Boolean)";
            SentenciaSQL.execute(stringSQL);
            System.out.println("   TABLA [Terrenos] creada con exito");
        }
        catch (Exception e) { System.out.println(" + ERROR: creando la tabla [Terrenos]: "+e.getMessage()); }
        Desconectar();
    }        
    
    public void CrearTabla_Estaticos ()
    {
        Conectar ();
        try 
        {
            Statement SentenciaSQL = con.createStatement();
            String stringSQL = "CREATE TABLE Estaticos ("
                    + "id INTEGER PRIMARY KEY,"
                    + "Nombre VARCHAR(50),"
                    + "DireccionBitmap VARCHAR(50),"
                    + "isSolido BOOLEAN)";
            SentenciaSQL.execute(stringSQL);
            System.out.println("   TABLA [Terrenos] creada con exito");
        }
        catch (Exception e) { System.out.println(" + ERROR: creando la tabla [Terrenos]: "+e.getMessage()); }
        Desconectar();
    }
    
    public void BorrarTabla (String nombretabla)
    {
        Conectar();
        String stringSQL;
        try
        {
            stringSQL = "DROP TABLE "+nombretabla;
            Statement SentenciaSQL = con.createStatement();
            SentenciaSQL.execute(stringSQL);
        }
        catch (Exception e) { System.out.println(" + ERROR: borrando la tabla ["+nombretabla+"]: "+e.getMessage()); }   
        Desconectar();
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
