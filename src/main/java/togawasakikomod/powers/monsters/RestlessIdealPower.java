package togawasakikomod.powers.monsters;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

@CharismaticFormCopyEnable(enable = false)
public class RestlessIdealPower extends BasePower {
    public static final String POWER_ID = makeID(RestlessIdealPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RestlessIdealPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if(this.owner instanceof AbstractMonster && damageAmount>0){
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Normality(),1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}
