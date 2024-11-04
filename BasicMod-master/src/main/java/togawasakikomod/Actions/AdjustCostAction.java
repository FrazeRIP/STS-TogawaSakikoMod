package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AdjustCostAction extends AbstractGameAction {
    AbstractCard card;
    public AdjustCostAction(AbstractCard card, int amount){
        this.amount = amount;
        this.card = card;
    }

    @Override
    public void update() {
        card.updateCost(amount - card.cost);
        isDone = true;
    }
}
