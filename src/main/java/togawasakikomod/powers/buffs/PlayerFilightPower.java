package togawasakikomod.powers.buffs;

import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;

public class PlayerFilightPower extends FlightPower {

    public PlayerFilightPower(AbstractCreature owner, int amount) {
        super(owner, amount);
    }

    @Override
    public void onRemove() {
        if(owner instanceof AbstractPlayer) {
            this.addToBot(new PressEndTurnButtonAction());
        }else{
            this.addToBot(new ChangeStateAction((AbstractMonster)this.owner, "GROUNDED"));
        }
    }
}
