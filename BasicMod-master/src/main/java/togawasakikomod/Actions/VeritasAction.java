package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class VeritasAction extends AbstractGameAction {
    public  VeritasAction(){

    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = DrawCardAction.drawnCards;
        if(cards.size()>0){
            for(AbstractCard card : cards){
                if(card.type != AbstractCard.CardType.ATTACK){
                    addToTop(new RemoveCardFromDeckAction(card));
                }
            }
        }
        isDone = true;
    }

    public static <T> ArrayList<T> getTopElements(ArrayList<T> list, int x) {
        if (list.size() <= x) {
            return new ArrayList<>(list);
        } else {
            return new ArrayList<>(list.subList(0, x));
        }
    }
}
