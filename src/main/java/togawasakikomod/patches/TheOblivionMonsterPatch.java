package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import togawasakikomod.Actions.MutsumiAttackAmountChangeEvent;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.dungeons.TheOblivion;
import togawasakikomod.monsters.oblivion.TheOblivionMonsterGroup;
import togawasakikomod.monsters.oblivion.bosses.avemujica.MisumiUikaBoss;

import java.util.Objects;
public class TheOblivionMonsterPatch {
    @SpirePatch(clz = MonsterHelper.class, method = "getEncounterName", paramtypez = {String.class})
    public static class TheOblivionMonsterNamePatch {
        public static String Postfix(String __result, String ___key)
        {
            if(Objects.equals(___key, TheOblivion.ID)){
                MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(TheOblivion.ID);
                return monsterStrings.NAME;
            }
            return __result;
        }
    }

    @SpirePatch(clz = MonsterHelper.class, method = "getEncounter", paramtypez = {String.class})
    public static class TheOblivionMonsterGroupPatch {
        public static MonsterGroup Postfix(MonsterGroup __result, String ___key)
        {
            if(Objects.equals(___key, TheOblivion.ID)){
                return new TheOblivionMonsterGroup();
            }
            return __result;
        }
    }
}
