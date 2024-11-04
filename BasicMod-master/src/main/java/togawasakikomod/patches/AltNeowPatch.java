package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.events.AltNeowEvent;

//https://github.com/Alchyr/Underspire/blob/master/src/main/java/UnderSpire/Patches/AltNeowPatch.java#L23

@SpirePatch(
        clz = NeowRoom.class,
        method = SpirePatch.CONSTRUCTOR
)
public class AltNeowPatch {
    @SpireInsertPatch( locator =  Locator.class )
    public static void ChangeEvent(NeowRoom __instance, boolean isDone)
    {
        if (AbstractDungeon.player.getCardColor() == TogawaSakiko.Meta.CARD_COLOR){
        __instance.event = new AltNeowEvent(isDone);
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractEvent.class, "onEnterRoom");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
