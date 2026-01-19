package togawasakikomod.powers.buffs;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import org.omg.CORBA.PUBLIC_MEMBER;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.monsters.bosses.mygo.ChihayaAnonBoss;
import togawasakikomod.powers.BasePower;

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
