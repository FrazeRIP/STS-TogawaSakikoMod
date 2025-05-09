package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;
import togawasakikomod.powers.BasePower;

import java.util.Objects;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class CrueltyPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(CrueltyPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public CrueltyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if(Objects.equals(card.cardID, Desire.ID)){
            this.flash();
            addToBot(new DrawCardAction(amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CrueltyPower(this.owner,amount);
    }
}