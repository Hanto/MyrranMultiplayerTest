/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Mobiles.NPC;
import Mobiles.PC;
import DATA.MiscData;
import Main.Myrran;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Hanto
 */

public class Nameplate 
{
    private static int NameplatesPlayer_AlturaRespectoCabeza = MiscData.NAMEPLATES_PLAYER_ALTURARESPECTOCABEZA;
    private static int NameplatesEnemigos_AlturaRespectoCabeza = MiscData.NAMEPLATES_ENEMIGOS_ALTURARESPECTOCABEZA; 
    
    private static int NameplatesPlayer_GrosorReborde = MiscData.NAMEPLATES_PLAYER_GROSORREBORDE;
    private static int NameplatesEnemigos_GrosorReborde = MiscData.NAMEPLATES_ENEMIGOS_GROSORREBORDE;
    
    private static int BarradeVidaPlayer_Grosor = MiscData.NAMEPLATES_PLAYER_BARRADEVIDAGROSOR;
    private static int BarradeVidaEnemigos_Grosor = MiscData.NAMEPLATES_ENEMIGOS_BARRADEVIDAGROSOR;
    private static int BarradeCasteoPlayer_Grosor = MiscData.NAMEPLATES_PLAYER_BARRADECASTEOGROSOR;
    private static int BarradeCasteoEnemigos_Grosor = MiscData.NAMEPLATES_ENEMIGOS_BARRADECASTEOGROSOR;
    
    private static Color BarradeVidaPlayer_ColorBase = MiscData.NAMEPLATE_BARRADEVIDA_PLAYER_COLORBASE;
    private static Color BarradeVidaPlayer_ColorFinal = MiscData.NAMEPLATE_BARRADEVIDA_PLAYER_COLORFINAL;
    private static Color BarradeVidaEnemigos_ColorBase = MiscData.NAMEPLATE_BARRADEVIDA_ENEMIGO_COLORBASE;
    private static Color BarradeVidaEnemigos_ColorFinal = MiscData.NAMEPLATE_BARRADEVIDA_ENEMIGO_COLORFINAL;
    
    private static Color BarradeCasteoPlayer_ColorBase = MiscData.NAMEPLATE_BARRADECASTEO_PLAYER_COLORBASE;
    private static Color BarradeCasteoPlayer_ColorFinal = MiscData.NAMEPLATE_BARRADECASTEO_PLAYER_COLORFINAL;
    private static Color BarradeCasteoEnemigos_ColorBase = MiscData.NAMEPLATE_BARRADECASTEO_ENEMIGO_COLORBASE;
    private static Color BarradeCasteoEnemigos_ColorFinal = MiscData.NAMEPLATE_BARRADECASTEO_ENEMIGO_COLORFINAL;
    
    private static Boolean isDegradadoVidaPlayer;
    private static Boolean isDegradadoVidaEnemigos;
    private static Boolean isDegradadoCasteoPlayer;
    private static Boolean isDegradadoCasteoEnemigos;
    
    private static int CalidadDegradadoVidaPlayer;
    private static int CalidadDegradadoVidaEnemigos;
    private static int CalidadDegradadoCasteoPlayer;
    private static int CalidadDegradadoCasteoEnemigos;
    
    private static Boolean verNameplatePlayer;
    private static Boolean verNameplateEnemigos;
    private static Boolean verNameplateCasteoPlayer;
    private static Boolean verNameplateCasteoEnemigos;
    
    private static Color colorRebordeNameplatevidaPlayer;
    private static Color colorRebordeNameplateVidaEnemigos;
    private static Color colorRebordeNameplateCasteoPlayer;
    private static Color colorRebordeNameplateCasteoEnemigos;
        
    private static int G;
    private static int GR; 
    private static int ARC;                         //ATENCION: el grosor positivo hace que reste, o sea que que baje hacia abajo la barra
        
