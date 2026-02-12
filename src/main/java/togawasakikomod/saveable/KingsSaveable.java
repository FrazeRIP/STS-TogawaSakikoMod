package togawasakikomod.saveable;

import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import togawasakikomod.relics.CuteAnimalBandAid;

import java.lang.reflect.Type;
import java.util.Objects;

public class KingsSaveable implements CustomSavable<Boolean> {
    public static boolean IsKing = false;

    public static void SetKing(boolean isKing){
        IsKing = isKing;
    }

    @Override
    public Boolean onSave() {
        System.out.println("---------------------Save:"+KingsSaveable.class.getSimpleName()+":"+IsKing);
        return IsKing;
    }

    @Override
    public void onLoad(Boolean isKing) {
        SetKing(isKing);
        System.out.println("---------------------Load:"+KingsSaveable.class.getSimpleName()+":"+IsKing);
    }

    @Override
    public Type savedType() {
        return Boolean.TYPE;
    }


    @SpirePatch(clz = AbstractScene.class, method = "nextRoom", paramtypez = {AbstractRoom.class})
    public static class KingsPatch{
        public static void Postfix(AbstractScene __instance)
        {
            //KingsSaveable.SetKing(false);
            //System.out.println("---------------------Next Room:"+KingsSaveable.class.getSimpleName()+":"+IsKing);
        }
    }
}
