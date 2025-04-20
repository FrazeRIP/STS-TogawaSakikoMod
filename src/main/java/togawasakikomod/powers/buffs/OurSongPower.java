package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import javassist.CtBehavior;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.patches.CustomEnumPatch;
import togawasakikomod.patches.ReducedPowerRecorderPatch;
import togawasakikomod.powers.BasePower;

import java.util.ArrayList;
import java.util.Objects;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class OurSongPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(OurSongPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    private boolean justApplied =false;

    public OurSongPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

//    @Override
//    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
//        if(this.amount>0 && info.type == DamageInfo.DamageType.NORMAL){
//            addToTop(new GainBlockAction(owner,(int)damageAmount));
//            addToTop(new ReducePowerAction(owner,owner,this,1));
//        }
//        super.onAttack(info, damageAmount, target);
//    }

    @SpirePatch (clz = AbstractMonster.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class OurSongPowerPatcher{
        @SpireInsertPatch(locator = Locator.class, localvars = {"damageAmount"})
        public static void What(AbstractMonster __inst,DamageInfo info, @ByRef int[] damageAmount) {
            AbstractPlayer p = AbstractDungeon.player;
            if(info.owner == p &&info.type == DamageInfo.DamageType.NORMAL){
                if(p.hasPower(OurSongPower.POWER_ID)){
                    OurSongPower power = (OurSongPower) p.getPower(OurSongPower.POWER_ID);
                    if(power.amount>0){
                      AbstractDungeon.actionManager.addToTop(new GainBlockAction(p,(int)damageAmount[0]));
                        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p,p,OurSongPower.POWER_ID,1));
                    }
                }
            }
        }

        public static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "lastDamageTaken");
                return LineFinder.findInOrder(ctBehavior, (Matcher)matcher);
            }
        }
    }


    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new OurSongPower(this.owner,amount);
    }
}