/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import DATA.MiscData;
import DATA.SpellData;
import Geo.Mundo;
import Graphics.Map3D;
import Main.Myrran;
import Mobiles.PC;
import Skills.AuraTemplate;
import Skills.StatSpell;
import Skills.SpellTemplate;
import WorldBuilder.WorldEditor;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Hanto
 */
public class UIManager 
{
    private static int MouseX;
    private static int MouseY;
    private static int Keycode;
    private static int MouseButton;
    public static Boolean MouseIsDragged = false;
    
    //BarraSkills 1
    private static int BS1PosX = MiscData.MAP3D_TILESIZE;
    private static int BS1PosY = MiscData.MAP3D_WINDOWVERTIZALRESOLUTION-MiscData.MAP3D_TILESIZE-20;
    private static int BS1AnchoIcono = MiscData.MAP3D_TILESIZE;
    private static int BS1AltoIcono = MiscData.MAP3D_TILESIZE;
    private static Boolean PermitirOrdenarBarraSkill = true;
    private static Boolean PermitirCambiarBindBarraSkill = false;
    private static Boolean OBS1_SpellSeleccionadoParaMover = false;
    private static int OBS1_SlotaMover;
    private static int OBS1_SpellIDaMover;
    
    //Barra de Terrenos
    private static int BTTerrenoPosX = 8;
    private static int BTTerrenoPosY = 160;
    private static int BTTerrenoInicial = 0;
    private static int BTTGrosorReborde = 1;
    private static int BTTEspacioEntreIconos = 2;
    private static int BTTIconosMaximos;
    private static Boolean MostrarBarraTiposTerreno = false;
    
    //Barra de Plantillas
    private static int BTPlantillaPosX = 8;
    private static int BTPlantillaPosY = 280;
    private static int BTPlantillaInicial = 0;
    private static int BTPGrosorReborde = 1;
    private static int BTPEspacioEntreIconos = 2;
    private static int BTPIconosMaximos;
    private static Boolean MostrarBarraTiposPlantilla = false;
    
    //Tooltip de Spells
    private static int TTSPosX = 32;
    private static int TTSPosY = 600;
    private static int TTSspellID = 0;
    private static int TTSspellID2 = -1;
    private static Boolean TTSMostrarDescripcion = true;
    private static Boolean MostrarTooltipSpell = false;
    private static List<StatSpell> QTTSdatos = new ArrayList<StatSpell>();
    
    //Detalles y personalizacion de Spells
    private static int SDPosX = 600;
    private static int SDPosY = 400;
    private static int SDspellID = -1;
    private static Boolean SDdragged = false;
    private static Boolean SDMostrarSpellDetails = false;
    private static List<StatSpell> QSDdatos = new ArrayList<StatSpell>();
    
    private static Image TalentoSI = LoadIcono(MiscData.PATH_UI_RES+"TalentoSI.png");
    private static Image TalentoNO = LoadIcono(MiscData.PATH_UI_RES+"TalentoNO.png");
    private static Image IconoSeleccionado = LoadIcono(MiscData.PATH_UI_RES+"Plantilla3x3.png");
        
    private static Boolean procesarClick = true;
    
    public static void setSDMostrarSpellDetails(Boolean b)      { SDMostrarSpellDetails = b;}
    
    public static Boolean UIMouseClick (int mouseButton, Boolean isDragged)
    {
        MouseX = MouseInputManager.MouseX; MouseY = MouseInputManager.MouseY;
        MouseButton=mouseButton; MouseIsDragged = isDragged;
        procesarClick = true;
        
        if (MostrarBarraTiposTerreno)               BarraTiposTerrenoAccionRaton();
        if (MostrarBarraTiposTerreno)               BarraTiposPlantillaAccionRaton();
        if (SDMostrarSpellDetails)                  SpellDetailsAccionRaton();
        BarraSkill1AccionRaton();
        
        return procesarClick;
    }
    
