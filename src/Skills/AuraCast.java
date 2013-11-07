/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Skills;

import DATA.AuraData;
import Auras.DOT;
import Auras.HOT;
import Auras.Slow;
import Geo.Mundo;
import Main.Myrran;
import Mobiles.Personaje;

/**
 *
 * @author Hanto
 */
public final class AuraCast 
{
    private AuraTemplate aura;
    private Personaje Target;
    private Personaje Owner;
    
    public AuraCast (AuraTemplate Aura, Personaje owner, Personaje target)
    {
        aura = Aura;
        AuraBeginCast (owner, target);
    }
    
    public AuraCast (int AuraID, Personaje owner, Personaje target)
    {   
        aura = Myrran.getMundo().ListadeAuras().get(AuraID);
        AuraBeginCast (owner, target);
    }
    
    public AuraCast (String AuraNombre, Personaje owner, Personaje target)
    {
        int AuraID = Mundo.getAuraID(AuraNombre);
        if (AuraID<0) {System.out.println("Nombre de Aura: "+AuraNombre+" ,no encontado.");return;}
        aura = Myrran.getMundo().ListadeAuras().get(AuraID);
        AuraBeginCast (owner, target);
    }
    
    public void AuraBeginCast (Personaje owner, Personaje target)
    {
        Owner = owner;
        Target = target;
        
        if (AuraExisteYEsNuestroRefrescar()) return;        //Para que un Buff o un Debuff de un Personaje solo stacke una unica vez
        
        switch (aura.getTipo())
        {
            case AuraData.AURA_DEBUFF_SNARE:           {AuraEndCastSnare();break;}
            case AuraData.AURA_BUFF_HOT:               {AuraEndCastHOT();break;}
            case AuraData.AURA_DEBUFF_DOT:             {AuraEndCastDOT();break;}
        }
    }
    
    public void AuraEndCastSnare ()
    {
        Slow snare = new Slow(Target);
        
        snare.setID(aura.getID());
        snare.setNombre(aura.getNombre());
        snare.setTarget(Target);
        snare.setOwner(Owner);
        snare.setIsDebuff(aura.isDebuff()); 
        
        snare.setDuracionMax((int)aura.Stats()[1].getValorTotal());
        snare.setPenalizadorMovimiento(aura.Stats()[0].getValorTotal());
    }
    
    public void AuraEndCastHOT ()
    {
        HOT hot = new HOT(Target);
        
        hot.setID(aura.getID());
        hot.setNombre(aura.getNombre());
        hot.setTarget(Target);
        hot.setOwner(Owner);
        hot.setIsDebuff(aura.isDebuff());
        
        hot.setDuracionMax((int)aura.Stats()[1].getValorTotal());
        hot.setHealPorTick((int)aura.Stats()[0].getValorTotal());
    }
    
    public void AuraEndCastDOT ()
    {
        DOT dot = new DOT(Target);
        
        dot.setID(aura.getID());
        dot.setNombre(aura.getNombre());
        dot.setTarget(Target);
        dot.setOwner(Owner);
        dot.setIsDebuff(aura.isDebuff());
        
        dot.setDuracionMax((int)aura.Stats()[1].getValorTotal());
        dot.setDañoPorTick((int)aura.Stats()[0].getValorTotal());
    }
    
    public Boolean AuraExisteYEsNuestroRefrescar()
    {
        //Chequear si el debuff ya existe en el target, y si ese debuff se lo hemos puesto nosotros, refrescarlo
        //en caso negativo aplicar un nuevo debuff
        
        for (int i=0;i<Target.ListadeAuras().size();i++)
        {
            if (Target.ListadeAuras().get(i).getID() == aura.getID() && Target.ListadeAuras().get(i).getOwner() == Owner) 
                {Target.ListadeAuras().get(i).setDuracion(0);return true;}
        }
        return false;
    }
}
