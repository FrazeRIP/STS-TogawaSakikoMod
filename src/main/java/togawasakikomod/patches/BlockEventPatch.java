package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import javassist.CtBehavior;
import togawasakikomod.Actions.MutsumiAttackAmountChangeEvent;
import togawasakikomod.monsters.bosses.FinalBossMonster;
import togawasakikomod.monsters.bosses.avemujica.WakabaMutsumiBoss;
import togawasakikomod.relics.CuteAnimalBandAid;
import togawasakikomod.saveable.KingsSaveable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BlockEventPatch {
    @SpirePatch(clz = AbstractCreature.class, method = "loseBlock",paramtypez ={int.class,boolean.class})
    public static class LoseBlockEventPatcher{
        public static void Postfix(AbstractCreature __instance,int amount, boolean noAnimation)
        {
            if(AbstractDungeon.getCurrRoom()!=null && AbstractDungeon.getCurrRoom().monsters!=null){
                AbstractDungeon.actionManager.addToTop(new MutsumiAttackAmountChangeEvent());
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "addBlock", paramtypez = {int.class})
    public static class AddBlockEventPatcher{
        public static void Postfix(AbstractCreature __instance,int blockAmount)
        {
            if(AbstractDungeon.getCurrRoom()!=null && AbstractDungeon.getCurrRoom().monsters!=null){
                AbstractDungeon.actionManager.addToTop(new MutsumiAttackAmountChangeEvent());
            }
        }
    }
}
