package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.cards.SakikoDeck.Attacks.*;

import java.util.ArrayList;

public class CrychicAction extends AbstractGameAction {

    ArrayList<AbstractCard> phantoms = new ArrayList<AbstractCard>();

    public CrychicAction(int amount){
        this.amount = amount;
        phantoms.add(new PhantomOfMutsumi());
        phantoms.add(new PhantomOfSakiko());
        phantoms.add(new PhantomOfSoyo());
        phantoms.add(new PhantomOfTaki());
        phantoms.add(new PhantomOfTomori());
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            amount += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.group = phantoms;
        for (int i = 0; i < amount; i++) {
            AbstractCard targetCard = group.getRandomCard(AbstractDungeon.cardRandomRng).makeStatEquivalentCopy();
            targetCard.freeToPlayOnce = true;
            addToTop(new MakeTempCardInHandAction(targetCard));
        }
        this.isDone = true;
    }
}
