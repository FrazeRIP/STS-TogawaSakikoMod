
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.patches.CustomEnumPatch;
import togawasakikomod.rewards.PurgeReward;

import java.util.ArrayList;
import java.util.Iterator;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class WishFulfilledAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    public WishFulfilledAction(int numberOfCards, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public WishFulfilledAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public void update() {

        ArrayList<AbstractCard> cardPool = new ArrayList<>(AbstractDungeon.player.hand.group);
        cardPool.addAll(AbstractDungeon.player.discardPile.group);
        cardPool.addAll(AbstractDungeon.player.drawPile.group);

        ArrayList<AbstractCard> unremoveableCards = new ArrayList<>();

        for(AbstractCard card : cardPool){
            if (card.cardID.equals("Necronomicurse") ||
                    card.cardID.equals("CurseOfTheBell") ||
                    card.cardID.equals("AscendersBane") ||
                    card.hasTag(CustomEnumPatch.TOGAWASAKIKO_UNREMOVEABLE)
            ) {
                unremoveableCards.add(card);
            }
        }

        for(AbstractCard card :unremoveableCards){
            cardPool.remove(card);
        }

        if (this.duration == this.startDuration) {
            if (this.numberOfCards > 0) {
                AbstractCard c;
                Iterator var6;

                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    var6 = cardPool.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();

            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    addToTop(new RemoveCardFromDeckAction(c));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString(makeID(PurgeReward.class.getSimpleName())).TEXT;
    }
}
