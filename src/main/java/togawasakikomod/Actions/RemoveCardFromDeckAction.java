//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import togawasakikomod.cards.SakikoDeck.Attacks.MasqueradeRhapsodyRequest;
import togawasakikomod.modifiers.UnremoveableModifier;
import togawasakikomod.patches.CustomEnumPatch;
import togawasakikomod.powers.buffs.EndurancePower;
import togawasakikomod.relics.MasqueradeMask;

import java.util.ArrayList;

public class RemoveCardFromDeckAction extends AbstractGameAction {
    private final AbstractCard card;
    private boolean removeCardFromDeck = true;
    private boolean enduranceCheck = true;
    private boolean masqueradeCheck = true;
    private  boolean isForced = false;

    public RemoveCardFromDeckAction(AbstractCard card) {
        this.card = card;
    }

    public RemoveCardFromDeckAction(AbstractCard card, boolean removeActualCard,boolean enduranceCheck ,boolean masquerade) {
        this.card = card;
        this.removeCardFromDeck = removeActualCard;
        this.enduranceCheck = enduranceCheck;
        this.masqueradeCheck = masquerade;
    }

    public void update() {
        if(card.hasTag(CustomEnumPatch.TOGAWASAKIKO_UTOPIA)){
            AbstractDungeon.player.drawPile.group.remove(card);
            AbstractDungeon.player.discardPile.group.remove(card);
            AbstractDungeon.player.hand.group.remove(card);
            //AbstractDungeon.getCurrRoom().souls.remove(card);
            //AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, false));
            addToTop(new WaitAction(0.25f));
            //card.use(AbstractDungeon.player,AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true));
            this.addToBot(new NewQueueCardAction(card, true, false, true));
            this.isDone = true;
            return;
        }

        if (card.cardID.equals("Necronomicurse") ||
                card.cardID.equals("CurseOfTheBell") ||
                card.cardID.equals("AscendersBane") ||
                card.hasTag(CustomEnumPatch.TOGAWASAKIKO_UNREMOVEABLE) ||
                CardModifierManager.hasModifier(card, UnremoveableModifier.ID)
        ) {
            this.isDone = true;
            return;
        }

        CardGroup purgeable = AbstractDungeon.player.masterDeck.getPurgeableCards();
        AbstractCard actualCard = StSLib.getMasterDeckEquivalent (card);
        if(actualCard!=null && removeCardFromDeck && purgeable.contains(actualCard)){
            if(!actualCard.tags.contains(CustomEnumPatch.TOGAWASAKIKO_UNREMOVEABLE)){
                AbstractDungeon.player.masterDeck.removeCard(actualCard);
            }
        }
        AbstractDungeon.player.drawPile.group.remove(card);
        AbstractDungeon.player.drawPile.refreshHandLayout();
        AbstractDungeon.player.hand.group.remove(card);
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.discardPile.group.remove(card);
        AbstractDungeon.player.discardPile.refreshHandLayout();

        ArrayList<CardQueueItem> targetItems = new ArrayList<>();
        for(CardQueueItem item : AbstractDungeon.actionManager.cardQueue){
            if(item.card == card){
                targetItems.add(item);
            }
        }

        for (CardQueueItem item : targetItems){
            AbstractDungeon.actionManager.cardQueue.remove(item);
        }

        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));

        if(enduranceCheck){
            EndurancePower.CheckForEndurance();
        }

        if(masqueradeCheck){
            ArrayList<AbstractCard> derp = new ArrayList<>(AbstractDungeon.player.hand.group);
            derp.addAll(AbstractDungeon.player.discardPile.group);
            derp.addAll(AbstractDungeon.player.drawPile.group);
            for(AbstractCard card : derp){
                if(card.cardID.equals(MasqueradeRhapsodyRequest.ID)){
                    addToTop(new IncreaseMiscDamageAction(card.uuid,card.misc,card.magicNumber));
                }
            }
        }

        if(AbstractDungeon.player.hasRelic(MasqueradeMask.ID)){
            AbstractRelic mm = AbstractDungeon.player.getRelic(MasqueradeMask.ID);
            ((MasqueradeMask)mm).OnRemoveCard();
        }

        this.isDone = true;
    }
}
