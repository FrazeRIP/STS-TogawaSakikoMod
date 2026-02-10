package togawasakikomod.powers.monsters;

import basemod.BaseMod;
import basemod.interfaces.OnPowersModifiedSubscriber;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;

@CharismaticFormCopyEnable(enable = false)
public class UnfadingYearningPower extends BasePower implements OnPowersModifiedSubscriber {
    public static final String POWER_ID = makeID(UnfadingYearningPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public UnfadingYearningPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        BaseMod.subscribe(this);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void receivePowersModified() {
        if(this.owner instanceof YuutenjiNyamuBoss){
            YuutenjiNyamuBoss c = (YuutenjiNyamuBoss) this.owner;
            boolean hasBackAttack = c.hasPower(BackAttackPower.POWER_ID);
            if(c.hasBackAttack != hasBackAttack){
                c.hasBackAttack = hasBackAttack;
                c.rollMove();
                c.createIntent();
            }
        }
    }
}
