package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RemoveRandomCardAction extends AbstractGameAction {

    AbstractCard excludedCard = null;

    public RemoveRandomCardAction(int amount, AbstractCard excluded){
        this.amount = amount;
        this.excludedCard = excluded;
    }


    @Override
    public void update() {
        ArrayList<AbstractCard> derp = new ArrayList<>(AbstractDungeon.player.hand.group);
        derp.addAll(AbstractDungeon.player.discardPile.group);
        derp.addAll(AbstractDungeon.player.drawPile.group);

        if(excludedCard!=null && derp.contains(excludedCard)){
            derp.remove(excludedCard);
        }


        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.group = derp;

        int removeAmount = Math.min(derp.size(), amount);

        for(int i = 0; i<removeAmount;i++){
            AbstractCard card = group.getRandomCard(AbstractDungeon.cardRandomRng);
            addToTop(new RemoveCardFromDeckAction(card));
            group.removeCard(card);
        }

        this.isDone = true;
    }


}