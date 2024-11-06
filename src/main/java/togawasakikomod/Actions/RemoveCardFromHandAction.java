//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class RemoveCardFromHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public RemoveCardFromHandAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            Iterator var5 = this.p.hand.group.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                tmp.addToRandomSpot(c);
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                addToTop(new RemoveCardFromDeckAction(card));
                this.isDone = true;
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
                this.tickDuration();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    card = (AbstractCard)var1.next();
                    card.unhover();
                    addToTop(new RemoveCardFromDeckAction(card));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID(RemoveCardFromHandAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
