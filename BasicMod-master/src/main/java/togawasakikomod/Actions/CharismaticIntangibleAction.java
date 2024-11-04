package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;


public class CharismaticIntangibleAction extends AbstractGameAction {
    AbstractPower power;

    public CharismaticIntangibleAction(AbstractPower power){
        this.power = power;
    }

    @Override
    public void update() {
        if(!AbstractDungeon.player.hasPower(IntangiblePlayerPower.POWER_ID)){
            power.amount+=1;
        }
        addToTop( new ApplyPowerAction(power.owner,power.owner,power,power.amount));
        this.isDone = true;
    }
}