    public static void UIMouseMove ()
    {
        MouseX = MouseInputManager.MouseX; MouseY = MouseInputManager.MouseY;
        MouseButton = -1;
       
        if (SDMostrarSpellDetails)                  SpellDetailsAccionRaton();
        BarraSkill1AccionRaton();
    }
    
    public static Boolean UIKeyboard (int KeyCode)
    {
        Keycode = KeyCode;
        MouseX = MouseInputManager.MouseX; MouseY = MouseInputManager.MouseY;
        
        BarraSkill1AccionTeclado();
        
        return procesarClick;
    }
    
    
    public static void DibujarBarraSkills1 (Graphics g)
    {
        PC player = Myrran.getMundo().getPlayer();
        Graphics2D g2 = (Graphics2D) g;
        
        for (int i=0; i<player.BarraSkills().length;i++)
        {
            int X = BS1PosX+BS1AnchoIcono*i;
            int Y = BS1PosY;
            int IDSkill = player.BarraSkills()[i];
            String Keybind = Character.toString(player.BarraKeybinds()[i]).toUpperCase(); if (Keybind.equals("Ø")) Keybind = "";
            
            if (IDSkill >=0)
            {
                Image img = player.getSpell(IDSkill).getIconoIMG();
                String NombreSpell = player.getSpell(IDSkill).getNombre();
                g2.drawImage(img, BS1PosX+BS1AnchoIcono*i, BS1PosY, null);
               
                if (player.getSkillSeleccionado() == (player.BarraSkills()[i])) 
                     { printText(g2, X+BS1AnchoIcono/2, 36+Y, NombreSpell, 9, Font.PLAIN, Color.WHITE, true, 0); }
                else { printText(g2, X+BS1AnchoIcono/2, 36+Y, NombreSpell, 9, Font.PLAIN, Color.ORANGE, true, 0); }
            
                if (player.getSkillSeleccionado() == (player.BarraSkills()[i])) 
                     { printText(g2, X+29, 24+Y, Keybind, 15, Font.BOLD, Color.WHITE, true, 1); }
                else { printText(g2, X+29, 24+Y, Keybind, 15, Font.BOLD, Color.WHITE, true, 1); }
            }
            else if (OBS1_SpellSeleccionadoParaMover)
            {
                Rectangle2D r = new Rectangle2D.Double ( X+2, Y+2, BS1AnchoIcono-4, BS1AnchoIcono-4);
                g2.setColor(new Color (Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 90)); g2.fill(r);
                printText(g2, X+29, 24+Y, Keybind, 15, Font.BOLD, Color.WHITE, true, 1);
            }
        }
        if (PermitirCambiarBindBarraSkill) printText(g2, BS1PosX-5, BS1PosY+33, "R", 15, Font.BOLD, Color.RED, true, 1);
        else                               printText(g2, BS1PosX-5, BS1PosY+33, "R", 15, Font.BOLD, Color.WHITE, true, 1);
    }
       
    public static void BarraSkill1AccionTeclado()
    {  
        if ( MouseX >  BS1PosX && MouseX < BS1PosX+BS1AnchoIcono*MiscData.PC_BARRASKILLS_MaxSkills &&
             MouseY >  BS1PosY && MouseY < BS1PosY+BS1AltoIcono )
        {
            procesarClick= false;
            int SlotBarraSkill = (MouseX-BS1AnchoIcono)/BS1AnchoIcono;
            
            if (PermitirCambiarBindBarraSkill)
            {
                PC player = Myrran.getMundo().getPlayer();
                
                char KeybindOriginal = player.BarraKeybinds()[SlotBarraSkill];
                for (int i=0; i<player.BarraKeybinds().length;i++)
                {
                    if (player.BarraKeybinds()[i] == (char)Keycode) { player.BarraKeybinds()[i]=KeybindOriginal;}
                }
                player.BarraKeybinds()[SlotBarraSkill] = (char)Keycode;
            }
        }
        procesarClick= true;
    }
    
