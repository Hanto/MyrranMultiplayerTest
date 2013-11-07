/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Skills;

import Main.Myrran;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Hanto
 */
public class AuraTemplate 
{
    private int ID;
    private String Nombre;
    private String Descripcion;
    private Image IMG;
    private int Tipo;
    private Boolean isDebuff;
    private Boolean isBuff;
        
    private StatSpell [] Stats;
    public StatSpell [] Stats()                     { return Stats; }
    
    public void setID (int id)                      { ID = id; }
    public void setNombre (String nombre)           { Nombre = nombre; }
    public void setDescripcion (String d)           { Descripcion = d; }
    public void setIMG (String Ifichero)            { LoadIMG(Ifichero);}
    public void setIMG (Image img)                  { IMG = img; }
    public void setIsDebuff (Boolean b)             { isDebuff = b; isBuff = !b; }
    public void setisBuff (Boolean b)               { isBuff = b ; isDebuff = !b; }
    
    public int getID()                              { return ID; }
    public String getNombre ()                      { return Nombre; }
    public String getDescripcion ()                 { return Descripcion; }
    public Image getIMG ()                          { return IMG; }
    public int getTipo ()                           { return Tipo; }
    public Boolean isDebuff()                       { return isDebuff; }
    public Boolean isBuff()                         { return isBuff; }
    
    
    
    public AuraTemplate ()
    {
        if (Myrran.getMundo().ListadeAuras().isEmpty()) ID=0;
        else { ID = Myrran.getMundo().ListadeAuras().get(Myrran.getMundo().ListadeAuras().size()-1).getID()+1;}
    }
    
    public void nuevaAuraTemplate ()
    {
        Nombre = ("Aura_"+ID);
        setTipo(0);
        setIsDebuff(true);
    }
    
    public void LoadIMG (String imgFilename)
    {
        URL url = getClass().getClassLoader().getResource(imgFilename);
        if (url == null) {System.out.println (imgFilename+" de la Imagen del Aura "+this.Nombre+" no se encuentra.");}
        else 
        {       
            try { IMG = ImageIO.read(url);}
            catch (Exception e) {}
        }
    }
    
    public void setTipo (int tipoID)                     
    { 
        Tipo = tipoID; 
        Stats = new StatSpell [Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.size()];
        
        for (int i=0; i<Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.size();i++)
        {
            StatSpell stat = new StatSpell();
            stat.Nombre = Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.get(i).Nombre;
            stat.ValorBase = Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.get(i).ValorBase;
            stat.TalentoActual = Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.get(i).TalentoActual;
            stat.TalentoMaximo = Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.get(i).TalentoMaximo;
            stat.CosteTalento = Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.get(i).CosteTalento;
            stat.BonoTalento = Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.get(i).BonoTalento;
            stat.isActivo = Myrran.getMundo().ListadeTiposAuras().get(tipoID).Stats.get(i).isActivo;
            Stats[i]=stat;
        }
    }
}
