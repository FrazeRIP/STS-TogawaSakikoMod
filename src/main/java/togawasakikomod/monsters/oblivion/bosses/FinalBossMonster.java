package togawasakikomod.monsters.oblivion.bosses;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.util.TriConsumer;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.monsters.SurroundedMonster;

import java.util.ArrayList;
import java.util.List;

public abstract class FinalBossMonster extends SurroundedMonster implements TriConsumer<AbstractPower, AbstractCreature, AbstractCreature> {

    private ArrayList<AbstractPower> currentRoundPowerGains = new ArrayList<>();
    private ArrayList<AbstractPower> previousRoundPowerGains = new ArrayList<>();

    public FinalBossMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        TogawaSakikoMod.addListener(this::accept);
    }

    @Override
    public abstract void takeTurn();

    @Override
    protected abstract void getMove(int i) ;

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().playBgmInstantly("Haruhikage");
        super.usePreBattleAction();
    }

    @Override
    public void applyStartOfTurnPowers() {
        previousRoundPowerGains = new ArrayList<>(currentRoundPowerGains);
        currentRoundPowerGains.clear();
        super.applyStartOfTurnPowers();
    }

    public void accept(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        if(target != this){return;}
        if(abstractPower.type != AbstractPower.PowerType.BUFF){return;}
        if(!(abstractPower instanceof CloneablePowerInterface)){return;}

        AbstractPower power =((CloneablePowerInterface) abstractPower).makeCopy();
        power.amount = Math.abs(abstractPower.amount);
        power.type = AbstractPower.PowerType.BUFF;

        boolean isFound = false;
        for(AbstractPower checkPower : currentRoundPowerGains){
            if(checkPower.ID.equals(power.ID)){
                checkPower.amount += power.amount;
                isFound = true;
                System.out.println("Power: Add "+power.amount +" to" + power.ID + ".");
                break;
            }
        }

        if(!isFound){
            currentRoundPowerGains.add(power);
            System.out.println("Power: Add " + power.ID + " to pool.");
        }
    }

    public void TemporalLongingBehavior(){
        List<AbstractPower> powers = new ArrayList<>();
        for (AbstractPower power :currentRoundPowerGains){
            addToTop(new ApplyPowerAction(this,this,power,power.amount));
        }

        for (AbstractPower power :previousRoundPowerGains){
            addToTop(new ApplyPowerAction(this,this,power,power.amount));
        }
    }
}
