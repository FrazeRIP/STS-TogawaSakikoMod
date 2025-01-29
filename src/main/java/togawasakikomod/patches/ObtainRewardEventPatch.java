package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import togawasakikomod.relics.CuteAnimalBandAid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
            for(AbstractRelic relic : AbstractDungeon.player.relics){
                if(Objects.equals(relic.relicId, CuteAnimalBandAid.ID)){
                    AbstractDungeon.player.heal(1);
                    return;
                }
            }
        }
    }
}
