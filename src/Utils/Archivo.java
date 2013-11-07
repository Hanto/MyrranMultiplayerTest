/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



/**
 * Clase para Bajar archivos a la carpeta DOWNLOADS localizada en la raiz de donde este el programa
 * @author Hanto
 */

public class Archivo 
{
    private String direccionURL;
    private String nombre_fichero;
 
    public static final int DL_CORRECTO = 0;
    public static final int DL_URLINCORRECTA = 1;
    public static final int DL_FALLOCONEXION = 2;
    public static final int DL_FALLOFICHERO = 3;
    public static final int DL_INTERRUMPIDO = 4;
    
    public void setDireccion(String direccion_web)          { this.direccionURL = direccion_web;}
    public String getDireccion()                            { return this.direccionURL; }
    public void setNombreFichero(String nombre_fichero)     { this.nombre_fichero = nombre_fichero; }  
    public String getNombreFichero()                        { return this.nombre_fichero; } 
    
    public String Copiar (File ficheroOrigen, String nombreFicheroDest)
    {
        String folder = "Images/";
        // crea el directorio de destino en caso de que no exista
        File dir = new File(folder);
        
        if (!dir.exists())
            if (!dir.mkdir())
                return "Error";   
        try 
        {
            // Crea el archivo destino
            String sfichero = nombreFicheroDest+"."+getFileExtension(ficheroOrigen);
            File sficheroDest = new File(folder + sfichero);
        
            InputStream in = new FileInputStream(ficheroOrigen);
            OutputStream out = new FileOutputStream(sficheroDest);
                      
            
            byte[] buffer = new byte [1024];
            int length;
        
            while ((length = in.read(buffer)) > 0)  
                { out.write(buffer,0,length); }
            
            in.close();
            out.close();
            System.out.println(sfichero+" Copiado");
            return sfichero;
            
        } catch (IOException e) {return "Error";}
    }
    
    //Metodo que ejecuta la orden de descarga del fichero contenido en el campo direccionURL al fichero nombre_fichero
    public int Descargar(){
                // datos basicos para una descarga
                String folder = "Images/";
               
                // crea el directorio de destino en caso de que no exista
                File dir = new File(folder);

                if (!dir.exists())
                    if (!dir.mkdir())
                        return DL_FALLOFICHERO; // no se pudo crear la carpeta de destino

                // Crea el archivo destino
                File file = new File(folder + this.getNombreFichero());

                try 
                {
                        // establece la conexion con la url
                        URLConnection conn = new URL(this.getDireccion()).openConnection();
                        conn.connect();

                        System.out.println("---Nombre: " + this.getNombreFichero());
                        System.out.println("   URL: " + this.getDireccion());
                        //System.out.println("   Tamaño: " + conn.getContentLength()+ " bytes");

                        // Abre los streams
                        InputStream in = conn.getInputStream();
                        OutputStream out = new FileOutputStream(file);

                        int b = 0;

                        // este ciclo lee un byte por vez y los escribe en un archivo
                        // el -1 significa que se llego al final
                        try  // Por si pierde la conexion mientras esta leyendo el fichero
                        {
                            while (b != -1) 
                            {
                                b = in.read();
                                if (b != -1)
                                    out.write(b);
                            }
                        } 
                        catch (Exception e) 
                        {
                            out.close();
                            in.close();
                            
                            System.out.println(" + Conexion Interrumpida: "+e);
                            return DL_INTERRUMPIDO;
                        }

                        // Cierra los streams
                        out.close();
                        in.close();

                        System.out.println(" * Descarga Finalizada");
                        return DL_CORRECTO;
                        
                //ERROR: URL Incorrecta
                } catch (MalformedURLException e) 
                {
                    System.out.println("---Nombre: " + this.getNombreFichero());   
                    System.out.println(" + URL no valida: " + this.getDireccion());
                    return DL_URLINCORRECTA;
                    
                //ERROR: Perdida de conexion
                } catch (IOException e) 
                {
                    System.out.println("---Nombre: " + this.getNombreFichero()); 
                    System.out.println(" + Conexion Fallida: "+e);
                    return DL_FALLOCONEXION;
                }
        }
        
    //Metodo que vuelca el contenido de un fichero a un String
    public static String LeerArchivo(String nombre)
    {
	 
        try
        {
            File f;
            FileReader lectorArchivo;

            f = new File(nombre);
            //Comprobamos que el archivo exista, si no existe cancelamos su lectura.
            if (f.exists() == false) { System.out.println("Archivo: "+nombre+" no encontrado.");return ""; }
            //Creamos el objeto FileReader que abrira el flujo(Stream) de datos para realizar la lectura
            lectorArchivo = new FileReader(f);
            //Creamos un lector en buffer para recopilar datos a travez del flujo "lectorArchivo" que hemos creado
            BufferedReader br = new BufferedReader(lectorArchivo);
            String l="";
            //Esta variable "l" la utilizamos para guardar mas adelante toda la lectura del archivo
            String aux="";/*variable auxiliar*/
	 
            while(true)
            //este ciclo while se usa para repetir el proceso de lectura, ya que se lee solo 1 linea de texto a la vez
            {
                aux = br.readLine();
                //leemos una linea de texto y la guardamos en la variable auxiliar
                if(aux!=null)
                l=l+aux+"\n";
                /*si la variable aux tiene datos se va acumulando en la variable l,
                   * en caso de ser nula quiere decir que ya nos hemos leido todo
                   * el archivo de texto*/
                else
                break;
            }
	 
            br.close();
            lectorArchivo.close();
            return l;
        }
        catch(IOException e) { System.out.println("Error:"+e.getMessage()); }
	return null;
    }
    
    public static String getFileExtension(File file) 
    {
        if (file == null) {return null;}

        String name = file.getName();
        int extIndex = name.lastIndexOf(".");

        if (extIndex == -1) {return "";} 
        else {return name.substring(extIndex + 1);} 
    }
        
}
