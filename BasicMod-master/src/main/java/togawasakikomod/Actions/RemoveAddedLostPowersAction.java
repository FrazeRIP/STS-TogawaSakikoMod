//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.character.TogawaSakiko;

public class RemoveAddedLostPowersAction extends AbstractGameAction {
    private boolean addPrevious = false;

    public RemoveAddedLostPowersAction(boolean addPrevious) {
        this.addPrevious = addPrevious;
    }

    public void update() {
        for(AbstractPower power : TogawaSakiko.CurrentRoundPowerLost){
            if(power.type == AbstractPower.PowerType.BUFF){
                addToTop(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,power.ID,power.amount));
            }
        }

        if(addPrevious){
            for(AbstractPower power : TogawaSakiko.LastRoundPowerLost){
                if(power.type == AbstractPower.PowerType.BUFF){
                    addToTop(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,power.ID,power.amount));
                }
            }
        }
        this.isDone = true;
    }
}
