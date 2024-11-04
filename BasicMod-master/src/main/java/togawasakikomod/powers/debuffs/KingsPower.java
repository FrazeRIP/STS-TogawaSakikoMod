package togawasakikomod.powers.debuffs;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.patches.ReducedPowerRecorderPatch;
import togawasakikomod.powers.BasePower;
import togawasakikomod.powers.buffs.HypePower;
import togawasakikomod.saveable.KingsSaveable;

import java.util.ArrayList;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class KingsPower extends BasePower
        //implements OnLoseBlockPower
{
    public static final String POWER_ID = makeID(KingsPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public KingsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @SpirePatch (clz = AbstractDungeon.class, method = "getRewardCards")
    public static class ReduceCardReward{
        @SpireInsertPatch(
                locator= ReduceCardReward.RewardCardNumLocator.class,
                localvars = {"numCards"})
        public static void ReduceCardRewardAction( @ByRef int[] numCards) {
            System.out.println("---------------------Reward");
            if(AbstractDungeon.player.hasPower(KingsPower.POWER_ID) || KingsSaveable.IsKing){
                numCards[0] = numCards[0] -= 1;
            }
        }

        public static class RewardCardNumLocator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher fieldAccessMatcher =
                        new Matcher.FieldAccessMatcher(AbstractPlayer.class,"relics");
                return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
            }
        }
    }
}