    public static void BarraSkill1AccionRaton()
    {
        if ( MouseX >  BS1PosX && MouseX < BS1PosX+BS1AnchoIcono*MiscData.PC_BARRASKILLS_MaxSkills &&
             MouseY >  BS1PosY && MouseY < BS1PosY+BS1AltoIcono )
        {
            procesarClick= false;
            int SlotBarraSkill = (MouseX-BS1AnchoIcono)/BS1AnchoIcono;
            
            //HOVER: Captura el ID del skill sobre el que estamos pasando el puntero (para el Tooltip)
            TTSspellID = Myrran.getMundo().getPlayer().BarraSkills()[SlotBarraSkill];
            MostrarTooltipSpell = true;
            
            //BUTTON1: Ordena el Skill si hacemos click y el flag se lo permite
            if (MouseButton == MouseEvent.BUTTON1 && PermitirOrdenarBarraSkill)
            {
                if (!OBS1_SpellSeleccionadoParaMover) 
                {
                    OBS1_SlotaMover=SlotBarraSkill;
                    OBS1_SpellIDaMover=TTSspellID;
                    OBS1_SpellSeleccionadoParaMover = true;
                }
                else 
                {
                    Myrran.getMundo().getPlayer().BarraSkills()[SlotBarraSkill] = OBS1_SpellIDaMover;
                    Myrran.getMundo().getPlayer().BarraSkills()[OBS1_SlotaMover] = TTSspellID;
                    
                    OBS1_SpellSeleccionadoParaMover = false;
                }
            }
            //BUTTON3: Nos Muestra los detalles del spell para personalizarlo, si apretamos el boton derecho
            if (MouseButton == MouseEvent.BUTTON3)
            {
                if (SDMostrarSpellDetails == false) { SDMostrarSpellDetails = true; }
                else if (SDspellID == TTSspellID && SDMostrarSpellDetails == true) { SDMostrarSpellDetails = false; }
                SDspellID = TTSspellID;    
            }
        }
        else { MostrarTooltipSpell = false; }
        
        //Toggle para Activar o Desactivar el Flag de Rebindear los Skills
        if ( MouseX >  BS1PosX-16 && MouseX < BS1PosX &&
             MouseY >  BS1PosY+25 && MouseY < BS1PosY+43 && MouseButton == MouseEvent.BUTTON1)
        {
            procesarClick= false;
            PermitirCambiarBindBarraSkill = !PermitirCambiarBindBarraSkill;
        }
    }
    
    public static void DibujarBarraTiposTerreno (Graphics g)
    {
        if (Myrran.getMundo().getPlayer().getSkillSeleccionado() == Mundo.getSpellID(SpellData.EDITARTERRENO_NOMBRE)) MostrarBarraTiposTerreno = true;
        else return;
        
        Graphics2D g2 = (Graphics2D) g;
         
        int Tile = MiscData.MAP3D_TILESIZE;
        int GR = BTTGrosorReborde;
        int EI = BTTEspacioEntreIconos;
        
        BTTIconosMaximos = (MiscData.MAP3D_WINDOWVERTIZALRESOLUTION-BTTerrenoPosY*2)/(Tile+GR*2+EI);
        
        for (int i=0;(i < Myrran.getMundo().ListaTerrenos().size() && i < BTTIconosMaximos);i++)
        {
            Image img = Myrran.getMundo().ListaTerrenos().get(BTTerrenoInicial+i).getIMG();
            Rectangle2D rb = new Rectangle2D.Double ( BTTerrenoPosX-GR, BTTerrenoPosY-GR+(Tile+GR*2+EI)*i, Tile+GR*2, Tile+GR*2); g2.setColor(Color.BLACK); g2.fill(rb);
            g2.drawImage ( img, BTTerrenoPosX, BTTerrenoPosY+(Tile+GR*2+EI)*i, null);
        }
        
        if ((WorldEditor.getnumTerreno()- BTTerrenoInicial) >= 0 && (WorldEditor.getnumTerreno()- BTTerrenoInicial) < BTTIconosMaximos)
        {
            int Altura = (Tile/2)-(IconoSeleccionado.getHeight(null)/2);
            g2.drawImage(IconoSeleccionado, BTTerrenoPosX+28, BTTerrenoPosY+Altura+(Tile+GR*2+EI)*(WorldEditor.getnumTerreno()- BTTerrenoInicial), null);
        }
    }
    
