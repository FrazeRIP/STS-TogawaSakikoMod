package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.Actions.MutsumiAttackAmountChangeEvent;

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
