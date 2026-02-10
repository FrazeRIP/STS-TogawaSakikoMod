package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;

public class RefreshMonsterIntentAction extends AbstractGameAction {
    YuutenjiNyamuBoss m;

   public RefreshMonsterIntentAction(YuutenjiNyamuBoss m){
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
