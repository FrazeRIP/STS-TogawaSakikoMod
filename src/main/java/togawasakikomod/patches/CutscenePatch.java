package togawasakikomod.patches;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.screens.SpeicialVictoryScreen;

@SpirePatch(clz = Cutscene.class, method = "openVictoryScreen")
public class CutscenePatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> CutscenePatchContent(Cutscene __instance){
        if(AbstractDungeon.player instanceof TogawaSakiko){
            GameCursor.hidden = false;
            AbstractDungeon.victoryScreen = new SpeicialVictoryScreen(null);
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
