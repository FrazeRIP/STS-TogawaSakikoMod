package togawasakikomod.Actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;
import togawasakikomod.monsters.bosses.FinalBossMonster;

public class TemporalLongingAction extends AbstractGameAction
{
    @Override
    public void update() {
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.isDead){continue;}
            if(monster instanceof FinalBossMonster){
                FinalBossMonster boss = (FinalBossMonster) monster;
                boss.TemporalLongingBehavior();
            }
        }
        this.isDone = true;
    }
}
