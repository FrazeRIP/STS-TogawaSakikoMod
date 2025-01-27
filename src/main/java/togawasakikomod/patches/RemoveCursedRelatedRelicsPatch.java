package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.unlock.relics.silent.DuvuDollUnlock;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

//@SpirePatch(clz = RelicLibrary.class, method = "add", paramtypez = {AbstractRelic.class})
//public class RemoveCursedRelatedRelicsPatch {
//    @SpirePrefixPatch()
//    public static SpireReturn<Void> RemoveCursedRelatedRelics(RelicLibrary __inst, AbstractRelic relic) {
//        if(Objects.equals(relic.relicId, DuVuDoll.ID) || Objects.equals(relic.relicId, DarkstonePeriapt.ID)){
//            return SpireReturn.Return();
//        }
//        return SpireReturn.Continue();
//    }
//}



//@SpirePatch(clz = RelicLibrary.class, method = "initialize")
//public class RemoveCursedRelatedRelicsPatch {
//        @SpireInsertPatch(
//                locator = SpireInsertLocator.class
//        )
//        public static void ThisIsAnInsert(RelicLibrary _inst,
//                                          HashMap<String, AbstractRelic> __sharedRelics,
//                                          ArrayList<AbstractRelic> __commonList,
//                                          ArrayList<AbstractRelic> __uncommonList,
//                                          ArrayList<AbstractRelic> __rareList
//                                          ) {
//            __sharedRelics.remove(DuVuDoll.ID);
//            __sharedRelics.remove(DarkstonePeriapt.ID);
//            removeItemsOfType(__commonList,DuVuDoll.class);
//            removeItemsOfType(__commonList,DarkstonePeriapt.class);
//            removeItemsOfType(__uncommonList,DuVuDoll.class);
//            removeItemsOfType(__uncommonList,DarkstonePeriapt.class);
//            removeItemsOfType(__rareList,DuVuDoll.class);
//            removeItemsOfType(__rareList,DarkstonePeriapt.class);
//        }
//
//    public static class ReduceLocator
//            extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctBehavior) throws Exception {
//            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(RelicLibrary.class, "initialize");
//            return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher); }
//    }
//
//    public static void removeItemsOfType(ArrayList<AbstractRelic> list, Class<?> typeToRemove) {
//        Iterator<AbstractRelic> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            Object item = iterator.next();
//            if (typeToRemove.isInstance(item)) {
//                iterator.remove();
//            }
//        }
//    }
//}








