package togawasakikomod.powers.monsters;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class MonsterVigorPower extends VigorPower {

    public MonsterVigorPower(AbstractCreature owner, int amount) {
        super(owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {

    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        addToTop(new ReducePowerAction(this.owner,this.owner,ID,1));
    }
}
