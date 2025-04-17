package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RandomCardToHandByIDAction extends AbstractGameAction {

    AbstractPlayer p;
    String ID;

    public RandomCardToHandByIDAction(String cardID) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.ID =cardID;
    }

    public void update() {
        if(ID!=null){
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for(AbstractCard c: this.p.drawPile.group){
           if(Objects.equals(c.cardID, ID)){
               group.group.add(c);
           }
        }

        if(group.group.isEmpty()){
            for(AbstractCard c: this.p.discardPile.group){
                if(Objects.equals(c.cardID, ID)){
                    group.group.add(c);
                }
            }
        }

        if(!group.group.isEmpty()){
            AbstractCard card = group.getRandomCard(true);
            if(card!=null){
                this.p.hand.addToHand(card);
                card.lighten(false);
                this.p.drawPile.removeCard(card);
                this.p.discardPile.removeCard(card);
                this.p.hand.refreshHandLayout();
            }
        }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
