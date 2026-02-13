package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import togawasakikomod.TogawaSakikoMod;

import java.util.ArrayList;
import java.util.Objects;

@SpirePatch(clz = MusicMaster.class, method = "playTempBgmInstantly", paramtypez = {String.class, boolean.class})
public class MusicMasterPatch {
    public static final Logger logger = LogManager.getLogger(TogawaSakikoMod.modID);
    @SpirePrefixPatch
    public static SpireReturn<Void> MusicMasterEndPatch(MusicMaster __instance, String ___key, boolean ___loop, ArrayList<MainMusic> ___mainTrack){
        if(TogawaSakikoMod.isGameClear && Objects.equals(___key, "CREDITS")){
            TogawaSakikoMod.isGameClear = false;
            for (MainMusic m : ___mainTrack){
                m.silenceInstantly();
            }
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
