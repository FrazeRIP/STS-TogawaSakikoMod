package togawasakikomod.powers.debuffs;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class MortisPower extends BasePower
        //implements OnLoseBlockPower
{
    public static final String POWER_ID = makeID(MortisPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean justApplied = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public MortisPower(AbstractCreature owner, int amount, boolean justApplied) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
            this.justApplied = justApplied;
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            }
        }
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if(this.owner == AbstractDungeon.player && damageAmount>0 && amount>0){
            this.flash();
            addToBot(new MakeTempCardInDrawPileAction(new Injury(),1,true,true,false));
        }
        return super.onLoseHp(damageAmount);
    }
}