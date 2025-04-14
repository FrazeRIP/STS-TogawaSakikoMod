package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RandomCardFromDrawPileToHandAction extends AbstractGameAction {

    private AbstractPlayer p;
    AbstractCard.CardType type = null;
    public RandomCardFromDrawPileToHandAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public RandomCardFromDrawPileToHandAction(AbstractCard.CardType type) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.type =type;
    }

    public void update() {
        if (!this.p.drawPile.isEmpty()) {
            AbstractCard card;
            if(type!=null){
                card = this.p.drawPile.getRandomCard(type,true);
            }
            else{
                card = this.p.drawPile.getRandomCard(true);
            }

            if(card!=null){
                this.p.hand.addToHand(card);
                card.lighten(false);
                this.p.drawPile.removeCard(card);
                this.p.hand.refreshHandLayout();
            }
        }else{
            addToTop(new RandomCardFromDrawPileToHandAction(this.type));
            addToTop(new EmptyDeckShuffleAction());
        }
        this.tickDuration();
        this.isDone = true;
    }
}
