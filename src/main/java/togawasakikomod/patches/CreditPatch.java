package togawasakikomod.patches;


import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.credits.CreditLine;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.megacrit.cardcrawl.localization.CreditStrings;
import com.megacrit.cardcrawl.neow.NeowRoom;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.saveable.KingsSaveable;

import java.util.ArrayList;
//
//@SpirePatch(
//        clz = CreditsScreen.class,
//        method = SpirePatch.CONSTRUCTOR
//)
//public class CreditPatch {
//    @SpirePrefixPatch
//    public static void CreditPatchContent(CreditsScreen __instance,
//                                          @ByRef ArrayList<CreditLine>[] ___lines,
//                                          @ByRef float[] ___tmpY){
//
//        if(___lines[0] == null){___lines[0] = new ArrayList<>();}
//
//        String id = TogawaSakikoMod.makeID("DEV");
//        CreditStrings str = CardCrawlGame.languagePack.getCreditString(id);
//        ___lines[0].add(new CreditLine(str.HEADER, ___tmpY[0] -= 150.0F, true));
//        for (int i = 0; i < str.NAMES.length; i++){
//            ___lines[0].add(new CreditLine(str.NAMES[i], ___tmpY[0] -= 45.0F, false));
//        }
//
//        id = TogawaSakikoMod.makeID("SPECIAL_THANKS");
//        str = CardCrawlGame.languagePack.getCreditString(id);
//        ___lines[0].add(new CreditLine(str.HEADER, ___tmpY[0] -= 150.0F, true));
//        for (int i = 0; i < str.NAMES.length; i++){
//            ___lines[0].add(new CreditLine(str.NAMES[i], ___tmpY[0] -= 45.0F, false));
//        }
//
//        id = TogawaSakikoMod.makeID("ORIGINAL_DEV");
//        str = CardCrawlGame.languagePack.getCreditString(id);
//        ___lines[0].add(new CreditLine(str.HEADER, ___tmpY[0] -= 150.0F, true));
//        for (int i = 0; i < str.NAMES.length; i++){
//            ___lines[0].add(new CreditLine(str.NAMES[i], ___tmpY[0] -= 45.0F, false));
//        }
//        System.out.println("---------------------Credit Changed");
//    }
//}