    public static void BarraTiposTerrenoAccionRaton ()
    {
        int Tile = MiscData.MAP3D_TILESIZE;
        int GR = BTTGrosorReborde;
        int EI = BTTEspacioEntreIconos;
        
        if (MostrarBarraTiposTerreno)
        {
            Boolean EjeX= false;
            Boolean EjeY = false;
            
            if (MouseX > BTTerrenoPosX && MouseX < (BTTerrenoPosX + Tile + GR*2) ) EjeX = true;
            if (MouseY > BTTerrenoPosY-GR && MouseY < BTTerrenoPosY+(Tile+GR*2+EI)*BTTIconosMaximos) EjeY = true;
            
            if (EjeX && EjeY)
            {
                if (Math.abs(MouseInputManager.ScrollRueda) > 0) 
                { 
                    BTTerrenoInicial = BTTerrenoInicial+MouseInputManager.ScrollRueda; 
                    if (BTTerrenoInicial < 0) BTTerrenoInicial = 0;
                    if (BTTerrenoInicial + BTTIconosMaximos >=  Myrran.getMundo().ListaTerrenos().size()) {BTTerrenoInicial = Myrran.getMundo().ListaTerrenos().size()-BTTIconosMaximos;}
                    MouseInputManager.ScrollRueda=0;
                }
                if (MouseButton>=0)
                {
                    int numTerreno = (MouseY-BTTerrenoPosY-GR)/(Tile+GR*2+EI);
                    WorldEditor.setnumTerreno(BTTerrenoInicial + numTerreno);
                    WorldEditor.setbrochaIDTerreno(BTTerrenoInicial + numTerreno);
                    WorldEditor.ActualizarDatosTerreno();
                }
                procesarClick = false;
            }
        } 
    }
    
    public static void DibujarBarraTiposPlantilla (Graphics g)
    {
        if (Myrran.getMundo().getPlayer().getSkillSeleccionado() == Mundo.getSpellID(SpellData.EDITARTERRENO_NOMBRE)) MostrarBarraTiposPlantilla = true;
        else return;
        
        Graphics2D g2 = (Graphics2D) g;
        
        int Tile = MiscData.MAP3D_TILESIZE;
        int GR = BTPGrosorReborde;
        int EI = BTPEspacioEntreIconos;
        
        int BD = MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION-(Tile+GR*2+EI)-BTPlantillaPosX;
        
        BTPIconosMaximos = (MiscData.MAP3D_WINDOWVERTIZALRESOLUTION-BTPlantillaPosY*2)/(Tile+GR*2+EI);
        
        for (int i=0;(i < Myrran.getMundo().ListaPlantillas().size() && i < BTPIconosMaximos);i++)
        {
            Image img = Myrran.getMundo().ListaPlantillas().get(BTPlantillaInicial+i).getIMGicono();
            //Reborde
            Rectangle2D rb = new Rectangle2D.Double ( BTPlantillaPosX-GR +BD, BTPlantillaPosY-GR+(Tile+GR*2+EI)*i, Tile+GR*2, Tile+GR*2);
            g2.setColor(new Color (Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 125)); g2.fill(rb);
            //TileIMG
            int CentrarIMGAncho = Tile/2-img.getWidth(null)/2;
            int CentrarIMGAlto = Tile/2-img.getHeight(null)/2;
            g2.drawImage ( img, BTPlantillaPosX + CentrarIMGAncho +BD, BTPlantillaPosY +CentrarIMGAlto +(Tile+GR*2+EI)*i, null);
        }
        
        if ((WorldEditor.getbrochaIDCursor()- BTPlantillaInicial) >= 0 && (WorldEditor.getbrochaIDCursor()- BTPlantillaInicial) < BTPIconosMaximos)
        {
            int Altura = (Tile/2)-(IconoSeleccionado.getHeight(null)/2);
            g2.drawImage(IconoSeleccionado , BTPlantillaPosX-5 +BD, BTPlantillaPosY+Altura+(Tile+GR*2+EI)*(WorldEditor.getbrochaIDCursor()- BTPlantillaInicial), null);
        }    
    }
    
