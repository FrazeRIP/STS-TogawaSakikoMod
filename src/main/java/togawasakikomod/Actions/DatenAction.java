//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

import java.util.ArrayList;

public class DatenAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private AbstractCard.CardType cardType = null;

    public DatenAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 3;
    }

    public void update() {
        ArrayList generatedCards;
        generatedCards = this.generateCardChoices();

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.cardRewardScreen.discoveryCard = null;
            this.isDone = true;
            return;
        }

        if(generatedCards == null){
            this.isDone = true;
            return;
        }

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], this.cardType != null);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    addToTop(new RemoveCardFromDeckAction(AbstractDungeon.cardRewardScreen.discoveryCard));
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                    this.isDone = true;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>(AbstractDungeon.player.hand.group);
        derp.addAll(AbstractDungeon.player.discardPile.group);
        derp.addAll(AbstractDungeon.player.drawPile.group);
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.group = derp;

        ArrayList<AbstractCard> result = new ArrayList<>();

        int chooseAmount = Math.min(derp.size(), amount);

        if(derp.size()>0){
            for(int i = chooseAmount; i >0;i--){
                AbstractCard card = group.getRandomCard(AbstractDungeon.cardRandomRng);
                result.add(card);
                group.removeCard(card);
            }
            return result;
        }else {
            return null;
        }
    }
}
