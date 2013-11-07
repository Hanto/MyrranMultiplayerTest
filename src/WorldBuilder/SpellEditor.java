/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WorldBuilder;

import DATA.MiscData;
import IO.UIManager;
import Main.Myrran;
import Mobiles.PC;
import Skills.AuraTemplate;
import Skills.SpellTemplate;
import Utils.Coordenadas;
import java.awt.Color;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hanto
 */
public class SpellEditor extends javax.swing.JFrame {

    private static int numSpell = 0;
    private static int numSpellAura = 0;
    
    private static int numSpellSprite =0;
    
    private static int numAura = 0;
    
    private static JToolBar tbSpellIconos = new JToolBar();
    private static JFrame fSpellIconos = new JFrame ("SpellIconos");
    private static JPanel pSpellSprites = new JPanel();
    private static JFrame fSpellSprites = new JFrame ("SpellSprites");
    
    
    private static int [] numStats = new int [0];
    
    private static ArrayList <Object []> TablaComparativa = new ArrayList <Object []>();
            
    public SpellEditor() 
    {
        initComponents();
                
        ActualizarListaSpells ();
        ActualizarDatosSpell ();
        ActualizarTablaSpellStats ();
        ActualizarListaAuras ();
        ActualizarDatosAura ();
        ActualizarTablaAuraStats ();
        ActualizarListaAurasQueAplica ();
        
        ActualizarTablaSpellStatsComparativa ();
        
        ActualizarToolbarSpellIconos ();
        ActualizarToolbarSpellSprites ();
        
        LSpells.setSelectedIndex(0);
        LAuras.setSelectedIndex(0);
        
        LSpells.setFixedCellHeight(12);
        LAuras.setFixedCellHeight(12);
        
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Utils.TableCellListener tcl = (Utils.TableCellListener)e.getSource();
                SalvarSpellStats (tcl.getRow(), tcl.getColumn(), tcl.getOldValue(), tcl.getNewValue());
            }
        };
        Utils.TableCellListener tcl = new Utils.TableCellListener(TSpellStats, action);
        
        
        Action action2 = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Utils.TableCellListener tcl2 = (Utils.TableCellListener)e.getSource();
                SalvarSpellStatsComparativa (tcl2.getRow(), tcl2.getColumn(), tcl2.getOldValue(), tcl2.getNewValue());
            }
        };
        Utils.TableCellListener tcl2 = new Utils.TableCellListener(TSpellStatsComparativa, action2);
        
        
        Action action3 = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Utils.TableCellListener tcl3 = (Utils.TableCellListener)e.getSource();
                SalvarAuraStats (tcl3.getRow(), tcl3.getColumn(), tcl3.getOldValue(), tcl3.getNewValue());
            }
        };
        Utils.TableCellListener tcl3 = new Utils.TableCellListener(TAuraStats, action3);
        
        CBSpellAuraQueAplica.addActionListener (new ActionListener () 
        {  
            @Override
            public void actionPerformed(ActionEvent e) 
            {   
                if (Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().get(numSpellAura).getID() != CBSpellAuraQueAplica.getSelectedIndex()) 
                {
                    //Modifica el Tipo de Aura asociado al Spell
                    Myrran.getMundo().ListadeSkills().get(numSpell).setAura(numSpellAura, CBSpellAuraQueAplica.getSelectedIndex());
                    //Sincroniza el Spell Modificado con el del Player
                    Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell)); 
                }
                ActualizarTablaSpellStats ();
                ActualizarListaAurasQueAplica ();
            }
        });
        
        CBAuraTipo.addActionListener (new ActionListener () 
        {  
            @Override
            public void actionPerformed(ActionEvent e) 
            {   
                //Modifica el Tipo de Aura asociado al Spell
                if (Myrran.getMundo().ListadeAuras().get(numAura).getTipo() != CBAuraTipo.getSelectedIndex()) 
                {
                    Myrran.getMundo().ListadeAuras().get(numAura).setTipo(CBAuraTipo.getSelectedIndex());
                    int auraID = Myrran.getMundo().ListadeAuras().get(numAura).getID();
                    for (int i=0;i<Myrran.getMundo().ListadeSkills().size();i++)
                    {
                        SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(i);
                        for (int j=0;j<spell.AurasQueAplica().size();j++)
                        {
                            if (spell.AurasQueAplica().get(j).getID() == auraID) 
                            {
                                spell.setAura(j, auraID);
                                //Sincroniza el Spell Modificado con el del Player
                                Myrran.getMundo().getPlayer().sincronizaSpell(spell);
                            }
                        }
                    } 
                }
                ActualizarTablaSpellStatsComparativa ();
                ActualizarTablaSpellStats ();
                ActualizarTablaAuraStats ();
                ActualizarListaAuras ();
            }
        });
    }
    
    public static void ActualizarListaSpells ()
    {
        DefaultListModel dl = new DefaultListModel ();
        for (int i=0; i<Myrran.getMundo().ListadeSkills().size();i++)
        {dl.addElement(Myrran.getMundo().ListadeSkills().get(i).getNombre());}
        
        LSpells.setModel(dl);  
    }
    
    public static void ActualizarListaAuras ()
    {
        DefaultListModel dl = new DefaultListModel ();
        for (int i=0; i<Myrran.getMundo().ListadeAuras().size();i++)
        {dl.addElement(Myrran.getMundo().ListadeAuras().get(i).getNombre());}
        
        LAuras.setModel(dl);
    }
    
    public static void ActualizarListaAurasQueAplica ()
    {
        DefaultListModel dl = new DefaultListModel ();
        for (int i=0; i<Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().size();i++)
        {dl.addElement(Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().get(i).getNombre());}
        
        LSpellAurasQueAplica.setModel(dl);
    
        if (Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().isEmpty()) {CBSpellAuraQueAplica.setEnabled(false);}
        else { CBSpellAuraQueAplica.setEnabled(true); CBSpellAuraQueAplica.setSelectedIndex(Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().get(numSpellAura).getID());}
    }
        
    public static void ActualizarDatosAura ()
    {
        try 
        {
            TFAuraID.setText(Integer.toString(Myrran.getMundo().ListadeAuras().get(numAura).getID()));
            TFAuraNombre.setText(Myrran.getMundo().ListadeAuras().get(numAura).getNombre());
            CBAuraTipo.setSelectedIndex(Myrran.getMundo().ListadeAuras().get(numAura).getTipo());        
        } catch (Exception e) {}
    }
    
    public static void ActualizarDatosSpell ()
    {
        try 
        {
            SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(numSpell);
            TFSpellID.setText(Integer.toString(spell.getID()));
            TFSpellNombre.setText(spell.getNombre());
            CBSpellTipo.setSelectedIndex(spell.getTipo());
            if (spell.getIconoIMG() != null) 
                {BSpellIcon.setIcon(new ImageIcon (spell.getIconoIMG()));}
            else {BSpellIcon.setIcon(null);}
            if (spell.getGraficoIMG() != null)
            {  
                BufferedImage Bimg = (BufferedImage)spell.getGraficoIMG();
                Image img = (Image)Bimg.getSubimage(spell.getAnimacionBaseXY().getX()*MiscData.MAP3D_TILESIZE, spell.getAnimacionBaseXY().getY()*MiscData.MAP3D_TILESIZE, MiscData.MAP3D_TILESIZE*3, MiscData.MAP3D_TILESIZE);
                BSpellSprite.setIcon(new ImageIcon (img));
            }
            else { BSpellSprite.setIcon(null); }
            ActualizarListaAurasQueAplica ();
        } catch (Exception e) {}
    }
    
    public static void ActualizarTablaSpellStatsComparativa ()
    {
        if (Myrran.getMundo().ListadeSkills().isEmpty()) return;
        
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Spell:Aura");dtm.addColumn("Nombre");dtm.addColumn("ValorBase");dtm.addColumn("ValorMaximo");dtm.addColumn("TActual");dtm.addColumn("TMaximo"); dtm.addColumn("CosteTalento");dtm.addColumn("BonoTalento");
        TablaComparativa.removeAll(TablaComparativa);
        
        SpellTemplate spellBase = Myrran.getMundo().ListadeSkills().get(numSpell);
        for (int i=0; i < numStats.length;i++)    
        {
            if (numStats[i]<spellBase.Stats().length) { BuscarListadeSkills_porTipoyStat (spellBase.getTipo(), numStats[i]); }
            else
            {
                int numFila =spellBase.Stats().length;
                
                for (int j=0; j<spellBase.AurasQueAplica().size();j++)
                {
                    AuraTemplate auraBase = spellBase.AurasQueAplica().get(j);
                    for (int k=0; k<auraBase.Stats().length;k++)
                    {
                        if (numFila == numStats[i]) { BuscarListadeAuras_porTipoyStat (/*spellBase.getTipo(),*/ auraBase.getTipo(), k); }
                        numFila++;
                    }
                }
            }
        }
        for (int i=0; i<TablaComparativa.size();i++) { dtm.addRow(TablaComparativa.get(i)); }
        
        TSpellStatsComparativa.setModel(dtm);
        TSpellStatsComparativa.setDefaultRenderer(Object.class,new TableRender());
        TSpellStatsComparativa.getColumnModel().getColumn(0).setPreferredWidth(130);
    }
    
    public static void BuscarListadeAuras_porTipoyStat (/*int SpellTipo,*/ int AuraTipo, int numStat)
    {
        for (int i=0; i<Myrran.getMundo().ListadeSkills().size();i++)
        {
            SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(i);
            //if (spell.getTipo() == SpellTipo)
            {
                for (int j=0; j<spell.AurasQueAplica().size();j++)
                {
                    AuraTemplate aura = spell.AurasQueAplica().get(j);
                    if (aura.getTipo() == AuraTipo)
                    {
                        for (int k=0; k<aura.Stats().length;k++)
                        {
                            if (k == numStat)
                            {
                                Object [] S = new Object [11];
                                S[0]=spell.getNombre()+"-"+Myrran.getMundo().ListadeTiposAuras().get(aura.getTipo()).getNombre()+": "+aura.getNombre();
                                S[1]=aura.Stats()[k].Nombre;
                                S[2]=aura.Stats()[k].ValorBase;
                                S[3]=aura.Stats()[k].ValorBase+aura.Stats()[k].TalentoMaximo*aura.Stats()[k].BonoTalento;
                                S[4]=aura.Stats()[k].TalentoActual;
                                S[5]=aura.Stats()[k].TalentoMaximo;
                                S[6]=aura.Stats()[k].CosteTalento;
                                S[7]=aura.Stats()[k].BonoTalento;
                                
                                //Referencia al spell para cuando modifiquemos los Datos.
                                S[8]=spell.getID();
                                S[9]=j;
                                S[10]=k;
                                TablaComparativa.add(S);
                            }
                        }
                    }    
                }
            }
        }
    }
    
    public static void BuscarListadeSkills_porTipoyStat (int tipo, int numStat)
    {
        for (int i=0; i<Myrran.getMundo().ListadeSkills().size();i++)
        {
            SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(i);
            if (spell.getTipo() == tipo)
            {
                for (int j=0; j<spell.Stats().length;j++)
                {
                    if (j == numStat)
                    {
                        Object [] S = new Object [11];
                        S[0]=spell.getNombre();
                        S[1]=spell.Stats()[j].Nombre;
                        S[2]=spell.Stats()[j].ValorBase;
                        S[3]=spell.Stats()[j].ValorBase+spell.Stats()[j].TalentoMaximo*spell.Stats()[j].BonoTalento;
                        S[4]=spell.Stats()[j].TalentoActual;
                        S[5]=spell.Stats()[j].TalentoMaximo;
                        S[6]=spell.Stats()[j].CosteTalento;
                        S[7]=spell.Stats()[j].BonoTalento;
                        
                        //Referencia al Aura, para cuando modifiquemos los Datos.
                        S[8]=spell.getID();
                        S[9]=-1;
                        S[10]=j;
                        TablaComparativa.add(S);
                    }
                }
            }
        }
    }
    
    public static void ActualizarTablaSpellStats ()
    {
        if (Myrran.getMundo().ListadeSkills().isEmpty()) return;
        
        DefaultTableModel dtm = new DefaultTableModel();
        SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(numSpell);
        
        dtm.addColumn("");dtm.addColumn("Nombre");dtm.addColumn("ValorBase");dtm.addColumn("ValorMaximo");dtm.addColumn("TActual");dtm.addColumn("TMaximo"); dtm.addColumn("CosteTalento");dtm.addColumn("BonoTalento");
        
        for (int i=0; i<spell.Stats().length;i++)
        {
            Object [] S = new Object [8];
            S[1]=spell.Stats()[i].Nombre;
            S[2]=spell.Stats()[i].ValorBase;
            S[3]=spell.Stats()[i].ValorBase+spell.Stats()[i].TalentoMaximo*spell.Stats()[i].BonoTalento;
            S[4]=spell.Stats()[i].TalentoActual;
            S[5]=spell.Stats()[i].TalentoMaximo;
            S[6]=spell.Stats()[i].CosteTalento;
            S[7]=spell.Stats()[i].BonoTalento;
            dtm.addRow(S);
        }
        
        for (int i=0; i<spell.AurasQueAplica().size();i++)
        {
            Object [] S = new Object [8];
            AuraTemplate aura = spell.AurasQueAplica().get(i);
            S[0]=aura.getNombre();
            
            for (int j=0; j<aura.Stats().length;j++)
            {
                S[1]=aura.Stats()[j].Nombre;
                S[2]=aura.Stats()[j].ValorBase;
                S[3]=aura.Stats()[j].ValorBase+aura.Stats()[j].TalentoMaximo*aura.Stats()[j].BonoTalento;
                S[4]=aura.Stats()[j].TalentoActual;
                S[5]=aura.Stats()[j].TalentoMaximo;
                S[6]=aura.Stats()[j].CosteTalento;
                S[7]=aura.Stats()[j].BonoTalento;
                dtm.addRow(S);
            }
        }
        TSpellStats.setModel(dtm);
    }
    
    public static void ActualizarTablaAuraStats ()
    {
        if (Myrran.getMundo().ListadeAuras().isEmpty()) return;
        
        DefaultTableModel dtm = new DefaultTableModel();
        AuraTemplate aura = Myrran.getMundo().ListadeAuras().get(numAura);
        
        dtm.addColumn("");dtm.addColumn("Nombre");dtm.addColumn("ValorBase");dtm.addColumn("ValorMaximo");dtm.addColumn("TalentoActual");dtm.addColumn("TalentoMaximo"); dtm.addColumn("CosteTalento");dtm.addColumn("BonoTalento");
        
        for (int i=0; i<aura.Stats().length;i++)
        {
            Object [] S = new Object [8];
            S[1]=aura.Stats()[i].Nombre;
            S[2]=aura.Stats()[i].ValorBase;
            S[3]=aura.Stats()[i].ValorBase+aura.Stats()[i].TalentoMaximo*aura.Stats()[i].BonoTalento;
            S[4]=aura.Stats()[i].TalentoActual;
            S[5]=aura.Stats()[i].TalentoMaximo;
            S[6]=aura.Stats()[i].CosteTalento;
            S[7]=aura.Stats()[i].BonoTalento;
            dtm.addRow(S);
        }
        TAuraStats.setModel(dtm);
    }
    
    public static void SalvarSpellStatsComparativa (int Fila, int Columna, Object ValorOriginal, Object ValorFinal)
    {
        int spellID = Integer.parseInt(TablaComparativa.get(Fila)[8].toString());
        SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(spellID);
        SalvarSpellStatsComparativa (spell, Fila, Columna, ValorOriginal, ValorFinal);
        
        PC player = Myrran.getMundo().getPlayer();
        for (int i=0; i < player.ListadeSkills().size(); i++)
        {
            if ( player.ListadeSkills().get(i).getID() == spellID ) 
            { 
                spell = player.ListadeSkills().get(i);
                SalvarSpellStatsComparativa (spell, Fila, Columna, ValorOriginal, ValorFinal);
            }
        }  
    }
    
    public static void SalvarSpellStatsComparativa (SpellTemplate spell, int Fila, int Columna, Object ValorOriginal, Object ValorFinal)
    {
        int auraID = Integer.parseInt(TablaComparativa.get(Fila)[9].toString());
        int numStat = Integer.parseInt(TablaComparativa.get(Fila)[10].toString());
        
        if (auraID <0)
        {    
            switch (Columna)
            {
                case 1:         spell.Stats()[numStat].Nombre=ValorFinal.toString();break;
                case 2:         spell.Stats()[numStat].setValorBase(Double.parseDouble(ValorFinal.toString()));break;
                case 3:         spell.Stats()[numStat].BonoTalento=(Double.parseDouble(ValorFinal.toString())-spell.Stats()[numStat].ValorBase)/spell.Stats()[numStat].TalentoMaximo;break;
                case 4:         spell.Stats()[numStat].TalentoActual= Integer.parseInt(ValorFinal.toString()); break;
                case 5:         spell.Stats()[numStat].TalentoMaximo= Integer.parseInt(ValorFinal.toString());break;
                case 6:         spell.Stats()[numStat].CosteTalento= Integer.parseInt(ValorFinal.toString());break;
                case 7:         spell.Stats()[numStat].BonoTalento= Double.parseDouble(ValorFinal.toString()); break;     
            }
        }
        else
        {
            AuraTemplate aura = spell.AurasQueAplica().get(auraID);
            switch (Columna)
            {
                case 1:         aura.Stats()[numStat].Nombre=ValorFinal.toString(); break;
                case 2:         aura.Stats()[numStat].setValorBase(Double.parseDouble(ValorFinal.toString())); break;
                case 3:         aura.Stats()[numStat].BonoTalento=(Double.parseDouble(ValorFinal.toString())-aura.Stats()[numStat].ValorBase)/aura.Stats()[numStat].TalentoMaximo;break;
                case 4:         aura.Stats()[numStat].TalentoActual= Integer.parseInt(ValorFinal.toString()); break;
                case 5:         aura.Stats()[numStat].TalentoMaximo= Integer.parseInt(ValorFinal.toString()); break;
                case 6:         aura.Stats()[numStat].CosteTalento= Integer.parseInt(ValorFinal.toString()); break;
                case 7:         aura.Stats()[numStat].BonoTalento= Double.parseDouble(ValorFinal.toString()); break;   
            }
        }
        ActualizarTablaSpellStats();
        ActualizarTablaSpellStatsComparativa();
    }
    
    public static void SalvarSpellStats (int Fila, int Columna, Object ValorOriginal, Object ValorFinal)
    {
        SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(numSpell);
        SalvarSpellStats (spell, Fila, Columna, ValorOriginal, ValorFinal);
        
        PC player = Myrran.getMundo().getPlayer();
        for (int i=0; i < player.ListadeSkills().size(); i++)
        {
            if ( player.ListadeSkills().get(i).getID() == Myrran.getMundo().ListadeSkills().get(numSpell).getID() ) 
            { 
                spell = player.ListadeSkills().get(i);
                SalvarSpellStats (spell, Fila, Columna, ValorOriginal, ValorFinal);
            }
        }
    }
    
    public static void SalvarSpellStats (SpellTemplate Spell, int Fila, int Columna, Object ValorOriginal, Object ValorFinal)
    {
        SpellTemplate spell = Spell;
               
        if (Fila< spell.Stats().length)
        switch (Columna)
        {
            case 1:         spell.Stats()[Fila].Nombre=ValorFinal.toString();break;
            case 2:         spell.Stats()[Fila].setValorBase(Double.parseDouble(ValorFinal.toString()));break;
            case 3:         spell.Stats()[Fila].BonoTalento=(Double.parseDouble(ValorFinal.toString())-spell.Stats()[Fila].ValorBase)/spell.Stats()[Fila].TalentoMaximo;break;
            case 4:         spell.Stats()[Fila].TalentoActual= Integer.parseInt(ValorFinal.toString()); break;
            case 5:         spell.Stats()[Fila].TalentoMaximo= Integer.parseInt(ValorFinal.toString());break;
            case 6:         spell.Stats()[Fila].CosteTalento= Integer.parseInt(ValorFinal.toString());break;
            case 7:         spell.Stats()[Fila].BonoTalento= Double.parseDouble(ValorFinal.toString()); break;     
        }
        
        int numFila = spell.Stats().length;
        for (int i=0; i<spell.AurasQueAplica().size();i++)
        {
            AuraTemplate aura = spell.AurasQueAplica().get(i);
            for (int j=0; j<aura.Stats().length;j++)
            {
                if (numFila == Fila)
                {   
                    switch (Columna)
                    {
                        case 0:         aura.setNombre(ValorFinal.toString()); break;
                        case 1:         aura.Stats()[j].Nombre=ValorFinal.toString(); break;
                        case 2:         aura.Stats()[j].setValorBase(Double.parseDouble(ValorFinal.toString())); break;
                        case 3:         aura.Stats()[j].BonoTalento=(Double.parseDouble(ValorFinal.toString())-aura.Stats()[j].ValorBase)/aura.Stats()[j].TalentoMaximo;break;
                        case 4:         aura.Stats()[j].TalentoActual= Integer.parseInt(ValorFinal.toString()); break;
                        case 5:         aura.Stats()[j].TalentoMaximo= Integer.parseInt(ValorFinal.toString()); break;
                        case 6:         aura.Stats()[j].CosteTalento= Integer.parseInt(ValorFinal.toString()); break;
                        case 7:         aura.Stats()[j].BonoTalento= Double.parseDouble(ValorFinal.toString()); break;   
                    }   
                }
                numFila++;
            }
        }
        ActualizarTablaSpellStats();
        ActualizarListaAurasQueAplica();
    }
    
    public static void SalvarAuraStats (int Fila, int Columna, Object ValorOriginal, Object ValorFinal)
    {
        AuraTemplate aura = Myrran.getMundo().ListadeAuras().get(numAura);
        SalvarAuraStats (aura, Fila, Columna, ValorOriginal, ValorFinal);
    }
    
    public static void SalvarAuraStats (AuraTemplate Aura, int Fila, int Columna, Object ValorOriginal, Object ValorFinal)
    {
        AuraTemplate aura = Aura;
               
        if (Fila< aura.Stats().length)
        switch (Columna)
        {
            case 1:         aura.Stats()[Fila].Nombre=ValorFinal.toString();break;
            case 2:         aura.Stats()[Fila].setValorBase(Double.parseDouble(ValorFinal.toString()));break;
            case 3:         aura.Stats()[Fila].BonoTalento=(Double.parseDouble(ValorFinal.toString())-aura.Stats()[Fila].ValorBase)/aura.Stats()[Fila].TalentoMaximo;break;
            case 4:         aura.Stats()[Fila].TalentoActual= Integer.parseInt(ValorFinal.toString()); break;
            case 5:         aura.Stats()[Fila].TalentoMaximo= Integer.parseInt(ValorFinal.toString());break;
            case 6:         aura.Stats()[Fila].CosteTalento= Integer.parseInt(ValorFinal.toString());break;
            case 7:         aura.Stats()[Fila].BonoTalento= Double.parseDouble(ValorFinal.toString()); break;     
        }
        ActualizarTablaAuraStats();
    }
    
    public static void ActualizarToolbarSpellIconos ()
    {
        tbSpellIconos.removeAll();
        for (int i=0; i<MiscData.RESOURCES_SPELLIconos.length;i++)
        {   
            
            JButton button = new JButton();
            String targetFilename = MiscData.RESOURCES_SPELLIconos[i];
           
            Image imga;
            
            URL url = WorldEditor.class.getClassLoader().getResource(targetFilename);
            if (url == null) {System.out.println (targetFilename+" no se encuentra.");}
            else 
            {       
                try 
                {
                    imga = ImageIO.read(url);
                    ImageIcon ic = new ImageIcon(imga);
                    button.setIcon(ic);
                }
                catch (Exception e) {}
            }
            button.setFocusable(false);
            
            final int id = i;
            
            button.addActionListener(new ActionListener() 
            {   public void actionPerformed (ActionEvent evt) 
                {
                    //Modifica el Grafico del Icono del Spell
                    Myrran.getMundo().ListadeSkills().get(numSpell).setIconoIMG(MiscData.RESOURCES_SPELLIconos[id]);
                    //Sincroniza el Spell Modificado con el del Player
                    Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell));
                    ActualizarDatosSpell();
                }
            });    
            
            tbSpellIconos.add(button);
        }
        fSpellIconos.add(tbSpellIconos);
        tbSpellIconos.setFloatable(false);
        fSpellIconos.setAlwaysOnTop(true);
        fSpellIconos.setResizable(false);
        fSpellIconos.pack();
    }
    
    public static void ActualizarToolbarSpellSprites ()
    {
        pSpellSprites.removeAll();
        
        {   
            String targetFilename = MiscData.RESOURCES_SPELLSprites[numSpellSprite];
           
            URL url = WorldEditor.class.getClassLoader().getResource(targetFilename);
            if (url == null) {System.out.println (targetFilename+" no se encuentra."); return;}
                             
            Image imga = LoadIMG (url);
            
            int alturaIMG = imga.getHeight(null);
            int anchuraIMG = imga.getWidth(null);
            
            for (int j=0;j<alturaIMG;j=j+MiscData.MAP3D_TILESIZE)
            {
                for (int k=0;k<anchuraIMG;k=k+MiscData.MAP3D_TILESIZE*3)
                {
                    BufferedImage bimga = (BufferedImage)imga;
                    bimga = bimga.getSubimage(k, j, MiscData.MAP3D_TILESIZE*3, MiscData.MAP3D_TILESIZE);
                    ImageIcon ic = new ImageIcon(bimga);
                    
                    JButton button = new JButton();
                    button.setIcon(ic);
                    button.setFocusable(false);
                    
                    final String stringImagen = targetFilename;
                    final int CoordX = k/(MiscData.MAP3D_TILESIZE);
                    final int CoordY = j/MiscData.MAP3D_TILESIZE;
            
                    button.addActionListener(new ActionListener() 
                    {   public void actionPerformed (ActionEvent evt) 
                        {
                            //Modifica el grafico de la Animacion del Spell
                            Myrran.getMundo().ListadeSkills().get(numSpell).LoadGrafico(stringImagen);
                            Myrran.getMundo().ListadeSkills().get(numSpell).setAnimacionBaseXY(new Coordenadas(CoordX, CoordY));
                            //Sincroniza el Spell Modificado con el del Player
                            Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell));
                            ActualizarDatosSpell();
                        }
                    });
                    button.setSize(MiscData.MAP3D_TILESIZE*3, MiscData.MAP3D_TILESIZE);
                    button.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    button.setLocation(k, j);
                    button.setBackground(new Color (250,250,204));
                    //button.setBorderPainted(false);
                    pSpellSprites.add(button);
                }
            }
            JButton button = new JButton();
            button.addActionListener(new ActionListener() 
            {   public void actionPerformed (ActionEvent evt) 
                {
                    numSpellSprite--;
                    if (numSpellSprite <0) numSpellSprite = 0;
                    else ActualizarToolbarSpellSprites();
                }
            });
            button.setText("Sprite Anterior");
            button.setFocusable(false);
            button.setLocation(0, alturaIMG);
            button.setSize(anchuraIMG/2, 32);
            button.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            pSpellSprites.add(button);
            
            button = new JButton();
            button.addActionListener(new ActionListener() 
            {   public void actionPerformed (ActionEvent evt) 
                {
                    numSpellSprite++;
                    if (numSpellSprite >= MiscData.RESOURCES_SPELLSprites.length) numSpellSprite = MiscData.RESOURCES_SPELLSprites.length-1;
                    else ActualizarToolbarSpellSprites();
                }
            });
            button.setText("Sprite Posterior");
            button.setFocusable(false);
            button.setLocation(anchuraIMG/2, alturaIMG);
            button.setSize(anchuraIMG/2, 32);
            button.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            pSpellSprites.add(button);
            
            
            
            fSpellSprites.add(pSpellSprites);
            pSpellSprites.setLayout(null);
            int rebordeAncho = (anchuraIMG/(MiscData.MAP3D_TILESIZE*3))*1+1; //por cada boton añade 4 pixeles de ancho
            int rebordeAlto = (alturaIMG/MiscData.MAP3D_TILESIZE)*3+1; //por cada boton añade 5 pixel de alto menos el reborde de arriba y abajo, que tienen la mitad, por eso el -2
            fSpellSprites.setSize(anchuraIMG+rebordeAncho, alturaIMG+rebordeAlto+32);
            fSpellSprites.setResizable(false);
        }
    }
    
    public static Image LoadIMG (URL url)
    {
        try { return ImageIO.read(url); }
        catch (Exception e) {return null;}
    }
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SSpellStats = new javax.swing.JScrollPane();
        TSpellStats = new javax.swing.JTable();
        SAuraStats = new javax.swing.JScrollPane();
        TAuraStats = new javax.swing.JTable();
        SSpellStatsComparativa = new javax.swing.JScrollPane();
        TSpellStatsComparativa = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        TFSpellID = new javax.swing.JTextField();
        TFSpellNombre = new javax.swing.JTextField();
        BSpellIcon = new javax.swing.JButton(new ImageIcon (DATA.MiscData.ESTATICO_DIRECCIONBITMAP_NEW));
        BSpellSprite = new javax.swing.JButton(new ImageIcon (DATA.MiscData.ESTATICO_DIRECCIONBITMAP_NEW));
        CBSpellTipo = new javax.swing.JComboBox();
        CBSpellAuraQueAplica = new javax.swing.JComboBox();
        BDescripcion = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        SSpells = new javax.swing.JScrollPane();
        LSpells = new javax.swing.JList();
        BBorrarSpell = new javax.swing.JButton();
        BAñadirSpell = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        SSpellAurasQueAplica = new javax.swing.JScrollPane();
        LSpellAurasQueAplica = new javax.swing.JList();
        BBorrarAuraQueAplica = new javax.swing.JButton();
        BAñadirAuraQueAplica = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        SAuras = new javax.swing.JScrollPane();
        LAuras = new javax.swing.JList();
        BBorrarAura = new javax.swing.JButton();
        BAñadirAura = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        TFAuraID = new javax.swing.JTextField();
        TFAuraNombre = new javax.swing.JTextField();
        CBAuraTipo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Myrran Spell Editor");

        TSpellStats.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TSpellStats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TSpellStats.setGridColor(new java.awt.Color(204, 204, 204));
        TSpellStats.setSelectionBackground(new java.awt.Color(255, 204, 51));
        TSpellStats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TSpellStatsMousePressed(evt);
            }
        });
        TSpellStats.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                TSpellStatsMouseDragged(evt);
            }
        });
        SSpellStats.setViewportView(TSpellStats);

        TAuraStats.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TAuraStats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TAuraStats.setGridColor(new java.awt.Color(204, 204, 204));
        TAuraStats.setSelectionBackground(new java.awt.Color(255, 204, 51));
        SAuraStats.setViewportView(TAuraStats);

        TSpellStatsComparativa.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TSpellStatsComparativa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TSpellStatsComparativa.setGridColor(new java.awt.Color(204, 204, 204));
        TSpellStatsComparativa.setSelectionBackground(new java.awt.Color(255, 204, 51));
        SSpellStatsComparativa.setViewportView(TSpellStatsComparativa);

        TFSpellID.setEditable(false);
        TFSpellID.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFSpellID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFSpellID.setText("ID");
        TFSpellID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFSpellIDKeyPressed(evt);
            }
        });

        TFSpellNombre.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFSpellNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFSpellNombre.setText("Nombre");
        TFSpellNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFSpellNombreKeyPressed(evt);
            }
        });

        BSpellIcon.setBackground(new java.awt.Color(255, 255, 204));
        BSpellIcon.setText("");
        BSpellIcon.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BSpellIcon.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BSpellIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSpellIconActionPerformed(evt);
            }
        });

        BSpellSprite.setBackground(new java.awt.Color(255, 255, 204));
        BSpellSprite.setText("");
        BSpellSprite.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BSpellSprite.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BSpellSprite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSpellSpriteActionPerformed(evt);
            }
        });

        CBSpellTipo.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        String [] pp = new String [Myrran.getMundo().ListadeTiposSpells().size()];
        for (int i=0;i<Myrran.getMundo().ListadeTiposSpells().size();i++) {pp[i]=Myrran.getMundo().ListadeTiposSpells().get(i).getNombre();}
        CBSpellTipo.setModel(new javax.swing.DefaultComboBoxModel(pp));
        CBSpellTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CBSpellTipoItemStateChanged(evt);
            }
        });

        CBSpellAuraQueAplica.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        String [] p = new String [Myrran.getMundo().ListadeAuras().size()];
        for (int i=0;i<Myrran.getMundo().ListadeAuras().size();i++) {p[i]=Myrran.getMundo().ListadeAuras().get(i).getNombre();}
        CBSpellAuraQueAplica.setModel(new javax.swing.DefaultComboBoxModel(p));

        BDescripcion.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BDescripcion.setText("Descripcion");
        BDescripcion.setAlignmentY(0.0F);
        BDescripcion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDescripcionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TFSpellID, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(TFSpellNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BSpellIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BSpellSprite, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(CBSpellTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(CBSpellAuraQueAplica, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(TFSpellID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TFSpellNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BSpellIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BSpellSprite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CBSpellTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CBSpellAuraQueAplica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        LSpells.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        LSpells.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LSpells.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LSpells.setSelectionBackground(new java.awt.Color(255, 204, 51));
        LSpells.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LSpellsValueChanged(evt);
            }
        });
        SSpells.setViewportView(LSpells);

        BBorrarSpell.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BBorrarSpell.setText("-");
        BBorrarSpell.setAlignmentY(0.0F);
        BBorrarSpell.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BBorrarSpell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBorrarSpellActionPerformed(evt);
            }
        });

        BAñadirSpell.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BAñadirSpell.setText("+");
        BAñadirSpell.setAlignmentY(0.0F);
        BAñadirSpell.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BAñadirSpell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAñadirSpellActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SSpells, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(BBorrarSpell, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAñadirSpell, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(SSpells, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BBorrarSpell)
                    .addComponent(BAñadirSpell)))
        );

        LSpellAurasQueAplica.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        LSpellAurasQueAplica.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LSpellAurasQueAplica.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LSpellAurasQueAplica.setSelectionBackground(new java.awt.Color(255, 204, 51));
        LSpellAurasQueAplica.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LSpellAurasQueAplicaValueChanged(evt);
            }
        });
        SSpellAurasQueAplica.setViewportView(LSpellAurasQueAplica);

        BBorrarAuraQueAplica.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BBorrarAuraQueAplica.setText("-");
        BBorrarAuraQueAplica.setAlignmentY(0.0F);
        BBorrarAuraQueAplica.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BBorrarAuraQueAplica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBorrarAuraQueAplicaActionPerformed(evt);
            }
        });

        BAñadirAuraQueAplica.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BAñadirAuraQueAplica.setText("+");
        BAñadirAuraQueAplica.setAlignmentY(0.0F);
        BAñadirAuraQueAplica.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BAñadirAuraQueAplica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAñadirAuraQueAplicaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(BBorrarAuraQueAplica, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BAñadirAuraQueAplica, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SSpellAurasQueAplica, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(SSpellAurasQueAplica, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BBorrarAuraQueAplica)
                    .addComponent(BAñadirAuraQueAplica)))
        );

        LAuras.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        LAuras.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LAuras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LAuras.setSelectionBackground(new java.awt.Color(255, 204, 51));
        LAuras.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LAurasValueChanged(evt);
            }
        });
        SAuras.setViewportView(LAuras);

        BBorrarAura.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BBorrarAura.setText("-");
        BBorrarAura.setAlignmentY(0.0F);
        BBorrarAura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BBorrarAura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBorrarAuraActionPerformed(evt);
            }
        });

        BAñadirAura.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        BAñadirAura.setText("+");
        BAñadirAura.setAlignmentY(0.0F);
        BAñadirAura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BAñadirAura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAñadirAuraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SAuras)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(BBorrarAura, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BAñadirAura, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(SAuras, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BBorrarAura)
                    .addComponent(BAñadirAura))
                .addContainerGap())
        );

        TFAuraID.setEditable(false);
        TFAuraID.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFAuraID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFAuraID.setText("ID");

        TFAuraNombre.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        TFAuraNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TFAuraNombre.setText("Nombre");
        TFAuraNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TFAuraNombreKeyPressed(evt);
            }
        });

        CBAuraTipo.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        p = new String [Myrran.getMundo().ListadeTiposAuras().size()];
        for (int i=0;i<Myrran.getMundo().ListadeTiposAuras().size();i++) {p[i]=Myrran.getMundo().ListadeTiposAuras().get(i).getNombre();}
        CBAuraTipo.setModel(new javax.swing.DefaultComboBoxModel(p));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TFAuraID)
            .addComponent(TFAuraNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
            .addComponent(CBAuraTipo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(TFAuraID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TFAuraNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CBAuraTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SSpellStats, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                    .addComponent(SAuraStats)
                    .addComponent(SSpellStatsComparativa))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SSpellStats, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SAuraStats, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SSpellStatsComparativa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 117, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LSpellsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LSpellsValueChanged
        if ( LSpells.getSelectedIndex() >= 0 && LSpells.getSelectedIndex() < Myrran.getMundo().ListadeSkills().size() ) { numSpell = LSpells.getSelectedIndex(); }
        else {numSpell = 0;}
       
        LSpells.setSelectedIndex(numSpell);
        numSpellAura = 0;
        
        ActualizarDatosSpell ();
        ActualizarTablaSpellStats ();
        ActualizarTablaSpellStatsComparativa ();
    }//GEN-LAST:event_LSpellsValueChanged

    private void TFSpellIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFSpellIDKeyPressed
        if (evt.getKeyCode()== KeyEvent.VK_ENTER)
        {
            try {Myrran.getMundo().ListadeSkills().get(numSpell).setID(Integer.parseInt(TFSpellID.getText()));}
            catch (Exception e) {}
            TFSpellID.setText(Integer.toString(Myrran.getMundo().ListadeSkills().get(numSpell).getID()));
        }
    }//GEN-LAST:event_TFSpellIDKeyPressed

    private void TFSpellNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFSpellNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            try 
            {
                //Modifica el Nombre del Spell
                Myrran.getMundo().ListadeSkills().get(numSpell).setNombre(TFSpellNombre.getText());
                //Sincroniza el Spell Modificado con el del Player
                Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell));
            }
            catch (Exception e) {}
            TFSpellNombre.setText(Myrran.getMundo().ListadeSkills().get(numSpell).getNombre());
            int aux = numSpell;
            ActualizarListaSpells ();
            LSpells.setSelectedIndex(aux);
        }
    }//GEN-LAST:event_TFSpellNombreKeyPressed

    private void LAurasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LAurasValueChanged
        if ( LAuras.getSelectedIndex() >= 0 && LAuras.getSelectedIndex() < Myrran.getMundo().ListadeAuras().size() ) { numAura = LAuras.getSelectedIndex(); }
                
        LAuras.setSelectedIndex(numAura);

        ActualizarDatosAura ();
        ActualizarTablaAuraStats ();
    }//GEN-LAST:event_LAurasValueChanged

    private void TFAuraNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TFAuraNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            try 
            {
                Myrran.getMundo().ListadeAuras().get(numAura).setNombre(TFAuraNombre.getText());
                int aux = CBSpellAuraQueAplica.getSelectedIndex();
                String [] p = new String [Myrran.getMundo().ListadeAuras().size()];
                for (int i=0;i<Myrran.getMundo().ListadeAuras().size();i++) {p[i]=Myrran.getMundo().ListadeAuras().get(i).getNombre();}
                CBSpellAuraQueAplica.setModel(new javax.swing.DefaultComboBoxModel(p));
                CBSpellAuraQueAplica.setSelectedIndex(aux);
                
                int auraID = Myrran.getMundo().ListadeAuras().get(numAura).getID();
                for (int i=0;i<Myrran.getMundo().ListadeSkills().size();i++)
                {
                    SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(i);
                    for (int j=0;j<spell.AurasQueAplica().size();j++)
                    {
                        if (spell.AurasQueAplica().get(j).getID() == auraID) 
                        {
                            spell.AurasQueAplica().get(j).setNombre(Myrran.getMundo().ListadeAuras().get(numAura).getNombre());
                            //Sincroniza el Spell Modificado con el del Player
                            Myrran.getMundo().getPlayer().sincronizaSpell(spell);
                        }
                    }
                }
            }
            catch (Exception e) {}
            TFAuraNombre.setText(Myrran.getMundo().ListadeAuras().get(numAura).getNombre());
            int aux = numAura;
            ActualizarListaAuras ();
            LAuras.setSelectedIndex(aux);
            ActualizarListaAurasQueAplica();
        }
    }//GEN-LAST:event_TFAuraNombreKeyPressed

    private void LSpellAurasQueAplicaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LSpellAurasQueAplicaValueChanged
        if (LSpellAurasQueAplica.getSelectedIndex() >=0 && LSpellAurasQueAplica.getSelectedIndex() < Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().size()) { numSpellAura = LSpellAurasQueAplica.getSelectedIndex(); }
        
        LSpellAurasQueAplica.setSelectedIndex(numSpellAura);
        
        try { CBSpellAuraQueAplica.setSelectedIndex(Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().get(numSpellAura).getID()); }
        catch (Exception e) { CBSpellAuraQueAplica.setEnabled(false); }
    }//GEN-LAST:event_LSpellAurasQueAplicaValueChanged

    private void TSpellStatsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TSpellStatsMousePressed
        numStats = TSpellStats.getSelectedRows();
        ActualizarTablaSpellStatsComparativa ();
    }//GEN-LAST:event_TSpellStatsMousePressed

    private void TSpellStatsMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TSpellStatsMouseDragged
        numStats = TSpellStats.getSelectedRows();
        ActualizarTablaSpellStatsComparativa ();
    }//GEN-LAST:event_TSpellStatsMouseDragged

    private void BBorrarAuraQueAplicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBorrarAuraQueAplicaActionPerformed
        if (Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().size() > 0 && numSpellAura < Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().size())
        {   
            //Elimina un Aura al  Spell
            Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().remove(numSpellAura);
            //Sincroniza el Spell Modificado con el del Player
            Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell));
        }
        else return;
        //despues de actualizar la lista el puntero siempre se coloca en la posicion -1, por tanto salvamos su posicion actual en la variable auxilar AUX
        int aux = numSpellAura--;
        if (numSpellAura <0) numSpellAura =0;
        ActualizarListaAurasQueAplica ();
        if (aux>=0) {LSpellAurasQueAplica.setSelectedIndex(aux);}
        else {LSpellAurasQueAplica.setSelectedIndex(0);numSpellAura=0;}
        ActualizarTablaSpellStats ();
    }//GEN-LAST:event_BBorrarAuraQueAplicaActionPerformed

    private void BAñadirAuraQueAplicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAñadirAuraQueAplicaActionPerformed
        //Añade un Aura al Spell
        Myrran.getMundo().ListadeSkills().get(numSpell).addAura(0, true);
        //Sincroniza el Spell Modificado con el del Player
        Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell));
        
        ActualizarListaAurasQueAplica ();
        LSpellAurasQueAplica.setSelectedIndex(Myrran.getMundo().ListadeSkills().get(numSpell).AurasQueAplica().size()-1);
        ActualizarTablaSpellStats();   
    }//GEN-LAST:event_BAñadirAuraQueAplicaActionPerformed

    private void CBSpellTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CBSpellTipoItemStateChanged
        if (CBSpellTipo.getSelectedIndex() != Myrran.getMundo().ListadeSkills().get(numSpell).getTipo())
        {
            //Modifica el TIPO del Spell
            Myrran.getMundo().ListadeSkills().get(numSpell).setTipo(CBSpellTipo.getSelectedIndex());
            //Sincroniza el Spell Modificado con el del Player
            Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell));
            
            ActualizarListaAurasQueAplica ();
            ActualizarTablaSpellStats ();
        }
    }//GEN-LAST:event_CBSpellTipoItemStateChanged

    private void BDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDescripcionActionPerformed
        String S = JOptionPane.showInputDialog("Descripcion:\n"+Myrran.getMundo().ListadeSkills().get(numSpell).getDescripcion(),Myrran.getMundo().ListadeSkills().get(numSpell).getDescripcion());
        if (S != null)
        {
            //Modifica la DESCRIPCION
            Myrran.getMundo().ListadeSkills().get(numSpell).setDescripcion(S);
            //Sincroniza el Spell Modificado con el del Player
            Myrran.getMundo().getPlayer().sincronizaSpell(Myrran.getMundo().ListadeSkills().get(numSpell));
        }
    }//GEN-LAST:event_BDescripcionActionPerformed

    private void BSpellIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSpellIconActionPerformed
        if (fSpellIconos.isVisible()) fSpellIconos.setVisible(Boolean.FALSE);
        else { fSpellIconos.setLocation(MouseInfo.getPointerInfo().getLocation()); fSpellIconos.setVisible(Boolean.TRUE);}
    }//GEN-LAST:event_BSpellIconActionPerformed

    private void BSpellSpriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSpellSpriteActionPerformed
        if (fSpellSprites.isVisible()) fSpellSprites.setVisible(Boolean.FALSE);
        else { fSpellSprites.setLocation(MouseInfo.getPointerInfo().getLocation()); fSpellSprites.setVisible(Boolean.TRUE);}
    }//GEN-LAST:event_BSpellSpriteActionPerformed

    private void BAñadirSpellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAñadirSpellActionPerformed
        SpellTemplate spell = new SpellTemplate();
        spell.nuevoSpellTemplate();
        Myrran.getMundo().ListadeSkills().add(spell);
        //Hay que hacer una copia del spell, que añadiremos a la lista de skills de player, para que ambos sean independientes
        SpellTemplate spellPlayer = new SpellTemplate();
        SpellTemplate.copiarSpell(spell, spellPlayer, true);
        Myrran.getMundo().getPlayer().ListadeSkills().add(spellPlayer);
        Myrran.getMundo().getPlayer().BarraSkills()[15]= Myrran.getMundo().getPlayer().ListadeSkills().get(Myrran.getMundo().getPlayer().ListadeSkills().size()-1).getID();
        ActualizarListaSpells();
    }//GEN-LAST:event_BAñadirSpellActionPerformed

    private void BBorrarSpellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBorrarSpellActionPerformed
        UIManager.setSDMostrarSpellDetails(false);
        if (Myrran.getMundo().ListadeSkills().isEmpty() || numSpell<0) return;
        for (int i=0; i<Myrran.getMundo().getPlayer().ListadeSkills().size();i++)
        {
            int spellID = Myrran.getMundo().ListadeSkills().get(numSpell).getID();
            if (Myrran.getMundo().getPlayer().ListadeSkills().get(i).getID() == spellID)
            { 
                Myrran.getMundo().getPlayer().ListadeSkills().remove(numSpell); 
                for (int j=0;j<Myrran.getMundo().getPlayer().BarraSkills().length;j++)
                {
                    if (Myrran.getMundo().getPlayer().BarraSkills()[j]==spellID) Myrran.getMundo().getPlayer().BarraSkills()[j]=-1;
                }
            }
        }
        if (Myrran.getMundo().ListadeSkills().contains(Myrran.getMundo().ListadeSkills().get(numSpell))) { Myrran.getMundo().ListadeSkills().remove(numSpell); }
        ActualizarListaSpells();
    }//GEN-LAST:event_BBorrarSpellActionPerformed

    private void BAñadirAuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAñadirAuraActionPerformed
        AuraTemplate aura = new AuraTemplate();
        aura.nuevaAuraTemplate();
        Myrran.getMundo().ListadeAuras().add(aura);
        String [] p = new String [Myrran.getMundo().ListadeAuras().size()];
        for (int i=0;i<Myrran.getMundo().ListadeAuras().size();i++) {p[i]=Myrran.getMundo().ListadeAuras().get(i).getNombre();}
        CBSpellAuraQueAplica.setModel(new javax.swing.DefaultComboBoxModel(p));
        ActualizarListaAuras();
    }//GEN-LAST:event_BAñadirAuraActionPerformed

    private void BBorrarAuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBorrarAuraActionPerformed
        if (Myrran.getMundo().ListadeAuras().contains(Myrran.getMundo().ListadeAuras().get(numAura))) 
        {
            int auraID = Myrran.getMundo().ListadeAuras().get(numAura).getID();
            Myrran.getMundo().ListadeAuras().remove(numAura);
            String [] p = new String [Myrran.getMundo().ListadeAuras().size()];
            for (int i=0;i<Myrran.getMundo().ListadeAuras().size();i++) {p[i]=Myrran.getMundo().ListadeAuras().get(i).getNombre();}
            CBSpellAuraQueAplica.setModel(new javax.swing.DefaultComboBoxModel(p));
            for (int i=0;i<Myrran.getMundo().ListadeSkills().size();i++)
            {
                SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(i);
                for (int j=0;j<spell.AurasQueAplica().size();j++)
                {
                    if (spell.AurasQueAplica().get(j).getID() == auraID) 
                    {
                        spell.AurasQueAplica().remove(j);
                        //Sincroniza el Spell Modificado con el del Player
                        Myrran.getMundo().getPlayer().sincronizaSpell(spell);
                    }
                }
            }
            numAura--; 
            ActualizarListaAuras();
            ActualizarListaAurasQueAplica();
            ActualizarTablaAuraStats();
            ActualizarTablaSpellStats();
            ActualizarTablaSpellStatsComparativa();
        }
    }//GEN-LAST:event_BBorrarAuraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SpellEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpellEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpellEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpellEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SpellEditor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BAñadirAura;
    private javax.swing.JButton BAñadirAuraQueAplica;
    private javax.swing.JButton BAñadirSpell;
    private javax.swing.JButton BBorrarAura;
    private javax.swing.JButton BBorrarAuraQueAplica;
    private javax.swing.JButton BBorrarSpell;
    private javax.swing.JButton BDescripcion;
    private static javax.swing.JButton BSpellIcon;
    private static javax.swing.JButton BSpellSprite;
    private static javax.swing.JComboBox CBAuraTipo;
    private static javax.swing.JComboBox CBSpellAuraQueAplica;
    private static javax.swing.JComboBox CBSpellTipo;
    private static javax.swing.JList LAuras;
    private static javax.swing.JList LSpellAurasQueAplica;
    private static javax.swing.JList LSpells;
    private javax.swing.JScrollPane SAuraStats;
    private javax.swing.JScrollPane SAuras;
    private javax.swing.JScrollPane SSpellAurasQueAplica;
    private javax.swing.JScrollPane SSpellStats;
    private javax.swing.JScrollPane SSpellStatsComparativa;
    private javax.swing.JScrollPane SSpells;
    private static javax.swing.JTable TAuraStats;
    private static javax.swing.JTextField TFAuraID;
    private static javax.swing.JTextField TFAuraNombre;
    private static javax.swing.JTextField TFSpellID;
    private static javax.swing.JTextField TFSpellNombre;
    private static javax.swing.JTable TSpellStats;
    private static javax.swing.JTable TSpellStatsComparativa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