    public static void BarraTiposPlantillaAccionRaton ()
    {
        int Tile = MiscData.MAP3D_TILESIZE;
        int GR = BTPGrosorReborde;
        int EI = BTPEspacioEntreIconos;
        
        int BD = MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION-(Tile+GR*2+EI)-BTPlantillaPosX;
        
        if (MostrarBarraTiposPlantilla)
        {
            Boolean EjeX= false;
            Boolean EjeY = false;
            
            if (MouseX > BTPlantillaPosX +BD && MouseX < (BTPlantillaPosX +BD + Tile + GR*2) ) EjeX = true;
            if (MouseY > BTPlantillaPosY -GR && MouseY < BTPlantillaPosY +(Tile+GR*2+EI)*BTPIconosMaximos) EjeY = true;
            
            if (EjeX && EjeY)
            {
                if (Math.abs(MouseInputManager.ScrollRueda) > 0) 
                { 
                    BTPlantillaInicial = BTPlantillaInicial+MouseInputManager.ScrollRueda; 
                    if (BTPlantillaInicial < 0) BTPlantillaInicial = 0;
                    if (BTPlantillaInicial + BTPIconosMaximos >=  Myrran.getMundo().ListaPlantillas().size()) {BTPlantillaInicial = Myrran.getMundo().ListaPlantillas().size()-BTPIconosMaximos;}
                    MouseInputManager.ScrollRueda=0;
                }
                if (MouseButton>=0)
                {
                    int numPlantilla = (MouseY-BTPlantillaPosY-GR)/(Tile+GR*2+EI);
                    WorldEditor.setbrochaIDCursor(BTPlantillaInicial + numPlantilla);
                    Map3D.cursor.setForma(Myrran.getMundo().ListaPlantillas().get(numPlantilla).getForma());
                    Map3D.cursor.setPuntero(Myrran.getMundo().ListaPlantillas().get(numPlantilla).getX(), Myrran.getMundo().ListaPlantillas().get(numPlantilla).getY());
                    WorldEditor.ActualizarDatosTerreno();
                }
                procesarClick = false;
            }
        } 
    }
    
    public static void SpellDetailsAccionRaton()
    {
        Boolean EjeX= false;
        Boolean EjeY = false;
        //Añadir o quitar Talentos Spell
        if (MouseX > SDPosX+95 && MouseX < SDPosX+155) EjeX = true;
        if (MouseY > SDPosY && MouseY < SDPosY+6+QSDdatos.size()*10) EjeY = true;
        if (EjeX && EjeY && MouseButton == MouseEvent.BUTTON1)
        {
            int Campo = (MouseY-SDPosY-6)/10;
            if ((MouseX - SDPosX-95-(155-95)/2)>0) 
            {
                if (QSDdatos.get(Campo).TalentoActual<=QSDdatos.get(Campo).TalentoMaximo)
                    QSDdatos.get(Campo).setTalentoActual(QSDdatos.get(Campo).TalentoActual+1);
            }
            else 
            {   if (QSDdatos.get(Campo).TalentoActual>0)
                QSDdatos.get(Campo).setTalentoActual(QSDdatos.get(Campo).TalentoActual-1);
            }
            procesarClick= false;
        }
        //Mover Spell Details
        if (MouseX > SDPosX && MouseX <SDPosX+32 && MouseY > SDPosY-32 && MouseY < SDPosY)
        {
            if (MouseIsDragged) { SDdragged = true; }
            else if (MouseButton == MouseEvent.BUTTON3) { SDMostrarSpellDetails = false; }
            procesarClick = false;
        }
        if (!MouseIsDragged) { SDdragged = false; }
    }
        
