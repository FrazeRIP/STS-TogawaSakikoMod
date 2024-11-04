package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.Iterator;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class PerfectionAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(PerfectionAction.class.getSimpleName()));
    private static final String[] TEXT = uiStrings.TEXT;


    public PerfectionAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (int i = 0; i < 20; ++i) {
                AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat();
                boolean containsDupe = false;

                while (true) {
                    Iterator var6;
                    while (containsDupe) {
                        containsDupe = false;
                        var6 = group.group.iterator();

                        while (var6.hasNext()) {
                            AbstractCard c = (AbstractCard) var6.next();
                            if (c.cardID.equals(card.cardID)) {
                                containsDupe = true;
                                card = AbstractDungeon.returnTrulyRandomCardInCombat();
                                break;
                            }
                        }
                    }

                    if (group.contains(card)) {
                        --i;
                    } else {
                        var6 = AbstractDungeon.player.relics.iterator();

                        while (var6.hasNext()) {
                            AbstractRelic r = (AbstractRelic) var6.next();
                            r.onPreviewObtainCard(card);
                        }

                        group.addToBottom(card);
                    }
                    break;
                }
            }

            Iterator var8 = group.group.iterator();

            while (var8.hasNext()) {
                AbstractCard c = (AbstractCard) var8.next();
                UnlockTracker.markCardAsSeen(c.cardID);
            }
            AbstractDungeon.gridSelectScreen.open(group, 1, TEXT[0], false);
            this.tickDuration();

        }else{
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard c = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeStatEquivalentCopy();
                 //c.setCostForTurn(0);
                c.freeToPlayOnce= true;
                 if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }


}
