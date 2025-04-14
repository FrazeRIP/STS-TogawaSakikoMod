package togawasakikomod.Actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;

public class AccompliceAction extends AbstractGameAction
{

    @Override
    public void update() {
        for(AbstractCard card :AbstractDungeon.player.hand.group){
            if(card instanceof Desire){
                Desire desire = (Desire) card;
                desire.setCostForTurn(0);
            }
        }
        this.isDone = true;
    }
}
