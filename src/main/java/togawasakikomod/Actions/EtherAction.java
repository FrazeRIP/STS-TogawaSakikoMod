//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.effects.DazzlingAttackEffect;

import java.util.ArrayList;

public class EtherAction extends AbstractGameAction {
    private DamageInfo info;
    private float startingDuration;
    private  int exhaustCount;

    public EtherAction(AbstractCreature target, int exhaustCount, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.exhaustCount = exhaustCount;
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = 0.02f;
        this.duration = this.startingDuration;
    }

    public void update() {
        ArrayList<AbstractCard> nonAttackCard = new ArrayList<>();

//        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
//            if(card.type != AbstractCard.CardType.ATTACK){
//                nonAttackCard.add(card);
//            }
//        }

//        for(AbstractCard card : AbstractDungeon.player.hand.group){
//            if(nonAttackCard.size()>=exhaustCount){return;}
//            if(card.type != AbstractCard.CardType.ATTACK){
//                nonAttackCard.add(card);
//            }
//        }

        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(nonAttackCard.size()>=exhaustCount){break;}
            if(card.type != AbstractCard.CardType.ATTACK){
                nonAttackCard.add(card);
            }
        }

        for(AbstractCard card : nonAttackCard){
            addToTop(new DamageAction(this.target, this.info, AttackEffect.NONE));
            addToTop(new VFXAction(new DazzlingAttackEffect(this.target,false)));
            addToTop(new ShowAndExhaustCardAction(card));

            AbstractDungeon.player.drawPile.moveToExhaustPile(card);
            CardCrawlGame.dungeon.checkForPactAchievement();
            card.exhaustOnUseOnce = false;
            card.freeToPlayOnce = false;
        }
        this.isDone = true;
    }
}
