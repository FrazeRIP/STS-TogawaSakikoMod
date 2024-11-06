package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ForcePlayCardAction extends AbstractGameAction {

    AbstractCard card;

    public ForcePlayCardAction(AbstractCreature target, AbstractCard card){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.card = card;
    }
    public ForcePlayCardAction(AbstractCreature target, AbstractCard card,boolean purgeOnUse){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.card = card;
        this.card.purgeOnUse = purgeOnUse;
    }


    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (card == null) {
                this.isDone = true;
                return;
            }

                AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.exhaustOnUseOnce = card.exhaust ||card.exhaustOnUseOnce;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = (float)Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                if(target == null){
                    target = AbstractDungeon.getRandomMonster();
                }
                this.addToTop(new NewQueueCardAction(card, this.target, true, true));
                this.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }


            this.isDone = true;
        }

    }

}
