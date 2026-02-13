package togawasakikomod.patches.actlikeit;


import actlikeit.events.GetForked;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import togawasakikomod.TogawaSakikoMod;


@SpirePatch(
        clz = GetForked.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez={  boolean.class }
)
public class ForkEventPatch {
    private static final String imgUrl = TogawaSakikoMod.imagePath("events/Fork.png");

    @SpirePostfixPatch
    public static void GetForkedConstructor(GetForked __instance, boolean afterdoor){
        __instance.imageEventText.loadImage(imgUrl);
    }
}
