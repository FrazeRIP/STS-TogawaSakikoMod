package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.Actions.SeizeTheFateAction;
import togawasakikomod.powers.BasePower;

import java.util.ArrayList;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class PridePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(PridePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public static ArrayList<AbstractCard> copies = new ArrayList<>();

    public PridePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfRound();
        if(isPlayer){
            for(AbstractCard card : copies){
                addToBot(new MakeTempCardInDrawPileAction(card.makeStatEquivalentCopy(),1,false,false,false));
            }
            copies.clear();
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PridePower(this.owner,amount);
    }
}