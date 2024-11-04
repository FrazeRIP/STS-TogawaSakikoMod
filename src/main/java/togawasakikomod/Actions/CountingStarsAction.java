package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.powers.buffs.DazzlingPower;

public class CountingStarsAction extends AbstractGameAction {

    public CountingStarsAction(AbstractCreature target, int amount){
        this.target= target;
        this.amount = amount;
    }

    @Override
    public void update() {
        int buffCount = 0;
        for(AbstractPower power : target.powers){
            if(power.type == AbstractPower.PowerType.BUFF){
                buffCount++;
            }
        }
        if(buffCount>0){
            addToTop(new ApplyPowerAction(
                    target,
                    target,
                    new DazzlingPower(target,buffCount*amount),
                    buffCount*amount));
        }

        this.isDone = true;
    }
}
