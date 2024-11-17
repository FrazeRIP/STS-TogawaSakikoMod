package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

import static togawasakikomod.patches.CustomEnumPatch.TOGAWASAKIKO_GRAVE;

public class GraveCardPatch {
    @SpirePatch(clz = CardGroup.class, method = "initializeDeck")
    public static class StartInGravePatch{
        @SpireInsertPatch(loc = 1052,localvars = {"placeOnTop"})
        public static void insert(CardGroup __instance, CardGroup masterDeck, @ByRef ArrayList<AbstractCard>[] placeOnTop){
            Iterator<AbstractCard> iterator = __instance.group.iterator();
            while(iterator.hasNext()){
                AbstractCard card = iterator.next();
                if(card.hasTag(TOGAWASAKIKO_GRAVE)){
                    iterator.remove();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(card,1));
                }
            }
        }
    }
}
