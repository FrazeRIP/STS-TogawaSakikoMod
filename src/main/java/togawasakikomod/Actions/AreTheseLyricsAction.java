package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.cards.SpecialDeck.Attacks.Melody;

import java.util.ArrayList;
import java.util.Iterator;


public class AreTheseLyricsAction extends AbstractGameAction {
    private boolean upgrade;

    public AreTheseLyricsAction(boolean upgraded) {
        this.upgrade = upgraded;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToDiscard = new ArrayList();
        Iterator var2 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type != AbstractCard.CardType.ATTACK) {
                cardsToDiscard.add(c);
            }
        }

        if(this.upgrade){
            AbstractCard s = (new Melody()).makeCopy();
            s.upgrade();
            this.addToTop(new MakeTempCardInHandAction(s, cardsToDiscard.size()));
        }else{
            this.addToTop(new MakeTempCardInHandAction(new Melody(), cardsToDiscard.size()));
        }

        var2 = cardsToDiscard.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            this.addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.hand));
        }
        this.isDone = true;
    }
}