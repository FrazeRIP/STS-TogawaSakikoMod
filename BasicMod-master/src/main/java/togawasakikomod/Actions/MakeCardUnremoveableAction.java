package togawasakikomod.Actions;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import togawasakikomod.modifiers.UnremoveableModifier;
import togawasakikomod.patches.CustomEnumPatch;

public class MakeCardUnremoveableAction  extends AbstractGameAction {

    AbstractCard card;
    public MakeCardUnremoveableAction(AbstractCard card){
        this.card = card;
    }

    @Override
    public void update() {
        if(!CardModifierManager.hasModifier(card, UnremoveableModifier.class.getSimpleName())){
            CardModifierManager.addModifier(card,new UnremoveableModifier());
            card.tags.add(CustomEnumPatch.TOGAWASAKIKO_UNREMOVEABLE);
        }
        AbstractCard actualCard = StSLib.getMasterDeckEquivalent (card);
        if(actualCard!=null && !CardModifierManager.hasModifier(actualCard, UnremoveableModifier.class.getSimpleName())){
            CardModifierManager.addModifier(actualCard,new UnremoveableModifier());
            actualCard.tags.add(CustomEnumPatch.TOGAWASAKIKO_UNREMOVEABLE);
        }
        this.isDone = true;
    }

}
