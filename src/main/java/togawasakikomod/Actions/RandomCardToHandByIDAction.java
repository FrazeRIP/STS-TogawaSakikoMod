package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
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
        for(AbstractCard c: this.p.drawPile.group){
           if(Objects.equals(c.cardID, ID)){
               if (this.p.hand.size() == 10) {
               this.p.drawPile.moveToDiscardPile(c);
               this.p.createHandIsFullDialog();
               isDone = true;
               return;
           } else {
                   c.unhover();
                   c.lighten(true);
                   c.setAngle(0.0F);
                   c.drawScale = 0.12F;
                   c.targetDrawScale = 0.75F;
                   c.current_x = CardGroup.DRAW_PILE_X;
                   c.current_y = CardGroup.DRAW_PILE_Y;
               this.p.drawPile.removeCard(c);
               AbstractDungeon.player.hand.addToTop(c);
               AbstractDungeon.player.hand.refreshHandLayout();
               AbstractDungeon.player.hand.applyPowers();
                   isDone = true;
                   return;
           }
           }
        }

            for(AbstractCard c: this.p.discardPile.group){
                if(Objects.equals(c.cardID, ID)){
                    addToTop(new DiscardToHandAction(c));
                }
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
