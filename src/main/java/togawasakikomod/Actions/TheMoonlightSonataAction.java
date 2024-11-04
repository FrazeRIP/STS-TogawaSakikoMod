package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import togawasakikomod.relics.TheThirdMovement;

public class TheMoonlightSonataAction extends AbstractGameAction {

    AbstractCreature m;
    DamageInfo dmgInfo;

    public TheMoonlightSonataAction(AbstractCreature m,DamageInfo info){
        this.m = m;
        this.dmgInfo = info;
    }

    @Override
    public void update() {
        addToTop(new KillGainPurgeRewardAction(m,dmgInfo));

        if(AbstractDungeon.player.hasRelic(TheThirdMovement.ID)){
            AbstractRelic relic = AbstractDungeon.player.getRelic(TheThirdMovement.ID);
            if(!relic.usedUp && relic.counter>0){
                TheThirdMovement movement = (TheThirdMovement)relic;
                movement.flash();
                movement.reduceCounter(1);
            }
        }

        this.isDone= true;
    }
}
