//package togawasakikomod.patches;
//
//
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//import javassist.CtBehavior;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import togawasakikomod.TogawaSakikoMod;
//import togawasakikomod.cards.SakikoDeck.Skills.HeartsBarrier;
//import togawasakikomod.relics.WarmthInfusedPorcelainCup;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class WarmthInfusedPorcelainCupPatch{
//
//    public static final Logger logger = LogManager.getLogger(TogawaSakikoMod.modID);
//
//    private final static float chance = 0.5f;
//   // @SpirePatch(clz = AbstractDungeon.class, method = "getRewardCards")
//    public static class Patch{
//        @SpireInsertPatch(
//                rloc=1851,
//                localvars={"retVal"}
//        )
//        public static SpireReturn<Object> Insert(AbstractDungeon __instance, @ByRef ArrayList<AbstractCard>[] retVal){
//            logger.error("WIPC-Triggered");
//            if(!AbstractDungeon.player.hasRelic(WarmthInfusedPorcelainCup.ID)){
//                logger.error("WIPC-Doesn;t have relic");
//                return  SpireReturn.Continue();
//            }
//            if(retVal[0].isEmpty()){
//                logger.error("WIPC-reward empty");
//                return SpireReturn.Continue();}
//            boolean alreadyHave = false;
//            for(AbstractCard card : retVal[0]){
//                if(Objects.equals(card.cardID, HeartsBarrier.ID)){
//                    alreadyHave = true;
//                    logger.error("WIPC-Already have");
//                }
//            }
//
//            if(!alreadyHave){
//                float rng =  AbstractDungeon.cardRng.random(0.0f,1.0f);
//                if(rng >=chance){
//                    retVal[0].remove(0);
//                    retVal[0].add(new HeartsBarrier());
//                }
//                logger.error("WIPC-Add");
//            }
//            return SpireReturn.Continue();
//        }
//    }
//}