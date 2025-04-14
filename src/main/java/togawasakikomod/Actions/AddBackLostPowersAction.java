//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.character.TogawaSakiko;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddBackLostPowersAction extends AbstractGameAction {
    private boolean addPrevious = false;

    public AddBackLostPowersAction(boolean addPrevious) {
        this.addPrevious = addPrevious;
    }

    public void update() {
        Debug();

        List<AbstractPower> powers = new ArrayList<>();
        for (AbstractPower power :TogawaSakiko.CurrentRoundPowerLost){
            if(power.type == AbstractPower.PowerType.BUFF){
                try{
                    AbstractPower copy= ((CloneablePowerInterface) power).makeCopy();
                    if(copy!=null){
                        powers.add(copy);
                    }
                }catch (Exception e){
                    System.out.println("Power: unable to copy "+power.ID);
                }
            }
        }


        if(addPrevious){
            for(AbstractPower power : TogawaSakiko.LastRoundPowerLost){
                if(power.type == AbstractPower.PowerType.BUFF){
                    boolean isFound = false;
                    for (AbstractPower checkPower : powers){
                        if(power.amount>0 && Objects.equals(power.ID, checkPower.ID)){
                            System.out.println("Power: Stack "+power.ID+" previous:"+checkPower.amount+", stack:"+power.amount);
                            checkPower.amount += power.amount;
                            isFound = true;
                            break;
                        }
                    }

                    if(!isFound){
                        System.out.println("Power: Add "+power.ID+"from previous. amount:"+power.amount);
                        powers.add(power);
                    }
                }
            }
        }

        for(AbstractPower power : powers){
            if(power.type == AbstractPower.PowerType.BUFF){
                try{
                    AbstractPower copy= ((CloneablePowerInterface) power).makeCopy();
                    if(copy!=null && copy.amount != 0){
                        addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,copy,copy.amount));
                    }
                }catch (Exception e){
                    System.out.println("Power: unable to copy "+power.ID);
                }
            }
        }

        this.isDone = true;
    }

    private void Debug(){
        StringBuilder currentList = new StringBuilder();
        for (AbstractPower power :TogawaSakiko.CurrentRoundPowerLost){
            currentList.append(power.ID).append("(").append(power.amount).append("), ");
        }
        System.out.println("Power: current list Count: "+TogawaSakiko.CurrentRoundPowerLost.size() + ". Items: "+currentList);

        StringBuilder lastRoundList = new StringBuilder();
        for (AbstractPower power :TogawaSakiko.LastRoundPowerLost){
            lastRoundList.append(power.ID).append("(").append(power.amount).append("), ");
        }
        System.out.println("Power: lastround list Count: "+TogawaSakiko.LastRoundPowerLost.size() + ". Items: "+lastRoundList);

    }
}
