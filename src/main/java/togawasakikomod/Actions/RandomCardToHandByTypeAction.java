package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.unique.DeckToHandAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RandomCardToHandByTypeAction extends AbstractGameAction {

     AbstractPlayer p;
    AbstractCard.CardType type = null;

    public RandomCardToHandByTypeAction(AbstractCard.CardType type) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.type =type;
    }

    public void update() {
        if(type!=null){
        AbstractCard card = null;
        if(!this.p.drawPile.group.isEmpty()){
            card = this.p.drawPile.getRandomCard(type,true);

            if(card != null){
                addToTop(new DrawPileToHandAction(1,type));
                this.isDone = true;
                return;
            }
        }

        if(!this.p.discardPile.group.isEmpty()){card = this.p.discardPile.getRandomCard(type,true);}
        if(card!=null){
                addToTop(new DiscardToHandAction(card));
//            this.p.hand.addToHand(card);
//            card.lighten(false);
//            this.p.drawPile.removeCard(card);
//            this.p.discardPile.removeCard(card);
//            this.p.hand.refreshHandLayout();
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