    public static List<StatSpell> GetSpellDetailsData (int SpellID)
    {   
        List<StatSpell> QDatos = new ArrayList<StatSpell>();
        
        SpellTemplate spell = Myrran.getMundo().getPlayer().getSpell(SpellID);
        
        if (spell.Stats().length>=2) 
        {
            QDatos.add(spell.Stats()[1]); QDatos.add(spell.Stats()[0]);
            for (int i=2; i<spell.Stats().length;i++)   { QDatos.add(spell.Stats()[i]); }
        }
        else { for (int i=0; i<spell.Stats().length;i++)   { QDatos.add(spell.Stats()[i]); } }
        
        for (int i = 0; i<spell.AurasQueAplica().size();i++)
        {
            AuraTemplate aura = spell.AurasQueAplica().get(i);
            
            for (int j=0; j<aura.Stats().length;j++) { QDatos.add(aura.Stats()[j]); }
        }
        return QDatos;
    }  
    
    public static void UIDibujarSpellTooltip (Graphics g)
    {
        if (MostrarTooltipSpell == false || TTSspellID <0) return; 
        if (TTSspellID2 != TTSspellID) { QTTSdatos = GetSpellDetailsData(TTSspellID);}
        
        TTSspellID2 = TTSspellID;
        SpellTemplate spell = Myrran.getMundo().getPlayer().getSpell(TTSspellID);
        Graphics2D g2 = (Graphics2D) g;
        
        String Descripcion;
        if (TTSMostrarDescripcion) Descripcion = spell.getDescripcion(); else Descripcion = "";
        
        g2.setFont(new Font("Arial", Font.PLAIN, 9));
        FontMetrics fm = g2.getFontMetrics();
        List<String> DescripcionList;
        
        if (Descripcion != null) DescripcionList = Utils.StringUtils.wrap(Descripcion, fm, 185);
        else DescripcionList = new ArrayList <String>();
        
        int numLineas = QTTSdatos.size() + DescripcionList.size();
        
        TTSPosX = MouseInputManager.MouseX;
        TTSPosY = MouseInputManager.MouseY-numLineas*10-32;
        
        //Recuadro Tooltip
        Rectangle2D rb = new Rectangle2D.Double ( TTSPosX, TTSPosY, 192, 7+numLineas*10);
        g2.setColor(new Color (Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 185)); g2.fill(rb);
        
        //Icono Tooltip
        g2.drawImage ( spell.getIconoIMG(), TTSPosX, TTSPosY-32, null);
        printText (g2, TTSPosX+2, TTSPosY-2, spell.getNombre(), 11, Font.BOLD ,Color.WHITE, true, 2);
        
        //Datos del Spell
        for (int i=0; i<QTTSdatos.size(); i++)
        {
            String NombreCampo = QTTSdatos.get(i).Nombre;
            String CosteTalento = Integer.toString(QTTSdatos.get(i).CosteTalento);
            String Valor; String ValorTotal; String BonoTalento;
            if (QTTSdatos.get(i).ValorBase > 500) { Valor = Double.toString(QTTSdatos.get(i).ValorBase/1000); ValorTotal = Double.toString(QTTSdatos.get(i).getValorTotal()/1000); BonoTalento = Double.toString(QTTSdatos.get(i).BonoTalento/1000);}
            else { Valor = Double.toString(QTTSdatos.get(i).ValorBase); ValorTotal = Double.toString(QTTSdatos.get(i).getValorTotal()); BonoTalento = Double.toString(QTTSdatos.get(i).BonoTalento);}
            
            int numTActual =  QTTSdatos.get(i).TalentoActual;
            int numTMax = QTTSdatos.get(i).TalentoMaximo;
            
            printText (g2, TTSPosX+4, TTSPosY+9+10*i, NombreCampo, 9, Font.BOLD, Color.ORANGE, false, 2);
            printText (g2, TTSPosX+92, TTSPosY+9+10*i, Valor, 9, Font.BOLD ,Color.WHITE, false, 1);
            printText (g2, TTSPosX+188, TTSPosY+9+10*i, ValorTotal, 9, Font.PLAIN ,Color.GREEN, false, 1);
            
            if (numTMax > 0)
            {
                printText (g2, TTSPosX+200, TTSPosY+9+10*i, CosteTalento, 9, Font.PLAIN ,Color.YELLOW, true, 1); 
                printText (g2, TTSPosX+205, TTSPosY+9+10*i, BonoTalento, 9, Font.PLAIN ,Color.YELLOW, true, 2); 
                
                for (int j=numTActual; j<numTMax && j<= 20;j++) { g2.drawImage (TalentoNO, TTSPosX+95 +j*3, TTSPosY+9+10*i -3, null); }
                for (int j=0; j<numTActual && j<= 20;j++) { g2.drawImage (TalentoSI, TTSPosX+95 +j*3, TTSPosY+9+10*i -3, null); }
            }          
        }
        //Descripcion del Spell
        for (int i=0; i<DescripcionList.size();i++)
        {
            printText (g2, TTSPosX+4, TTSPosY+9+10*QTTSdatos.size()+i*10, DescripcionList.get(i), 9, Font.PLAIN, Color.GRAY, false, 2);
        }    
    }   
    
