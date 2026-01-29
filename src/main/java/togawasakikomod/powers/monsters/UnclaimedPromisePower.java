package togawasakikomod.powers.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

@CharismaticFormCopyEnable(enable = false)
public class UnclaimedPromisePower extends BasePower{
    public static final String POWER_ID = makeID(UnclaimedPromisePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public UnclaimedPromisePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        amount2 = 1;
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        amount2++;
        updateDescription();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (this.amount2 != 0) {
            if (!this.isTurnBased) {
                float alpha = c.a;
                c = this.yellowColor2 ;
                c.a = alpha;
            }
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void ApplyEffect(){
        if(amount2%2 == 1){
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner,new IntangiblePower(this.owner,1),1));
            AbstractDungeon.actionManager.addToTurnStart(new ReducePowerAction(AbstractDungeon.player,this.owner,IntangiblePlayerPower.POWER_ID,1));
        }
    }

}
