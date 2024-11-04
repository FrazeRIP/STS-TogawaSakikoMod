//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class EtherAction extends AbstractGameAction {
    private DamageInfo info;
    private float startingDuration;

    public EtherAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        ArrayList<AbstractCard> nonAttackCard = new ArrayList<>();

        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card.type != AbstractCard.CardType.ATTACK){
                nonAttackCard.add(card);
            }
        }

        for(AbstractCard card : nonAttackCard){
            addToTop(new ExhaustSpecificCardAction(card,AbstractDungeon.player.drawPile,true));
            addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_LIGHT));
        }

        this.isDone = true;
    }
}