    public static void UIDibujarSpellDetails (Graphics g)
    {
        if (SDMostrarSpellDetails == false || SDspellID <0) return; 
        QSDdatos = GetSpellDetailsData(SDspellID);
        
        SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(SDspellID);
        Graphics2D g2 = (Graphics2D) g;
        
        String Descripcion;
        if (TTSMostrarDescripcion) Descripcion = spell.getDescripcion(); else Descripcion = "";
        
        g2.setFont(new Font("Arial", Font.PLAIN, 9));
        FontMetrics fm = g2.getFontMetrics();
        List<String> DescripcionList;
        
        if (Descripcion != null) DescripcionList = Utils.StringUtils.wrap(Descripcion, fm, 185);
        else DescripcionList = new ArrayList <String>();
        
        int numLineas = QSDdatos.size() + DescripcionList.size();
        
        if (SDdragged) { SDPosX = MouseX-16; SDPosY = MouseY+16; }
        
        //Recuadro Tooltip
        Rectangle2D rb = new Rectangle2D.Double ( SDPosX, SDPosY, 192, 7+numLineas*10);
        g2.setColor(new Color (Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 185)); g2.fill(rb);
        
        //Icono Tooltip
        g2.drawImage ( Myrran.getMundo().ListadeSkills().get(SDspellID).getIconoIMG(), SDPosX, SDPosY-32, null);
        printText (g2, SDPosX+2, SDPosY-2, Myrran.getMundo().ListadeSkills().get(SDspellID).getNombre(), 11, Font.BOLD ,Color.WHITE, true, 2);
        
        //Datos del Spell
        for (int i=0; i<QSDdatos.size(); i++)
        {
            String NombreCampo = QSDdatos.get(i).Nombre;
            String CosteTalento = Integer.toString(QSDdatos.get(i).CosteTalento);
            String Valor; String ValorTotal; String BonoTalento;
            if (QSDdatos.get(i).ValorBase > 500) { Valor = Double.toString(QSDdatos.get(i).ValorBase/1000); ValorTotal = Double.toString(QSDdatos.get(i).getValorTotal()/1000); BonoTalento = Double.toString(QSDdatos.get(i).BonoTalento/1000);}
            else { Valor = Double.toString(QSDdatos.get(i).ValorBase); ValorTotal = Double.toString(QSDdatos.get(i).getValorTotal()); BonoTalento = Double.toString(QSDdatos.get(i).BonoTalento);}  
            
            int numTActual =  QSDdatos.get(i).TalentoActual;
            int numTMax = QSDdatos.get(i).TalentoMaximo;
            
            printText (g2, SDPosX+4, SDPosY+9+10*i, NombreCampo, 9, Font.BOLD, Color.ORANGE, false, 2);
            printText (g2, SDPosX+92, SDPosY+9+10*i, Valor, 9, Font.BOLD ,Color.WHITE, false, 1);
            printText (g2, SDPosX+188, SDPosY+9+10*i, ValorTotal, 9, Font.PLAIN ,Color.GREEN, false, 1);
            
            if (numTMax > 0)
            {
                printText (g2, SDPosX+200, SDPosY+9+10*i, CosteTalento, 9, Font.PLAIN ,Color.YELLOW, true, 1); 
                printText (g2, SDPosX+205, SDPosY+9+10*i, BonoTalento, 9, Font.PLAIN ,Color.YELLOW, true, 2); 
                
                for (int j=numTActual; j<numTMax && j<= 20;j++) { g2.drawImage (TalentoNO, SDPosX+95 +j*3, SDPosY+9+10*i -3, null); }
                for (int j=0; j<numTActual && j<= 20;j++) { g2.drawImage (TalentoSI, SDPosX+95 +j*3, SDPosY+9+10*i -3, null); }
            }   
        }  
        //Descripcion del Spell
        for (int j=0; j<DescripcionList.size();j++)
        {
            printText (g2, SDPosX+4, SDPosY+9+10*QSDdatos.size()+j*10, DescripcionList.get(j), 9, Font.PLAIN, Color.GRAY, false, 2);
        }
    }
    
    
    public static void printTextBorder (Graphics g, String string, int x, int y, Color interior, Boolean reborde)
    {
        if (reborde)
        {
            g.setColor(Color.BLACK);
            g.drawString(string, x+1, y-1);
            g.drawString(string, x+1, y+1);
            g.drawString(string, x-1, y-1);
            g.drawString(string, x-1, y+1);
            g.drawString(string, x,   y-1);
            g.drawString(string, x,   y+1);
            g.drawString(string, x-1,   y);
            g.drawString(string, x+1,   y);
        }
        g.setColor(interior);
        g.drawString(string, x, y);
    }
    
