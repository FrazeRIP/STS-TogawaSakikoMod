package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoDrawPower;

//From The Hermit Mod
public class BandInvitationAction extends AbstractGameAction {
    private AbstractPlayer p;

    private AbstractCard.CardType typeToCheck;

    private int energy;

    private int tracker = 0;

    public BandInvitationAction(int energy) {
        this.p = AbstractDungeon.player;
        setValues((AbstractCreature)this.p, (AbstractCreature)this.p);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.typeToCheck = AbstractCard.CardType.CURSE;
        this.energy = energy;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if(this.p.hand.size() >= 10){
                this.p.createHandIsFullDialog();
                this.isDone = true;
                return;
            }

            if (this.tracker >= this.energy || (this.p.drawPile.isEmpty() && this.p.discardPile.isEmpty())) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
                AbstractDungeon.player.getPower(NoDrawPower.POWER_ID).flash();
                this.isDone = true;
                return;
            }

            if (!this.p.drawPile.isEmpty()) {
                AbstractCard c = AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - 1);
                if (c.cost > 0)
                    this.tracker += c.cost;
                addToTop(new BandInvitationAction(this.energy - this.tracker));
                addToTop((AbstractGameAction)new DrawCardAction(1));
            } else {
                if(!this.p.discardPile.isEmpty()){
                    addToTop(new BandInvitationAction(this.energy - this.tracker));
                    addToTop((AbstractGameAction)new EmptyDeckShuffleAction());
                }
            }
            this.isDone = true;
            return;
        }
        tickDuration();
    }
}