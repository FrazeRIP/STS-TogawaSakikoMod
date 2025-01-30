package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.cards.SpecialDeck.Attacks.Melody;

public class MatchaParfaitAction extends AbstractGameAction {
    Melody card;
    public MatchaParfaitAction(){
        card = new Melody(AbstractDungeon.potionRng.random(6,30));
    }

    @Override
    public void update() {
        addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
        isDone = true;
    }
}
