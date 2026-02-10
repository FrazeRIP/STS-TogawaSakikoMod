package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

@CharismaticFormCopyEnable(enable = false)
public class StrengthUpPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(StrengthUpPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public StrengthUpPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrengthUpPower(this.owner,amount);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        if(this.amount>0){
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, amount), amount));
        }
    }
}