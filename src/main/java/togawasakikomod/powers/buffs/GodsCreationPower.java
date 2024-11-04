package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.patches.CustomEnumPatch;
import togawasakikomod.powers.BasePower;

import java.util.ArrayList;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class GodsCreationPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(GodsCreationPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public GodsCreationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL){
            damage-= amount;
            if(damage<0){damage =0;}
        }
        return super.atDamageFinalReceive(damage, type);
    }


    @SpirePatch(clz = AbstractMonster.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class GodsCreationMonsters{
        @SpirePrefixPatch()
        public static SpireReturn<Integer> Prefix(AbstractMonster __instance, @ByRef DamageInfo[] info)
        {
            if(__instance.hasPower(GodsCreationPower.POWER_ID)){
                if(info[0].type != DamageInfo.DamageType.NORMAL){
                    AbstractPower power = __instance.getPower(GodsCreationPower.POWER_ID);
                    info[0].output -= power.amount;
                    info[0].output = Math.max(info[0].output,0);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class GodsCreationPlayer{
        @SpirePrefixPatch()
        public static SpireReturn<Integer> Prefix(AbstractPlayer __instance, @ByRef DamageInfo[] info)
        {
            if(__instance.hasPower(GodsCreationPower.POWER_ID)){
                if(info[0].type != DamageInfo.DamageType.NORMAL){
                    AbstractPower power = __instance.getPower(GodsCreationPower.POWER_ID);
                    info[0].output -= power.amount;
                    info[0].output = Math.max(info[0].output,0);
                }
            }
            return SpireReturn.Continue();
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GodsCreationPower(this.owner,amount);
    }
}