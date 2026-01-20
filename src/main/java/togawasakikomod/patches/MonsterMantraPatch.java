package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import javassist.CtBehavior;
import togawasakikomod.powers.monsters.MonsterDivinityPower;
import togawasakikomod.relics.CuteAnimalBandAid;
import togawasakikomod.saveable.KingsSaveable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class MonsterMantraPatch {
    @SpirePatch(clz = MantraPower.class, method = "stackPower")
    public static class Comon{
        @SpireInsertPatch(locator= Locator.class)
        public static SpireReturn<Void> update(MantraPower __instance, int amount)
        {
            if(__instance.owner instanceof AbstractMonster){
                AbstractMonster monster = (AbstractMonster) __instance.owner;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster,monster, new MonsterDivinityPower(monster),0));
                __instance.amount -= 10;
                if (__instance.amount <= 0)
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, "Mantra"));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        public static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(MantraPower.class, "addToTop");
                return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
            }
        }
    }
}
