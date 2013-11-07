package Skills;


import java.awt.Image;
import javax.imageio.ImageIO;
import Main.Myrran;
import Utils.Coordenadas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.net.URL;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hanto
 */
public class SpellTemplate 
{
    private int ID;
    private String Nombre;
    private String Descripcion;
    private int Tipo;           //DEFINE el tipo de Metodo al que se llama para ejecutar el skill asi como el numero y SpellStats que componen el vector Stats
    private Image IconoIMG;
    private String nombreRecursoIcono;
    public TextureRegion texture;
    private Image GraficoIMG;
    private String GraficoIMGFilename;
    private Coordenadas AnimacionBaseXY;
    private char Keybind;
    
    private StatSpell [] Stats;
    public StatSpell [] Stats()                         { return Stats; }
    
    private ArrayList <AuraTemplate> AurasQueAplica = new ArrayList <AuraTemplate>();
    public ArrayList <AuraTemplate> AurasQueAplica()    { return AurasQueAplica; }
    
    public void setID (int id)                          { ID = id; }
    public void setNombre (String nombre)               { Nombre = nombre;}
    public void setDescripcion (String d)               { Descripcion = d; }
    public void setIconoIMG (String Ifichero)           { LoadIcono(Ifichero);}
    public void setIconoIMG (Image IMG)                 { IconoIMG = IMG; }
    public void setGraficoIMG (String s)                { LoadGrafico(s); GraficoIMGFilename = s;}
    public void setGraficoIMG (Image IMG)               { GraficoIMG = IMG; }
    public void setGraficoIMGFilename (String s)        { GraficoIMGFilename = s; }
    public void setAnimacionBaseXY (Coordenadas XY)     { AnimacionBaseXY = XY;}
    public void setKeybind (char ch)                    { Keybind = ch; }
        
    public int getID()                                  { return ID;}
    public String getNombre ()                          { return Nombre;}
    public String getDescripcion ()                     { return Descripcion; }
    public Image getIconoIMG ()                         { return IconoIMG; }
    public String getNombreRecursoIcono()               { return nombreRecursoIcono; }
    public Image getGraficoIMG ()                       { return GraficoIMG; }
    public String getGraficoIMGFilename ()              { return GraficoIMGFilename; }
    public Coordenadas getAnimacionBaseXY()             { return AnimacionBaseXY; }
    public char getKeybind ()                           { return Keybind; }
    public int getTipo ()                               { return Tipo; }
    
    
    
    public SpellTemplate ()
    {
        if (Myrran.getMundo().ListadeSkills().isEmpty()) ID=0;
        else { ID = Myrran.getMundo().ListadeSkills().get(Myrran.getMundo().ListadeSkills().size()-1).getID()+1;}
    }
    
    public void nuevoSpellTemplate ()
    {
        Nombre = ("Spell_"+ID);
        setTipo(0);
    }
    
    public void LoadIcono (String imgFilename)
    {
        URL url = getClass().getClassLoader().getResource(imgFilename);
        if (url == null) {System.out.println (imgFilename+" del Icono del skill "+this.Nombre+" no se encuentra.");}
        else 
        {       
            try 
            { 
                IconoIMG = ImageIO.read(url);
                int inicio = imgFilename.lastIndexOf("/")+1;
                nombreRecursoIcono = imgFilename.substring(inicio, imgFilename.length()-4);
            }
            catch (Exception e) {}
        }
    }
    
    public void LoadGrafico (String imgFilename)
    {
        URL url = getClass().getClassLoader().getResource(imgFilename);
        if (url == null) {System.out.println (imgFilename+" del Grafico del skill "+this.Nombre+" no se encuentra.");}
        else 
        {       
            try { GraficoIMG = ImageIO.read(url);}
            catch (Exception e) {}
        }
    }
    
    public void setTipo (int tipoID)                     
    { 
        Tipo = tipoID; 
        Stats = new StatSpell [Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.size()];
        
        for (int i=0; i<Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.size();i++)
        {
            StatSpell stat = new StatSpell();
            stat.Nombre = Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.get(i).Nombre;
            stat.ValorBase = Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.get(i).ValorBase;
            stat.TalentoActual = Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.get(i).TalentoActual;
            stat.TalentoMaximo = Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.get(i).TalentoMaximo;
            stat.CosteTalento = Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.get(i).CosteTalento;
            stat.BonoTalento = Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.get(i).BonoTalento;
            stat.isActivo = Myrran.getMundo().ListadeTiposSpells().get(tipoID).Stats.get(i).isActivo;
            Stats[i]=stat;
        }
    }
    
