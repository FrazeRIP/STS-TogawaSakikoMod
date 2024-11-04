package togawasakikomod.patches;
import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import javassist.CtBehavior;
import togawasakikomod.character.TogawaSakiko;

import java.util.ArrayList;
import java.util.Objects;

public class ReducedPowerRecorderPatch {
    @SpirePatch (clz = ReducePowerAction.class, method = "update")
    public static class RecordReducedPowerInstanceVer{
        @SpireInsertPatch( locator= ReduceLocator.class, localvars = {"reduceMe"})
        public static void DoIt(ReducePowerAction _inst, AbstractPower reduceMe) {
            if(reduceMe!=null&& reduceMe.owner == AbstractDungeon.player&& reduceMe.type == AbstractPower.PowerType.BUFF){
                AbstractPower power = null;
                if(reduceMe instanceof CloneablePowerInterface){
                    power =((CloneablePowerInterface) reduceMe).makeCopy();
                    power.amount = _inst.amount;
                }

                if(power!=null){
                    boolean isFound = false;
                    for(AbstractPower checkPower : TogawaSakiko.CurrentRoundPowerLost){
                        if(checkPower.ID.equals(power.ID)){
                            checkPower.amount += power.amount;
                            isFound = true;
                            System.out.println("Power: Add "+power.amount +" to" + power.ID + ".");
                            break;
                        }
                    }

                    if(!isFound){
                        TogawaSakiko.CurrentRoundPowerLost.add(power);
                        System.out.println("Power: Add " + power.ID + " to pool.");
                    }

                }else{
                    System.out.println("Power: Error: Cannot create instance of " + reduceMe.ID);
                }
            }
        }

        public static class ReduceLocator
              extends SpireInsertLocator {
             public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "updateDescription");
              return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
                 }
            }
    }


    @SpirePatch (clz = RemoveSpecificPowerAction.class, method = "update")
    public static class RecordRemovedPowerInstanceVar{
        @SpireInsertPatch( locator= RecordRemovedPowerInstanceVar.RemoveLocator.class, localvars = {"removeMe"})
        public static void Okay(RemoveSpecificPowerAction _inst, AbstractPower removeMe) {
            if(removeMe!=null&& removeMe.owner == AbstractDungeon.player && removeMe.type == AbstractPower.PowerType.BUFF){
                AbstractPower power = null;
                if(removeMe instanceof CloneablePowerInterface){
                    power =((CloneablePowerInterface) removeMe).makeCopy();
                }

                if(power!=null){
                    boolean isFound = false;
                    for(AbstractPower checkPower : TogawaSakiko.CurrentRoundPowerLost){
                        if(Objects.equals(checkPower.ID, power.ID)){
                            checkPower.amount += power.amount;
                            isFound = true;
                            System.out.println("Power: Add "+power.amount +" to" + power.ID + ".");
                            break;
                        }
                    }

                    if(!isFound){  TogawaSakiko.CurrentRoundPowerLost.add(power);}

                    System.out.println("Power: Add " + power.ID + " to pool.");
                }else{
                    System.out.println("Power: Error: Cannot create instance of " + removeMe.ID);
                }
            }
        }

        public static class RemoveLocator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
                return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
            }
        }
    }


    @SpirePatch (clz = RegenAction.class, method = "update")
    public static class RecordRegenLostInstanceVar{
        @SpireInsertPatch( locator= RecordRegenLostInstanceVar.RemoveLocator.class)
        public static void What(RegenAction _inst) {
            AbstractPower power = _inst.target.getPower("Regeneration");
            if(power!=null && power.amount>1){
                boolean isFound = false;
                for(AbstractPower checkPower : TogawaSakiko.CurrentRoundPowerLost){
                    if(Objects.equals(checkPower.ID, power.ID)){
                        checkPower.amount += 1;
                        isFound = true;
                        System.out.println("Power: Add "+power.amount +" to" + power.ID + ".");
                        break;
                    }
                }
                if(!isFound){
                        AbstractPower copy = new RegenPower(power.owner,1);
                        TogawaSakiko.CurrentRoundPowerLost.add(copy);
                        System.out.println("Power: Add " + power.ID + " to pool. Amount:"+copy.amount);
                }
            }
        }

        public static class RemoveLocator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "getPower");
                return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
            }
        }
    }
}
