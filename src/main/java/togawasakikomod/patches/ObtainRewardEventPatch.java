package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import javassist.CtBehavior;
import togawasakikomod.relics.CuteAnimalBandAid;
import togawasakikomod.saveable.KingsSaveable;

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
        public static void Prefix(RewardItem __instance)
        {
            if(__instance.type== RewardType.CARD){
                return;
            }else if(__instance.type == RewardType.POTION){
                boolean hasSlot =false;
                for(AbstractPotion potion : AbstractDungeon.player.potions){
                    if(potion instanceof PotionSlot){
                        hasSlot = true;
                        break;
                    }
                }
                if(!hasSlot){
                    return;
                }
            }

            for(AbstractRelic relic : AbstractDungeon.player.relics){
                if(Objects.equals(relic.relicId, CuteAnimalBandAid.ID)){
                    AbstractDungeon.player.heal(1);
                    return;
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
            KingsSaveable.SetKing(false);
            System.out.println("---------------------takeReward:"+KingsSaveable.class.getSimpleName()+":"+KingsSaveable.IsKing);
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
