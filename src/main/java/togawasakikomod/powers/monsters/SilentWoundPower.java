package togawasakikomod.powers.monsters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import togawasakikomod.Actions.TemporalLongingAction;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

@CharismaticFormCopyEnable(enable = false)
public class SilentWoundPower extends BasePower{
    public static final String POWER_ID = makeID(SilentWoundPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private  static  final int PLATE_ARMOR_AMOUNT = 1;

    public SilentWoundPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        this.flash();
        addToBot(new ApplyPowerAction(this.owner,this.owner,new PlatedArmorPower(this.owner,PLATE_ARMOR_AMOUNT),PLATE_ARMOR_AMOUNT));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + PLATE_ARMOR_AMOUNT + DESCRIPTIONS[1];
    }
}
