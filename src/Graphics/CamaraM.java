/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Mobiles.Mob;
import Mobiles.PC;
import DATA.MiscData;
/**
 *
 * @author Hanto
 */
public class CamaraM 
{
    private Mob Target;
    
    public void setTarget (PC target)       { Target = target;}
    public Mob getTarget ()                 { return Target;}
    
    public int getEsquinaSupIzdaX()         { return (Target.getX()-MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2); }
    public int getEsquinaSupIzdaY()         { return (Target.getY()-MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2); }
    
    public int getEsquinaInfDchaX()         { return (Target.getX()+MiscData.MAP3D_WIDOWHORIZONTALRESOLUTION/2); }
    public int getEsquinaInfDchaY()         { return (Target.getY()+MiscData.MAP3D_WINDOWVERTIZALRESOLUTION/2); }
}
