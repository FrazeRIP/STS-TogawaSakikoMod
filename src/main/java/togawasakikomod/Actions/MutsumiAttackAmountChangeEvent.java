package togawasakikomod.Actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;
import togawasakikomod.monsters.bosses.avemujica.WakabaMutsumiBoss;

public class MutsumiAttackAmountChangeEvent extends AbstractGameAction
{
    @Override
    public void update() {
        if(AbstractDungeon.getCurrRoom()!=null && AbstractDungeon.getCurrRoom().monsters!=null){
            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof WakabaMutsumiBoss){
                    if(monster.isDead){continue;}
                    WakabaMutsumiBoss mutsumi = (WakabaMutsumiBoss)monster;
                    mutsumi.updateBlockRemoveAttackAmount();
                }
            }
        }
        this.isDone = true;
    }
}