    private static int ColorBaseR;
    private static int ColorBaseG;
    private static int ColorBaseB;
    
    private static int ColorFinalR;
    private static int ColorFinalG;
    private static int ColorFinalB;
    
    
    public static Boolean setMedidasBarrasdeVida (int TipoChar)
    {
        switch (TipoChar)
        {
            case (MiscData.NAMEPLATES_TIPO_ENEMIGO):
            {
                G = BarradeVidaEnemigos_Grosor;
                GR = NameplatesEnemigos_GrosorReborde;
                ARC = NameplatesEnemigos_AlturaRespectoCabeza+BarradeVidaEnemigos_Grosor+BarradeCasteoEnemigos_Grosor+NameplatesEnemigos_GrosorReborde; //ATENCION: el grosor positivo hace que reste, o sea que que baje hacia abajo la barra
        
                ColorBaseR = BarradeVidaEnemigos_ColorBase.getRed(); ColorBaseG = BarradeVidaEnemigos_ColorBase.getGreen(); ColorBaseB = BarradeVidaEnemigos_ColorBase.getBlue();
                ColorFinalR = BarradeVidaEnemigos_ColorFinal.getRed(); ColorFinalG = BarradeVidaEnemigos_ColorFinal.getGreen(); ColorFinalB = BarradeVidaEnemigos_ColorFinal.getBlue();
                return true;
            }
            case (MiscData.NAMEPLATES_TIPO_PLAYER):
            {
                G = BarradeVidaPlayer_Grosor;
                GR = NameplatesPlayer_GrosorReborde;
                ARC = NameplatesPlayer_AlturaRespectoCabeza+BarradeCasteoPlayer_Grosor+BarradeVidaPlayer_Grosor+NameplatesPlayer_GrosorReborde; //ATENCION: el grosor positivo hace que reste, o sea que que baje hacia abajo la barra
                
                ColorBaseR = BarradeVidaPlayer_ColorBase.getRed(); ColorBaseG = BarradeVidaPlayer_ColorBase.getGreen(); ColorBaseB = BarradeVidaPlayer_ColorBase.getBlue();
                ColorFinalR = BarradeVidaPlayer_ColorFinal.getRed(); ColorFinalG = BarradeVidaPlayer_ColorFinal.getGreen(); ColorFinalB = BarradeVidaPlayer_ColorFinal.getBlue();
                return true;
            }
        }
        return false;
    }
    
    public static Boolean setMedidasBarrasdeCasteo (int TipoChar)
    {
        switch (TipoChar)
        {
            case (MiscData.NAMEPLATES_TIPO_ENEMIGO):
            {
                G = BarradeCasteoPlayer_Grosor;
                GR = NameplatesPlayer_GrosorReborde;
                ARC = NameplatesPlayer_AlturaRespectoCabeza+BarradeCasteoPlayer_Grosor;
        
                ColorBaseR = BarradeCasteoEnemigos_ColorBase.getRed(); ColorBaseG = BarradeCasteoEnemigos_ColorBase.getGreen(); ColorBaseB = BarradeCasteoEnemigos_ColorBase.getBlue(); 
                ColorFinalR = BarradeCasteoEnemigos_ColorFinal.getRed(); ColorFinalG = BarradeCasteoEnemigos_ColorFinal.getGreen(); ColorFinalB = BarradeCasteoEnemigos_ColorFinal.getBlue();
                return true;
            }
            case (MiscData.NAMEPLATES_TIPO_PLAYER):
            {
                G = BarradeCasteoEnemigos_Grosor;
                GR = NameplatesEnemigos_GrosorReborde;
                ARC = NameplatesEnemigos_AlturaRespectoCabeza+BarradeCasteoEnemigos_Grosor;
                
                ColorBaseR = BarradeCasteoPlayer_ColorBase.getRed(); ColorBaseG = BarradeCasteoPlayer_ColorBase.getGreen(); ColorBaseB = BarradeCasteoPlayer_ColorBase.getBlue();
                ColorFinalR = BarradeCasteoPlayer_ColorFinal.getRed(); ColorFinalG = BarradeCasteoPlayer_ColorFinal.getGreen(); ColorFinalB = BarradeCasteoPlayer_ColorFinal.getBlue();
                return true;
            }
        }
        return false;
    } 
    
