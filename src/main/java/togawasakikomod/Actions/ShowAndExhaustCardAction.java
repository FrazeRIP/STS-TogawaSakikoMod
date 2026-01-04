package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.effects.ShowAndExhaustCardEffect;

public class ShowAndExhaustCardAction extends AbstractGameAction {
    AbstractCard card;
    public ShowAndExhaustCardAction(AbstractCard card){
        this.card = card;
        this.duration = 0.05f;
    }

    @Override
    public void update() {
        if(card!=null){
            addToTop(new VFXAction(new ShowAndExhaustCardEffect(card)));
            if(AbstractDungeon.player.hand.contains(card)){
                addToTop(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand,true));
            }else if(AbstractDungeon.player.drawPile.contains(card)){
                addToTop(new ExhaustSpecificCardAction(card,AbstractDungeon.player.drawPile,true));
            }else if(AbstractDungeon.player.discardPile.contains(card)){
                addToTop(new ExhaustSpecificCardAction(card,AbstractDungeon.player.discardPile,true));
            }
        }
        this.isDone = true;
    }
}
