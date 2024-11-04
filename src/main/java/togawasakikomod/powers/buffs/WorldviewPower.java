package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.Actions.RemoveCardFromDeckAction;
import togawasakikomod.cards.SpecialDeck.Attacks.Melody;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class WorldviewPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(WorldviewPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public WorldviewPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if(card.cost<=-2){
            this.flash();
            addToBot(new RemoveCardFromDeckAction(card,false,false,false));
            addToBot(new MakeTempCardInHandAction(new Melody()));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WorldviewPower(this.owner,amount);
    }

}