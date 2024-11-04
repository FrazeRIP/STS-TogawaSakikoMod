package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.powers.buffs.HypePower;
import togawasakikomod.powers.buffs.SeizeTheFatePower;

public class SeizeTheFateAction extends AbstractGameAction {

    private  AbstractPower power;

    public SeizeTheFateAction(AbstractPower power){
        this.power = power;
    }

    @Override
    public void update() {
        if(power!=null && power.owner.currentBlock>0){
            addToBot(new ApplyPowerAction(power.owner,power.owner,new HypePower(power.owner,amount)));
        }
        isDone = true;
    }
}
