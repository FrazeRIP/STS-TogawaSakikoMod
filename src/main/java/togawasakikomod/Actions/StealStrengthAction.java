package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StealStrengthAction extends AbstractGameAction {
    AbstractCreature target;
    AbstractCreature source;
    public StealStrengthAction(int amount, AbstractCreature target, AbstractCreature source){
        this.amount = amount;
        this.target = target;
        this.source = source;
    }

    @Override
    public void update() {
        if(target.hasPower(StrengthPower.POWER_ID)){
            AbstractPower power = target.getPower(StrengthPower.POWER_ID);
            if(power.amount>0){
                if(power.amount<=this.amount){
                    addToTop(new RemoveSpecificPowerAction(target,source,power));
                    addToTop(new ApplyPowerAction(source,source,new StrengthPower(source,power.amount),power.amount));
                }else{
                    addToTop(new ReducePowerAction(target,source,power,this.amount));
                    addToTop(new ApplyPowerAction(source,source,new StrengthPower(source,power.amount),this.amount));
                }
            }
        }
        this.isDone = true;
    }
}
