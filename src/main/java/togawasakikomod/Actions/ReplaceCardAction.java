package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.CardDisappearEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;

public class ReplaceCardAction extends AbstractGameAction {
    AbstractCard cardToRemove;
    AbstractCard cardToReplace;
    private static final float PADDING;

    public ReplaceCardAction(AbstractCard cardToRemove, AbstractCard cardToReplace){
        this.cardToRemove = cardToRemove;
        this.cardToReplace = cardToReplace;
    }



    @Override
    public void update() {
        if(cardToReplace != null){
            addToTop(new MakeTempCardInHandAction(cardToReplace.makeStatEquivalentCopy(),false));
        }

        if(cardToRemove != null){
            AbstractDungeon.player.drawPile.group.remove(cardToRemove);
            AbstractDungeon.player.hand.group.remove(cardToRemove);
            AbstractDungeon.player.discardPile.group.remove(cardToRemove);
            AbstractDungeon.player.drawPile.refreshHandLayout();
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.discardPile.refreshHandLayout();

            ArrayList<CardQueueItem> targetItems = new ArrayList<>();
            for(CardQueueItem item : AbstractDungeon.actionManager.cardQueue){
                if(item.card == cardToRemove){
                    targetItems.add(item);
                }
            }

            for (CardQueueItem item : targetItems){
                AbstractDungeon.actionManager.cardQueue.remove(item);
            }

            //AbstractDungeon.topLevelEffects.add(new CardDisappearEffect(cardToRemove, (float)Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
        }
        this.isDone = true;
    }
    static {
        PADDING = 25.0F * Settings.scale;
    }
}
