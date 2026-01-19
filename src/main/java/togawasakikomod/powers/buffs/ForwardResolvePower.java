package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class ForwardResolvePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ForwardResolvePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ForwardResolvePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount>0){
            addToBot(new ApplyPowerAction(owner,owner,new MonsterVigorPower(owner,amount),amount));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ForwardResolvePower(owner,amount);
    }
}
