package togawasakikomod.powers.monsters;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import togawasakikomod.annotations.CardEnable;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

@CharismaticFormCopyEnable(enable = false)
public class EarnestCryPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(EarnestCryPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public EarnestCryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        addToBot(new ApplyPowerAction(this.owner,this.owner,new MantraPower(this.owner,amount),amount));
        addToBot(new ApplyPowerAction(this.owner,this.owner,new EarnestCryPower(this.owner,1),1));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new EarnestCryPower(owner,amount);
    }
}
