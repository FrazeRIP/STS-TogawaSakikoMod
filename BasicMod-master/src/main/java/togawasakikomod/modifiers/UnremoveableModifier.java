package togawasakikomod.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import togawasakikomod.patches.CustomEnumPatch;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class UnremoveableModifier extends AbstractCardModifier {

     public static String ID = makeID(UnremoveableModifier.class.getSimpleName());
     private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    @Override
    public AbstractCardModifier makeCopy() {
        return new UnremoveableModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return makeID(UnremoveableModifier.class.getSimpleName());
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(CustomEnumPatch.TOGAWASAKIKO_UNREMOVEABLE);
        card.rawDescription = card.rawDescription.replace(uiStrings.TEXT[0], "");
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
       if (rawDescription.contains(uiStrings.TEXT[1])) return rawDescription;
       return String.format(uiStrings.TEXT[0], new Object[] { rawDescription });
    }
}
