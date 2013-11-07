/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Hanto
 */
public class Coordenadas 
{
    public int X;
    public int Y;

    public void setXY(int X,int Y)  {this.X=X; this.Y=Y;}
    public void setX(int X)         {this.X = X;}
    public void setY(int Y)         {this.Y = Y;}
    public int getX()               {return X;}
    public int getY()               {return Y;}
    
    public Coordenadas(int X, int Y)
    {
        this.X=X;
        this.Y=Y;
    }
    public Coordenadas ()
    {
        
    }
}
