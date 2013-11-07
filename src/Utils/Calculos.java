/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Hanto
 */
public class Calculos 
{
    //Distancia(en Tiles) entre dos puntos
    public static double Distancia (int OrigenX, int Origeny, int DestinoX, int DestinoY)
    {
        return Math.sqrt(Math.pow(DestinoX-OrigenX,2)+Math.pow(DestinoY-Origeny, 2));
    }
    public static double Distancia (double OrigenX, double Origeny, int DestinoX, int DestinoY)
    {
        return Math.sqrt(Math.pow(DestinoX-OrigenX,2)+Math.pow(DestinoY-Origeny, 2));
    }
    public static double Distancia (double OrigenX, double Origeny, double DestinoX, double DestinoY)
    {
        return Math.sqrt(Math.pow(DestinoX-OrigenX,2)+Math.pow(DestinoY-Origeny, 2));
    }
    
   
    
}
