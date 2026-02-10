package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;

import java.util.Objects;

public class BlackBirthdayAction extends AbstractGameAction {

    int[] multiDamage;
    public BlackBirthdayAction(int[] multiDamage){
        this.multiDamage = multiDamage;
    }

    @Override
    public void update() {
        for (AbstractPower power : AbstractDungeon.player.powers){
            if(Objects.equals(power.ID, SurroundedPower.POWER_ID)){continue;}
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player,multiDamage, DamageInfo.DamageType.NORMAL,AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,power));
        }
        this.isDone = true;
    }
}