    public static Boolean setMedidasNameplate(int TipoBarra, int TipoChar)
    {
        switch (TipoBarra)
        {
            case (MiscData.NAMEPLATES_TIPO_BARRAVIDA):      { if (setMedidasBarrasdeVida(TipoChar)) return true; else return false; }
            case (MiscData.NAMEPLATES_TIPO_BARRACASTEO):    { if (setMedidasBarrasdeCasteo(TipoChar)) return true; else return false; }
        }
        return false;
    } 
    
    public static void DibujarNameplate (Graphics g, int TipoChar, int TipoBarra, int charX, int charY,  int Actual, int Total, Boolean isActiva, Boolean Degradado, int CalidadDegradado)
    {
        Graphics2D g2 = (Graphics2D) g;
        if (!setMedidasNameplate(TipoBarra, TipoChar)) return;
        if (!isActiva) return;
        
        charX = charX-Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
        charY = charY-Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();
        
        int Tile = MiscData.MAP3D_TILESIZE;
        int numPixeles = Tile/CalidadDegradado;                             //CALIDAD DEGRADADO Tiene que ser Multiplos de 2 (1,2,4,8,16,32) (ya que la barra de vida ocupa 32 pixeles, y la calidad de degradado define el numero de pixeles que ocupa cada punto de color)
        int incColorR = (ColorFinalR-ColorBaseR)/numPixeles;                //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
        int incColorG = (ColorFinalG-ColorBaseG)/numPixeles;                //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
        int incColorB = (ColorFinalB-ColorBaseB)/numPixeles;
        
        if (Degradado==true)
        {    
            g2.setColor(Color.BLACK);                                                               //X-1 (-1 un pixel de reborde negro a la izda)
            Rectangle2D.Double s = new Rectangle2D.Double(charX-GR,charY-GR-ARC,Tile+GR*2,G+GR*2);  //Y+1 un pixel de reborde negro arriba, a 8 pixeles encima de la cabeza del char y 3 debajo de la barra de vida
            g2.fill(s);                                                                             //TileSize Pixeles de ancho +2, +1 por cada lado, y 5 pixeles de alto (el Alto de la barra mas 1 pixel de reborde por arriba y por abajo, +2)                                                   
            for (int i=numPixeles;i>=0;i--)
            {
                g2.setColor(new Color (ColorBaseR+i*incColorR,ColorBaseG+i*incColorG,ColorBaseB+i*incColorB));
                Rectangle2D.Double r = new Rectangle2D.Double(charX,charY-ARC,CalidadDegradado*i,G);
                g2.fill(r);
            }
            g2.setColor(Color.BLACK);
            float percent = ((float)Actual/(float)Total)*Tile;
            float size = ((float)Total-(float)Actual)/(float)Total*Tile;if (percent<=0) {percent=0;size=Tile;}
            Rectangle2D.Double r = new Rectangle2D.Double(charX+percent,charY-ARC,size,G);
            g2.fill(r);
        }
        else
        {
            g2.setColor(Color.BLACK);
            Rectangle2D.Double s = new Rectangle2D.Double(charX-1,charY-1-ARC,Tile+2,G+2);
            g2.fill(s);
            g2.setColor(Color.BLACK);
            Rectangle2D.Double r = new Rectangle2D.Double(charX,charY-ARC,Tile,G);
            g2.fill(r);
            g2.setColor(new Color (ColorFinalR, ColorFinalG, ColorFinalB));
            float percent = ((float)Actual/(float)Total)*Tile;if (percent>Tile) {percent=Tile;}
            //float CastSize = ((float)TotalCastingTime-(float)CastedTime)/(float)TotalCastingTime*TileSize;if (CastPercent<=0) {CastPercent=0;CastSize=TileSize;}
            r = new Rectangle2D.Double(charX,charY-ARC,percent,G);
            g2.fill(r);
        }   
    }
    
    
    