    public static int printText (Graphics g, int x, int y, String string, int size, int GrosorFuente, Color color, Boolean reborde, int alineacion)
    {
        g.setFont(new Font("Arial", GrosorFuente, size));
        FontMetrics fm = g.getFontMetrics();
        int msgWidth = fm.stringWidth(string);  //nos da el ancho del texto
        int msgAscent = fm.getAscent();         //nos da el alto del texto
        
        if (alineacion == 0) {
        x = x -msgWidth /2;                     //de esta forma podemos centrar el texto justo en el centro
        y = y +msgAscent /2;}
        if (alineacion == 1) {
        x = x -msgWidth;                        //de esta forma podemos centrar el texto justo en el centro
        y = y +msgAscent /2;}
        if (alineacion == 2) {                 //de esta forma podemos centrar el texto justo en el centro
        y = y +msgAscent /2;}
        
        float alpha;
        
        Color cinterior = new Color (color.getRed(),color.getGreen(),color.getBlue(),255);
        printTextBorder (g, string, x, y, cinterior, reborde);
        
        return msgWidth;
    }
    
    private static int findMax(int... vals) 
    {
        int max = Integer.MIN_VALUE;
        for (int d : vals) { if (d > max) max = d; }
        return max;
    }
    
    public static Image LoadIcono (String imgFilename)
    {
        URL url = UIManager.class.getClassLoader().getResource(imgFilename);
        if (url == null) {System.out.println (imgFilename+" del Icono UI no se encuentra.");}
        else 
        {       
            try { return ImageIO.read(url);}
            catch (Exception e) {}
        }
        return null;
    }
}
