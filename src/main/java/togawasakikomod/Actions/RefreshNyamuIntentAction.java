package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;

public class RefreshNyamuIntentAction extends AbstractGameAction {
    YuutenjiNyamuBoss m;

   public RefreshNyamuIntentAction(YuutenjiNyamuBoss m){
       this.m = m;
   }

    @Override
    public void update() {
        if(!m.isDeadOrEscaped()){
            m.rollMove();
            m.createIntent();
        }
       this.isDone = true;
    }
}
