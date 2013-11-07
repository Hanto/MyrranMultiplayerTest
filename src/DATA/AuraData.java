/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

/**
 *
 * @author Hanto
 */
public class AuraData 
{
    //DEBUFFS
    //TIPOS DE SPELLS:
    public final static int AURA_DEBUFF_SNARE = 0;
    public final static int AURA_BUFF_HOT = 1;
    public final static int AURA_DEBUFF_DOT = 2;
    
    //snare
    public final static String SNARE_NOMBRE="Snare";
    public final static String SNARE_IMG= MiscData.PATH_AURAS_RES+"Snare.png";
    public final static int SNARE_TIPO = AuraData.AURA_DEBUFF_SNARE;
    public final static int SNARE_DURACIONMAXIMA=5000;
    public final static Boolean SNARE_ISDEBUFF = Boolean.TRUE;
    
    public final static double SNARE_PENALIZADORMOVIMIENTO=0.5;
    
    //HOT:Renew
    public final static String HOTRENEW_NOMBRE="HOT Renew";
    public final static String HOTRENEW_IMG= MiscData.PATH_AURAS_RES+"Renew.png";
    public final static int HOTRENEW_TIPO = AuraData.AURA_BUFF_HOT;
    public final static int HOTRENEW_DURACIONMAXIMA = 10000;
    public final static Boolean HOTRENEW_ISDEBUFF = Boolean.FALSE;
    
    public final static int HOTRENEW_HEALPORTICK = 90;
    
    //DOT: LifeRot
    public final static String DOT_LIFEROT_NOMBRE = "DOT";
    public final static String DOT_LIFEROT_IMG = MiscData.PATH_AURAS_RES+"Liferot.png";
    public final static int DOT_LIFEROT_TIPO = AuraData.AURA_DEBUFF_DOT;
    public final static int DOT_LIFEROT_DURACIONMAXIMA = 20000;
    public final static Boolean DOT_LIFEROT_ISDEBUFF = Boolean.TRUE;
    
    public final static int DOT_LIFEROT_DAÑOPORTICK = 39;
    
}
