package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import javassist.CtBehavior;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.relics.CuteAnimalBandAid;

import java.util.*;
import java.util.function.Consumer;

public class ObtainRewardEventPatch {
    private  static final List<Consumer<RewardItem>> consumers = new ArrayList<>();

    public static void add(Consumer<RewardItem> consumer) {
        consumers.add(consumer);
    }

    public static void remove(Consumer<RewardItem> consumer) {
        consumers.remove(consumer);
    }

    public static void removeAll() {
        consumers.clear();
    }

    public static void invoke(RewardItem arg) {
        consumers.forEach(consumer -> consumer.accept(arg));
    }

    @SpirePatch(clz = RewardItem.class, method = "claimReward")
    public static class ObtainRewardEventPatcher{
        public static void Postfix(RewardItem __instance)
        {
            if(__instance.type!= RewardItem.RewardType.CARD){
            for(AbstractRelic relic : AbstractDungeon.player.relics){
                if(Objects.equals(relic.relicId, CuteAnimalBandAid.ID)){
                    AbstractDungeon.player.heal(1);
                    return;
                }
            }
            }
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "takeReward")
    public static class Comon{
        @SpireInsertPatch(locator= Locator.class)
        public static SpireReturn<Integer> Please(CardRewardScreen __instance)
        {
            for(AbstractRelic relic : AbstractDungeon.player.relics){
                if(Objects.equals(relic.relicId, CuteAnimalBandAid.ID)){
                    AbstractDungeon.player.heal(1);
                }
            }
            return SpireReturn.Continue();
        }

        public static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "remove");
                return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
            }
        }
    }


}
