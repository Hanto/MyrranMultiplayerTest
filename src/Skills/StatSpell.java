/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Skills;

/**
 *
 * @author Hanto
 */
public class StatSpell 
{
    public String Nombre;
    public double ValorBase;
    public Boolean isActivo;
    
    public int TalentoActual;
    public int TalentoMaximo;
    public int CosteTalento;
    public double BonoTalento;
    
    //public double ValorTotal = ValorBase + TalentoActual*BonoTalento;

    public void setValorBase(String nombre, double cantidad)                { Nombre = nombre; ValorBase = cantidad; isActivo = true; }
    public void setValorBase(double cantidad)                               { ValorBase = cantidad; isActivo = true; }
    public void setTalentoActual (int i)                                    { TalentoActual = i; }
    public void setTalento (int TActual, int TMaximo, int Coste, double Bono)  { TalentoActual = TActual; TalentoMaximo = TMaximo; CosteTalento = Coste; BonoTalento = Bono;}
    public void setTalento (int TActual, int TMaximo)                       { TalentoActual = TActual; TalentoMaximo = TMaximo; }
    
    public double getValorTotal ()                                          { return (ValorBase + TalentoActual * BonoTalento); }
    
    
    
    public StatSpell ()
    {
        isActivo=false;
    }
    
}

