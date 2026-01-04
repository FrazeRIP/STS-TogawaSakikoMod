package togawasakikomod.powers.buffs;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class HypePower extends BasePower implements CloneablePowerInterface
        //implements OnLoseBlockPower
{
    public static final String POWER_ID = makeID(HypePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public HypePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @SpirePatch(clz = AbstractCreature.class, method = "loseBlock", paramtypez = {int.class,boolean.class})
    public static class PreventBlockLossForEnemy{
        @SpirePrefixPatch()
        public static SpireReturn<Integer> BlockLossForEnemy(AbstractCreature _inst, @ByRef int[] amount, boolean noAnimation){
            if(_inst.currentBlock >0 && _inst.hasPower(HypePower.POWER_ID)){
                if(amount[0] <= 0)
                {
                    return SpireReturn.Continue();
                }
                AbstractPower power = _inst.getPower(HypePower.POWER_ID);
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(power.owner,power.owner,power,1));
                power.flash();
                amount[0] = 0;
            }
            return SpireReturn.Continue();
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new HypePower(this.owner,amount);
    }
}