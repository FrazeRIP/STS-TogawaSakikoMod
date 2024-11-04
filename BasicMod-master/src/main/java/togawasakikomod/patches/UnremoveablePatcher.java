package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import togawasakikomod.powers.debuffs.DolorisPower;

import java.util.ArrayList;

public class UnremoveablePatcher {
    @SpirePatch(clz = CardGroup.class, method = "getPurgeableCards")
    public static class RemoveUnremoveableFromPurge{
        public static CardGroup Postfix(CardGroup __result, CardGroup __instance)
        {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            for(AbstractCard card : __result.group){
                if(card.hasTag(CustomEnumPatch.TOGAWASAKIKO_UNREMOVEABLE)){
                    cards.add(card);
                }
            }
            for(AbstractCard card :cards){
                __result.group.remove(card);
            }

            return __result;
        }
    }
}
