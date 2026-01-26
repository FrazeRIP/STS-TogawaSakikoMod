package togawasakikomod.powers.monsters;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.Actions.TemporalLongingAction;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.monsters.bosses.FinalBossMonster;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

@CharismaticFormCopyEnable(enable = false)
public class TemporalLongingPower extends BasePower {
    public static final String POWER_ID = makeID(TemporalLongingPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private final int maxCount = 10;

    public TemporalLongingPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        amount2 = 1;
        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        amount2++;
        if(this.owner instanceof FinalBossMonster){
            FinalBossMonster m = (FinalBossMonster) this.owner;
        }
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        flashWithoutSound();
        this.amount++;
        if( this.amount>=maxCount){
            this.amount = 0;
            playApplyPowerSfx();
            addToTop(new TemporalLongingAction());
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1];
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
}