    public void addAura (int AuraID, Boolean TalentoActualTambien)
    {
        AuraTemplate auraOrigen = Myrran.getMundo().ListadeAuras().get(AuraID);
        AuraTemplate auraDestino = new AuraTemplate();
        auraDestino.setID(auraOrigen.getID());
        auraDestino.setNombre(auraOrigen.getNombre());
        auraDestino.setDescripcion(auraOrigen.getDescripcion());
        auraDestino.setTipo(auraOrigen.getTipo());
        auraDestino.setIMG(auraOrigen.getIMG());
        auraDestino.setIsDebuff(auraOrigen.isDebuff());
        for (int i=0; i<auraOrigen.Stats().length;i++)
        {
            StatSpell stat = new StatSpell();
            stat.Nombre = auraOrigen.Stats()[i].Nombre;
            stat.setValorBase(auraOrigen.Stats()[i].ValorBase);
            if (TalentoActualTambien) { stat.TalentoActual = auraOrigen.Stats()[i].TalentoActual; }
            stat.TalentoMaximo = auraOrigen.Stats()[i].TalentoMaximo;
            stat.CosteTalento = auraOrigen.Stats()[i].CosteTalento;
            stat.BonoTalento = auraOrigen.Stats()[i].BonoTalento;
            stat.isActivo = auraOrigen.Stats()[i].isActivo;
            auraDestino.Stats()[i]=stat;
        }
        AurasQueAplica.add(auraDestino);
    }
    
    //cambia el Aura asociada a un Spell al template base que le especifiquemos
    public void setAura (int numAura, int AuraID)
    {
        AuraTemplate auraOrigen = null;
        for (int i=0; i<Myrran.getMundo().ListadeAuras().size();i++)
            { if (Myrran.getMundo().ListadeAuras().get(i).getID() == AuraID ) auraOrigen = Myrran.getMundo().ListadeAuras().get(i); }
        if (auraOrigen == null) return;
        
        AuraTemplate auraDestino = AurasQueAplica.get(numAura);
        auraDestino.setID(auraOrigen.getID());
        auraDestino.setNombre(auraOrigen.getNombre());
        auraDestino.setDescripcion(auraOrigen.getDescripcion());
        auraDestino.setTipo(auraOrigen.getTipo());
        auraDestino.setIMG(auraOrigen.getIMG());
        auraDestino.setIsDebuff(auraOrigen.isDebuff());
        for (int i=0; i<auraOrigen.Stats().length;i++)
        {
            StatSpell stat = new StatSpell();
            stat.Nombre = auraOrigen.Stats()[i].Nombre;
            stat.setValorBase(auraOrigen.Stats()[i].ValorBase);
            stat.TalentoActual = auraOrigen.Stats()[i].TalentoActual;
            stat.TalentoMaximo = auraOrigen.Stats()[i].TalentoMaximo;
            stat.CosteTalento = auraOrigen.Stats()[i].CosteTalento;
            stat.BonoTalento = auraOrigen.Stats()[i].BonoTalento;
            stat.isActivo = auraOrigen.Stats()[i].isActivo;
            auraDestino.Stats()[i]=stat;
        }
    }
    
    public static void copiarSpell (SpellTemplate spellOrigen, SpellTemplate spellDestino, Boolean TalentoActualTambien)
    {
        spellDestino.setID(spellOrigen.getID());
        spellDestino.setNombre(spellOrigen.getNombre());
        spellDestino.setDescripcion(spellOrigen.getDescripcion());
        spellDestino.setTipo(spellOrigen.getTipo());
        spellDestino.setIconoIMG(spellOrigen.getIconoIMG());
        spellDestino.setGraficoIMG(spellOrigen.getGraficoIMG());
        spellDestino.setGraficoIMGFilename(spellOrigen.getGraficoIMGFilename());
        try { spellDestino.setAnimacionBaseXY(new Coordenadas(spellOrigen.getAnimacionBaseXY().getX(),spellOrigen.getAnimacionBaseXY().getY())); } catch (Exception e) {}
        spellDestino.setKeybind(spellOrigen.getKeybind());
        
        for (int i=0; i<spellOrigen.Stats.length;i++)
        {
            StatSpell stat = new StatSpell();
            stat.Nombre = spellOrigen.Stats()[i].Nombre;
            stat.ValorBase = spellOrigen.Stats()[i].ValorBase;
            if (TalentoActualTambien) { stat.TalentoActual = spellOrigen.Stats()[i].TalentoActual; }
            stat.TalentoMaximo = spellOrigen.Stats()[i].TalentoMaximo;
            stat.CosteTalento = spellOrigen.Stats()[i].CosteTalento;
            stat.BonoTalento = spellOrigen.Stats()[i].BonoTalento;
            stat.isActivo = spellOrigen.Stats()[i].isActivo;
            spellDestino.Stats[i]=stat;
        }
        
        spellDestino.AurasQueAplica = new ArrayList <AuraTemplate>();
        
        for (int i=0; i<spellOrigen.AurasQueAplica.size();i++)
        {
            spellDestino.addAura(spellOrigen.AurasQueAplica.get(i).getID(), TalentoActualTambien);
        }
        
    }
    
 
    
}

