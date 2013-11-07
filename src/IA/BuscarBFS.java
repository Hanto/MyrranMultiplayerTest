package IA;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import Main.Myrran;
import DATA.MiscData;
import Graphics.Map3D;
import Utils.Coordenadas;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SwingUtilities;


public class BuscarBFS
{   
    private int OrigenX;
    private int OrigenY;
    
    private int DestinoX;
    private int DestinoY;
    
    private int ALCANCE=MiscData.BUSCADOR_ALCANCE;
    
    static class Estado
    {
        int x , y , d;								// Fila, columna y distancie del estado
	    
	public Estado( int x1, int y1 , int d1)
        {
            this.x = x1;
	    this.y = y1;
	    this.d = d1;
	}
    };
    
    public ArrayList<Coordenadas> EncontrarCamino( int OrigenX , int OrigenY , int DestinoX, int DestinoY)                                 
    {
        this.OrigenX=OrigenX;
        this.OrigenY=OrigenY;
        this.DestinoX=DestinoX;
        this.DestinoY=DestinoY;
        
        boolean visitado[][] = new boolean[ MiscData.MUNDO_MAPAMAXX ][ MiscData.MUNDO_MAPAMAXY ];                               //Matriz de casillas visitadas
        BuscarBFS.Estado [][] MapadeVuelta = new BuscarBFS.Estado [ MiscData.MUNDO_MAPAMAXX ][ MiscData.MUNDO_MAPAMAXY ];
        
        Queue<Estado> Q = new LinkedList<Estado>();                                     
	Q.add( new BuscarBFS.Estado( OrigenX , OrigenY , 0 ) );				
	    
	int dx[  ] = { 0 ,  0 , 1 , -1 };                                               //incremento en coordenada OrigenX
	int dy[  ] = { 1 , -1 , 0 ,  0 };                                               //incremento en coordenada OrigenY
	int nX , nY;
        
	while( !Q.isEmpty())
        {                                                                               //Mientras cola no este vacia
            BuscarBFS.Estado actual = Q.remove();
    
            if( actual.x == DestinoX && actual.y == DestinoY )  { return this.MostrarCamino(MapadeVuelta);}
            
            visitado [ actual.x ][ actual.y ] = true;                                    //Marco como visitado dicho estado para no volver a recorrerlo
            
            for (int i = 0 ; i < 4 ; ++i)
            {                                                                           
                nX = dx[ i ] + actual.x;                                                
                nY = dy[ i ] + actual.y;                                                
                                                                                        
                if  ((nX >= 0) && (nY >= 0) && (nX < MiscData.MUNDO_MAPAMAXX) && (nY < MiscData.MUNDO_MAPAMAXY) &&                  // Margenes del mapa
                     (nX >= (OrigenX-ALCANCE)) && (nX < (OrigenX+ALCANCE)) && (nY >= (OrigenY-ALCANCE)) && (nY < (OrigenY+ALCANCE)) &&  // Margenes de la pantalla (en Tiles)
                      !visitado [nX][nY] &&                                                                                             // Que no este visitado
                      !Myrran.getMundo().ListaMapas().get(0).map()[nX][nY].isSolido())                                                 // Que no sea muro
                {   
                    MapadeVuelta[nX][nY] = new BuscarBFS.Estado(actual.x,actual.y,actual.d);
                    Q.add( new BuscarBFS.Estado( nX , nY , actual.d + 1 ) );                                                               //agregamos estado adyacente aumento en 1 la distancia recorrida      
                }
            }
        }
        return null;
    }
    
    private ArrayList<Coordenadas> MostrarCamino (BuscarBFS.Estado [][] MapadeVuelta)
    {
        ArrayList <Coordenadas> CaminoaSeguirBFS = new ArrayList <Coordenadas>();
        
        int X,Y,Xaux,Yaux;
        X=DestinoX;
        Y=DestinoY;
        
        if (OrigenX==DestinoX && OrigenY == DestinoY || MapadeVuelta.length==0) return null;
        Coordenadas Coord = new Coordenadas ();
        Coord.setXY(X, Y);
        CaminoaSeguirBFS.add(Coord);
        //System.out.println("\nDistancia: "+MapadeVuelta[DestinoX][DestinoY].d+"\nX: "+X+" Y: "+Y);
        for (int Distancia=0;Distancia<MapadeVuelta[DestinoX][DestinoY].d;Distancia++)
        {   try
            {   
                Xaux=X;Yaux=Y;
                X=MapadeVuelta[Xaux][Yaux].x;
                Y=MapadeVuelta[Xaux][Yaux].y;
                //System.out.println("X: "+X+" Y: "+Y);
                Coord = new Coordenadas ();
                Coord.setXY(X, Y);
                CaminoaSeguirBFS.add(Coord);
                
            } catch (Exception E) {}
        }
        return CaminoaSeguirBFS;
    }        
    
}

