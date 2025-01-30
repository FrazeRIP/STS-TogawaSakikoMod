package togawasakikomod.powers.debuffs;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import togawasakikomod.powers.BasePower;
import togawasakikomod.powers.buffs.DazzlingPower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class DazzlingDownPower extends BasePower
        //implements OnLoseBlockPower
{
    public static final String POWER_ID = makeID(DazzlingDownPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean justApplied = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public DazzlingDownPower(AbstractCreature owner, int amount, boolean justApplied) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
            this.justApplied = justApplied;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if(isPlayer){
            addToBot(new ReducePowerAction(owner,owner,DazzlingPower.POWER_ID,amount));
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
        }
    }
}