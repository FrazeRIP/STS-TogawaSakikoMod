package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.unlock.relics.silent.DuvuDollUnlock;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

@SpirePatch(clz = RelicLibrary.class, method = "add", paramtypez = {AbstractRelic.class})
public class RemoveCursedRelatedRelicsPatch {
    @SpirePrefixPatch()
    public static SpireReturn<Void> RemoveCursedRelatedRelics(RelicLibrary __inst, @ByRef AbstractRelic relic) {
        if(Objects.equals(relic.relicId, DuVuDoll.ID) || Objects.equals(relic.relicId, DarkstonePeriapt.ID)){
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}

//@SpirePatch(clz = RelicLibrary.class, method = "initialize")
//public class RemoveCursedRelatedRelicsPatch {
//        @SpireInsertPatch(
//                locator = SpireInsertLocator.class
//        )
//        public static void ThisIsAnInsert(RelicLibrary __inst) {
//
//        }
//
//        public static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//                Matcher matcher = new Matcher.MethodCallMatcher(RelicLibrary.class, "sortLists");
//                return LineFinder.findInOrder(ctMethodToPatch, matcher);
//            }
//        }
//}
