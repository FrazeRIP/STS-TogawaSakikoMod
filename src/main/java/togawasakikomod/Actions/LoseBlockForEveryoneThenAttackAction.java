package togawasakikomod.Actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.monsters.bosses.avemujica.WakabaMutsumiBoss;

public class LoseBlockForEveryoneThenAttackAction extends AbstractGameAction
{
    AbstractCreature target;
    AbstractCreature source;
    DamageInfo.DamageType damageType;
    AttackEffect effect;

    public LoseBlockForEveryoneThenAttackAction(AbstractCreature target, AbstractCreature source, DamageInfo.DamageType damageType , AttackEffect effect ){
        this.target = target;
        this.source = source;
        this.damageType = damageType;
        this.effect = effect;
    }

    @Override
    public void update() {
        //calculate total block
        int totalBlock = 0;
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.isDead){continue;}
            totalBlock += monster.currentBlock;
            monster.loseBlock();
        }
        totalBlock += AbstractDungeon.player.currentBlock;
        AbstractDungeon.player.loseBlock();

        //deal damage
        addToTop(new DamageAction(target,new DamageInfo(source,totalBlock,damageType),effect));
        addToTop((AbstractGameAction) new AnimateSlowAttackAction(source));

        //remove all blocks
        this.isDone = true;
    }
}
