package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.dungeons.TheOblivion;
import togawasakikomod.helpers.DungeonHelper;

import java.util.Objects;

//https://github.com/QingMu01/sts-sakiko-mod/blob/main/src/main/java/com/qingmu/sakiko/patch/room/GoToDemonSakikoPatch.java

@SpirePatch(clz = ProceedButton.class, method = "goToTrueVictoryRoom")
public class TheEndGoToTheOblivionEventPatch {
    public static SpireReturn<Void> Prefix(ProceedButton __instance) {
        if(AbstractDungeon.player instanceof TogawaSakiko){
            if( Objects.equals(AbstractDungeon.lastCombatMetricKey, "The Heart")){
                DungeonHelper.goToAct(TheOblivion.ID);
                return SpireReturn.Return();
            }else if(Objects.equals(AbstractDungeon.lastCombatMetricKey, TheOblivion.ID)){
                return SpireReturn.Continue();
            }
        }
        return SpireReturn.Continue();
    }
}
