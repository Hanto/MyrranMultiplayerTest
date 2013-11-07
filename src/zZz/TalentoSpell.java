/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zZz;

import Main.Myrran;
import Skills.SpellTemplate;

/**
 *
 * @author Hanto
 */
public class TalentoSpell 
{
    private int SpellID;
    private Boolean isConocido;
    
    private static class TalentoStat
    {
        public int TalentoActual;
        public double ValorTotal;
    };
    
    private TalentoStat [] TStats;
   
    
    
    public void reCalc ()
    {
        for (int i=0; i<TStats.length;i++)
        {   
            SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(SpellID);
            TStats[i].ValorTotal = spell.Stats()[i].ValorBase + spell.Stats()[i].BonoTalento * TStats[i].TalentoActual;
        }
    }
    
    public TalentoSpell (int SpellID)
    {
        this.SpellID = SpellID;
        isConocido = true;
        TStats = new TalentoStat [Myrran.getMundo().ListadeSkills().get(SpellID).Stats().length];
        
        SpellTemplate spell = Myrran.getMundo().ListadeSkills().get(SpellID);
        
        for (int i=0; i<TStats.length;i++)
        {
            TalentoStat tstat = new TalentoStat();
            tstat.TalentoActual = 0;
            tstat.ValorTotal = spell.Stats()[i].ValorBase + spell.Stats()[i].BonoTalento * tstat.TalentoActual;
            TStats[i]=tstat;
        }
    }
}
