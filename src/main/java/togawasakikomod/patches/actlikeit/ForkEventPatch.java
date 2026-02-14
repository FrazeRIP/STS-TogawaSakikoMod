package togawasakikomod.patches.actlikeit;


import actlikeit.events.GetForked;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.character.TogawaSakiko;

import java.util.ArrayList;


@SpirePatch(
        clz = GetForked.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez={  boolean.class }
)
public class ForkEventPatch {
    private static final String imgUrl = TogawaSakikoMod.imagePath("events/Fork.png");
    private static final EventStrings eventString = CardCrawlGame.languagePack.getEventString(TogawaSakikoMod.makeID("GetForked"));

    @SpirePostfixPatch
    public static void GetForkedConstructor(
            GetForked __instance,
            boolean ___afterdoor,
            @ByRef String[] ___body){
        if(AbstractDungeon.player instanceof TogawaSakiko){
            __instance.imageEventText.loadImage(imgUrl);
            ___body[0] = eventString.DESCRIPTIONS[0];
        }
    }
}
