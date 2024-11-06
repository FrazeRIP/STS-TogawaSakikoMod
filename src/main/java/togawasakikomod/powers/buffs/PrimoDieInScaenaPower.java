package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.cards.SakikoDeck.Powers.PrimoDieInScaena;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class PrimoDieInScaenaPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(PrimoDieInScaenaPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public PrimoDieInScaenaPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
        addToTop(new GainEnergyAction(amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PrimoDieInScaenaPower(this.owner,amount);
    }

    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {})
    public static class EmptyDeckShuffleActionPatch{
        @SpirePostfixPatch()
        public static SpireReturn<Void> EmptyDeckShuffleActionPatchEvent(EmptyDeckShuffleAction __instance){
            AbstractPlayer player = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, PrimoDieInScaenaPower.POWER_ID));
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = ShuffleAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {CardGroup.class,boolean.class})
    public static class ShuffleActionPatch{
        @SpirePostfixPatch()
        public static SpireReturn<Void> ShuffleActionPatchEvent(ShuffleAction __instance, CardGroup theGroup, boolean trigger){
            if(theGroup == AbstractDungeon.player.discardPile){
                AbstractPlayer player = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, PrimoDieInScaenaPower.POWER_ID));
            }
            return SpireReturn.Continue();
        }
    }
}