    /*
     * 
     *  CODIGO ANTIGO DE DIBUJAR BARRAS (Antes de existir el Organizador del Plano-Z)
     * 
     * 
    public static void DibujarBarrasdeVidaPlayers(Graphics g, Boolean Degradado, int CalidadDegradado)
    {
        for (int i=0;i<Myrran.getMundo().ListadePlayers().size();i++)
        {
            Graphics2D g2 = (Graphics2D) g;
            PC player = Myrran.getMundo().ListadePlayers().get(i);
            int Tile = MiscData.MAP3D_TILESIZE;
            int G = BarradeVidaPlayer_Grosor;
            int GR = NameplatesPlayer_GrosorReborde;
            int ARC = NameplatesPlayer_AlturaRespectoCabeza+BarradeCasteoPlayer_Grosor+BarradeVidaPlayer_Grosor+NameplatesPlayer_GrosorReborde; //ATENCION: el grosor positivo hace que reste, o sea que que baje hacia abajo la barra
            
            
            int X = Myrran.getMap3D().getPlayer().getX()-Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
            int Y = Myrran.getMap3D().getPlayer().getY()-Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();
            
            if (Degradado==true)
            {   
                int numPixeles = Tile/CalidadDegradado; //CALIDAD DEGRADADO Tiene que ser Multiplos de 2 (1,2,4,8,16,32) (ya que la barra de vida ocupa 32 pixeles, y la calidad de degradado define el numero de pixeles que ocupa cada punto de color)
                int incColorR = 60/numPixeles;          //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
                int incColorG = 100/numPixeles;
                
                g2.setColor(Color.BLACK);
                Rectangle2D.Double s = new Rectangle2D.Double(X-GR,Y-GR-ARC,Tile+GR*2,G+GR*2);
                g2.fill(s);
                for (int j=numPixeles;j>=0;j--)
                {
                    g2.setColor(new Color (190+j*incColorR,123+j*incColorG,0));
                    Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,CalidadDegradado*j,G);
                    g2.fill(r);
                }
                g2.setColor(Color.BLACK);
                float HPpercent = ((float)player.getActualHPs()/(float)player.getTotalHps())*Tile;
                float HPSize = ((float)player.getTotalHps()-(float)player.getActualHPs())/(float)player.getTotalHps()*Tile;if (HPpercent<=0) {HPpercent=0;HPSize=Tile;}
                Rectangle2D.Double r = new Rectangle2D.Double(X+HPpercent,Y-ARC,HPSize,G);
                g2.fill(r);
            }
            else
            {
                g2.setColor(Color.BLACK);
                Rectangle2D.Double s = new Rectangle2D.Double(X-1,Y-1-ARC,Tile+2,G+2);
                g2.fill(s);
                g2.setColor(Color.BLACK);
                Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,Tile,G);
                g2.fill(r);
                g2.setColor(Color.ORANGE);
                float HPpercent = ((float)player.getActualHPs()/(float)player.getTotalHps())*Tile;
                r = new Rectangle2D.Double(X,Y-ARC,HPpercent,G);
                g2.fill(r);
            }    
        }
    }
    
    public static void DibujarBarrasdeCasteoPlayer (Graphics g, Boolean Degradado, int CalidadDegradado)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        if (!Myrran.getMap3D().getPlayer().isCasteandoSpell()) return;
        int X = Myrran.getMap3D().getPlayer().getX() - Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
        int Y = Myrran.getMap3D().getPlayer().getY() - Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();    
        int CastedTime = Myrran.getMap3D().getPlayer().getActualCastedTime();
        int TotalCastingTime = Myrran.getMap3D().getPlayer().getTotalCastingTime();
        
        int G = BarradeCasteoPlayer_Grosor;
        int GR = NameplatesPlayer_GrosorReborde;
        int ARC = NameplatesPlayer_AlturaRespectoCabeza+BarradeCasteoPlayer_Grosor;
        int Tile = MiscData.MAP3D_TILESIZE;
        
        if (Degradado==true)
        {   
            
            int numPixeles = Tile/CalidadDegradado; //CALIDAD DEGRADADO Tiene que ser Multiplos de 2 (1,2,4,8,16,32) (ya que la barra de vida ocupa 32 pixeles, y la calidad de degradado define el numero de pixeles que ocupa cada punto de color) 
            int incColorG = 100/numPixeles;//Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
            int incColorB = 60/numPixeles;
            
            g2.setColor(Color.BLACK);                                                               //X-1 (-1 un pixel de reborde negro a la izda)
            Rectangle2D.Double s = new Rectangle2D.Double(X-GR,Y-GR-ARC,Tile+GR*2,G+GR*2);                  //Y+1 un pixel de reborde negro arriba, a 8 pixeles encima de la cabeza del char y 3 debajo de la barra de vida
            g2.fill(s);                                                                             //TileSize Pixeles de ancho +2, +1 por cada lado, y 5 pixeles de alto (el Alto de la barra mas 1 pixel de reborde por arriba y por abajo, +2)                                                   
            for (int j=numPixeles;j>=0;j--)
            {
                g2.setColor(new Color (0,133+j*incColorG,100+j*incColorB));
                Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,CalidadDegradado*j,G);
                g2.fill(r);
            }
            g2.setColor(Color.BLACK);
            float CastPercent = ((float)CastedTime/(float)TotalCastingTime)*Tile;
            float CastSize = ((float)TotalCastingTime-(float)CastedTime)/(float)TotalCastingTime*Tile;if (CastPercent<=0) {CastPercent=0;CastSize=Tile;}
            Rectangle2D.Double r = new Rectangle2D.Double(X+CastPercent,Y-ARC,CastSize,G);
            g2.fill(r);
        }
        else
        {
            g2.setColor(Color.BLACK);
            Rectangle2D.Double s = new Rectangle2D.Double(X-1,Y-1-ARC,Tile+2,G+2);
            g2.fill(s);
            g2.setColor(Color.BLACK);
            Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,Tile,G);
            g2.fill(r);
            g2.setColor(new Color (0,183,170));
            float CastPercent = ((float)CastedTime/(float)TotalCastingTime)*Tile;
            //float CastSize = ((float)TotalCastingTime-(float)CastedTime)/(float)TotalCastingTime*TileSize;if (CastPercent<=0) {CastPercent=0;CastSize=TileSize;}
            r = new Rectangle2D.Double(X,Y-ARC,CastPercent,G);
            g2.fill(r);
        }
        
    }
    
    public static void DibujarBarrasdeVidaEnemigos (Graphics g, Boolean Degradado, int CalidadDegradado)
    {
        for (int i=0;i<Myrran.getMundo().ListadeMobsCercanos().size();i++)
        {   
            Graphics2D g2 = (Graphics2D) g;
            NPC mob = Myrran.getMundo().ListadeMobsCercanos().get(i);
            int Tile = MiscData.MAP3D_TILESIZE;
            int G = BarradeVidaEnemigos_Grosor;
            int GR = NameplatesEnemigos_GrosorReborde;
            int ARC = NameplatesEnemigos_AlturaRespectoCabeza+BarradeVidaEnemigos_Grosor+BarradeCasteoEnemigos_Grosor+NameplatesEnemigos_GrosorReborde; //ATENCION: el grosor positivo hace que reste, o sea que que baje hacia abajo la barra
            int X = mob.getX()-Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
            int Y = mob.getY()-Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();
          
            if (Degradado == true)
            {  
                int numPixeles = Tile/CalidadDegradado; //CALIDAD DEGRADADO Tiene que ser Multiplos de 2 (1,2,4,8,16,32) (ya que la barra de vida ocupa 32 pixeles, y la calidad de degradado define el numero de pixeles que ocupa cada punto de color)
                int incColor = 120/numPixeles; //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
                
                g2.setColor(Color.BLACK);
                Rectangle2D.Double s = new Rectangle2D.Double(X-GR,Y-GR-ARC,Tile+GR*2,G+GR*2);
                g2.fill(s);
                for (int j=numPixeles;j>=0;j--)
                {   
                    g2.setColor(new Color (133+j*incColor,0,0));
                    Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,CalidadDegradado*j,G);
                    g2.fill(r);
                }
                g2.setColor(Color.BLACK);
                float HPpercent = ((float)mob.getActualHPs()/(float)mob.getTotalHps())*Tile;
                float HPSize = ((float)mob.getTotalHps()-(float)mob.getActualHPs())/(float)mob.getTotalHps()*Tile;if (HPpercent<=0) {HPpercent=0;HPSize=Tile;}
                Rectangle2D.Double r = new Rectangle2D.Double(X+HPpercent,Y-ARC,HPSize,G);
                g2.fill(r);
            }
            else
            {
                g2.setColor(Color.BLACK);
                Rectangle2D.Double s = new Rectangle2D.Double(X-1,Y-1-ARC,Tile+2,G+2);
                g2.fill(s);
                g2.setColor(Color.BLACK);
                Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,Tile,G);
                g2.fill(r);
                g2.setColor(Color.RED);
                float HPpercent = ((float)mob.getActualHPs()/(float)mob.getTotalHps())*Tile;
                r = new Rectangle2D.Double(X,Y-ARC,HPpercent,G);
                g2.fill(r);
            }    
        }
    }        
    
    public static void DibujarBarrasdeCasteoEnemigos (Graphics g, Boolean Degradado, int CalidadDegradado)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        for (int i=0;i<Myrran.getMundo().ListadeMobsCercanos().size();i++)
        {
            NPC mob = Myrran.getMundo().ListadeMobsCercanos().get(i);
            if (!mob.isCasteandoSpell()) break;
            int X = mob.getX() - Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
            int Y = mob.getY() - Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();    
            int CastedTime = mob.getActualCastedTime();
            int TotalCastingTime = mob.getTotalCastingTime();
            int G = BarradeCasteoEnemigos_Grosor;
            int GR = NameplatesEnemigos_GrosorReborde;
            int ARC = NameplatesEnemigos_AlturaRespectoCabeza+BarradeCasteoEnemigos_Grosor;
            int Tile = MiscData.MAP3D_TILESIZE;
            
            if (Degradado==true)
            {   
                int numPixeles = Tile/CalidadDegradado; //CALIDAD DEGRADADO Tiene que ser Multiplos de 2 (1,2,4,8,16,32) (ya que la barra de vida ocupa 32 pixeles, y la calidad de degradado define el numero de pixeles que ocupa cada punto de color) 
                int incColorG = 100/numPixeles;//Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
                int incColorB = 60/numPixeles;
            
                g2.setColor(Color.BLACK);                                                               //X-1 (-1 un pixel de reborde negro a la izda)
                Rectangle2D.Double s = new Rectangle2D.Double(X-GR,Y-GR-ARC,Tile+GR*2,G+GR*2);          //Y+1 un pixel de reborde negro arriba, a 7 pixeles encima de la cabeza del char y 3 debajo de la barra de vida (la barra de player esta a 8,la de enemigos esta mas cercana)
                g2.fill(s);                                                                             //TileSize Pixeles de ancho +2, +1 por cada lado, y 5 pixeles de alto (el Alto de la barra mas 1 pixel de reborde por arriba y por abajo, +2)                                                   
                for (int j=numPixeles;j>=0;j--)
                {
                    g2.setColor(new Color (0,133+j*incColorG,100+j*incColorB));
                    Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,CalidadDegradado*j,G);
                    g2.fill(r);
                }
                g2.setColor(Color.BLACK);
                float CastPercent = ((float)CastedTime/(float)TotalCastingTime)*Tile;
                float CastSize = ((float)TotalCastingTime-(float)CastedTime)/(float)TotalCastingTime*Tile;if (CastPercent<=0) {CastPercent=0;CastSize=Tile;}
                Rectangle2D.Double r = new Rectangle2D.Double(X+CastPercent,Y-ARC,CastSize,G);
                g2.fill(r);
            }
            else
            {
                g2.setColor(Color.BLACK);
                Rectangle2D.Double s = new Rectangle2D.Double(X-1,Y-1-ARC,Tile+2,G+2);
                g2.fill(s);
                g2.setColor(Color.BLACK);
                Rectangle2D.Double r = new Rectangle2D.Double(X,Y-ARC,Tile,G);
                g2.fill(r);
                g2.setColor(new Color (0,183,170));
                float CastPercent = ((float)CastedTime/(float)TotalCastingTime)*Tile;
                r = new Rectangle2D.Double(X,Y-ARC,CastPercent,G);
                g2.fill(r);
            }    
        }
    }
    
    
    
    public static void DibujarBarrasdeVida (Graphics g, int TipoChar, int charX, int charY, int ActualHps, int TotalHps, Boolean Degradado, int CalidadDegradado)
    {    
        Graphics2D g2 = (Graphics2D) g;
        setMedidasBarrasdeVida(TipoChar);
        
        charX = charX-Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
        charY = charY-Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();
        
        int Tile = MiscData.MAP3D_TILESIZE;
        int numPixeles = Tile/CalidadDegradado;                             //CALIDAD DEGRADADO Tiene que ser Multiplos de 2 (1,2,4,8,16,32) (ya que la barra de vida ocupa 32 pixeles, y la calidad de degradado define el numero de pixeles que ocupa cada punto de color)
        int incColorR = (ColorFinalR-ColorBaseR)/numPixeles;                //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
        int incColorG = (ColorFinalG-ColorBaseG)/numPixeles;                //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
        int incColorB = (ColorFinalB-ColorBaseB)/numPixeles;
        
        if (Degradado == true)
        {  
            g2.setColor(Color.BLACK);
            Rectangle2D.Double s = new Rectangle2D.Double(charX-GR,charY-GR-ARC,Tile+GR*2,G+GR*2);
            g2.fill(s);
            for (int i=numPixeles;i>=0;i--)
            {   
                g2.setColor(new Color (ColorBaseR+i*incColorR,ColorBaseG+i*incColorG,ColorBaseB+i*incColorB));
                Rectangle2D.Double r = new Rectangle2D.Double(charX,charY-ARC,CalidadDegradado*i,G);
                g2.fill(r);
            }
            g2.setColor(Color.BLACK);
            float HPpercent = ((float)ActualHps/(float)TotalHps)*Tile;
            float HPSize = ((float)TotalHps-(float)ActualHps)/(float)TotalHps*Tile;if (HPpercent<=0) {HPpercent=0;HPSize=Tile;}
            Rectangle2D.Double r = new Rectangle2D.Double(charX+HPpercent,charY-ARC,HPSize,G);
            g2.fill(r);
        }
        else
        {
            g2.setColor(Color.BLACK);
            Rectangle2D.Double s = new Rectangle2D.Double(charX-1,charY-1-ARC,Tile+2,G+2);
            g2.fill(s);
            g2.setColor(Color.BLACK);
            Rectangle2D.Double r = new Rectangle2D.Double(charX,charY-ARC,Tile,G);
            g2.fill(r);
            g2.setColor(Color.RED);
            float HPpercent = ((float)ActualHps/(float)TotalHps)*Tile;
            r = new Rectangle2D.Double(charX,charY-ARC,HPpercent,G);
            g2.fill(r);
        }      
    }
    
    public static void DibujarBarrasdeCasteo (Graphics g, int TipoChar, int charX, int charY, Boolean isCasteandoSpell, int CastedTime, int TotalCastingTime, Boolean Degradado, int CalidadDegradado)
    {
        Graphics2D g2 = (Graphics2D) g;
        setMedidasBarrasdeCasteo(TipoChar);
        
        if (!isCasteandoSpell) return;
        charX = charX-Myrran.getMap3D().getCamara().getEsquinaSupIzdaX();
        charY = charY-Myrran.getMap3D().getCamara().getEsquinaSupIzdaY();
        
        int Tile = MiscData.MAP3D_TILESIZE;
        int numPixeles = Tile/CalidadDegradado;                             //CALIDAD DEGRADADO Tiene que ser Multiplos de 2 (1,2,4,8,16,32) (ya que la barra de vida ocupa 32 pixeles, y la calidad de degradado define el numero de pixeles que ocupa cada punto de color)
        int incColorR = (ColorFinalR-ColorBaseR)/numPixeles;                //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
        int incColorG = (ColorFinalG-ColorBaseG)/numPixeles;                //Como el color base empieza en 130, y el maximo es 250, el degrado son esos 120 puntos de color que hay que repartir entre los pixeles que creemos dependiendo de la calidad de degradado
        int incColorB = (ColorFinalB-ColorBaseB)/numPixeles;
        
        if (Degradado==true)
        {    
            g2.setColor(Color.BLACK);                                                               //X-1 (-1 un pixel de reborde negro a la izda)
            Rectangle2D.Double s = new Rectangle2D.Double(charX-GR,charY-GR-ARC,Tile+GR*2,G+GR*2);                  //Y+1 un pixel de reborde negro arriba, a 8 pixeles encima de la cabeza del char y 3 debajo de la barra de vida
            g2.fill(s);                                                                             //TileSize Pixeles de ancho +2, +1 por cada lado, y 5 pixeles de alto (el Alto de la barra mas 1 pixel de reborde por arriba y por abajo, +2)                                                   
            for (int i=numPixeles;i>=0;i--)
            {
                g2.setColor(new Color (ColorBaseR+i*incColorR,ColorBaseG+i*incColorG,ColorBaseB+i*incColorB));
                Rectangle2D.Double r = new Rectangle2D.Double(charX,charY-ARC,CalidadDegradado*i,G);
                g2.fill(r);
            }
            g2.setColor(Color.BLACK);
            float CastPercent = ((float)CastedTime/(float)TotalCastingTime)*Tile;
            float CastSize = ((float)TotalCastingTime-(float)CastedTime)/(float)TotalCastingTime*Tile;if (CastPercent<=0) {CastPercent=0;CastSize=Tile;}
            Rectangle2D.Double r = new Rectangle2D.Double(charX+CastPercent,charY-ARC,CastSize,G);
            g2.fill(r);
        }
        else
        {
            g2.setColor(Color.BLACK);
            Rectangle2D.Double s = new Rectangle2D.Double(charX-1,charY-1-ARC,Tile+2,G+2);
            g2.fill(s);
            g2.setColor(Color.BLACK);
            Rectangle2D.Double r = new Rectangle2D.Double(charX,charY-ARC,Tile,G);
            g2.fill(r);
            g2.setColor(new Color (0,183,170));
            float CastPercent = ((float)CastedTime/(float)TotalCastingTime)*Tile;
            //float CastSize = ((float)TotalCastingTime-(float)CastedTime)/(float)TotalCastingTime*TileSize;if (CastPercent<=0) {CastPercent=0;CastSize=TileSize;}
            r = new Rectangle2D.Double(charX,charY-ARC,CastPercent,G);
            g2.fill(r);
        }   
    }*/
}
