package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DesuWaAction extends AbstractGameAction {

    AbstractCard.CardType type = null;

    public DesuWaAction(int amount){this.amount = amount;}

    public DesuWaAction(int amount, AbstractCard.CardType type){this.amount = amount; this.type = type;}

    @Override
    public void update() {
        if(type != null || AbstractDungeon.actionManager.cardsPlayedThisCombat.size()>1){
            if(type == null){
                AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size()-2);
                type = card.type;
            }
            for(int i =0; i<amount;i++){
                addToTop(new RandomCardToHandByTypeAction(type));
            }
        }
        this.isDone = true;
    }
}
