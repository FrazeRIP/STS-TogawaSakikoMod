package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.cards.SakikoDeck.Attacks.*;
import togawasakikomod.powers.BasePower;

import java.util.ArrayList;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class CrychicPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(CrychicPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    ArrayList<AbstractCard> phantoms = new ArrayList<AbstractCard>();

    public CrychicPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        phantoms.add(new PhantomOfMutsumi());
        phantoms.add(new PhantomOfSakiko());
        phantoms.add(new PhantomOfSoyo());
        phantoms.add(new PhantomOfTaki());
        phantoms.add(new PhantomOfTomori());
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.flash();
        addToBot(new ReducePowerAction(owner,owner,this,1));

        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.group = phantoms;
        AbstractCard targetCard = group.getRandomCard(AbstractDungeon.cardRandomRng).makeStatEquivalentCopy();
        targetCard.setCostForTurn(0);
        addToBot(new MakeTempCardInHandAction(targetCard));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CrychicPower(this.owner,amount);
    }


}