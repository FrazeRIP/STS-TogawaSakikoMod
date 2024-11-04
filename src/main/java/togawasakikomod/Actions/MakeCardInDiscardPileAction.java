//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import togawasakikomod.effects.ShowCardAndAddToDiscardPileEffect2;
import togawasakikomod.powers.buffs.EndurancePower;

public class MakeCardInDiscardPileAction extends AbstractGameAction {
    private AbstractCard cardToMake;
    private boolean randomSpot;
    private boolean autoPosition;
    private boolean toBottom;
    private float x;
    private float y;

    public MakeCardInDiscardPileAction(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom, float cardX, float cardY) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.cardToMake = card;
        this.randomSpot = randomSpot;
        this.autoPosition = autoPosition;
        this.toBottom = toBottom;
        this.x = cardX;
        this.y = cardY;
    }

    public MakeCardInDiscardPileAction(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom) {
        this(card, amount, randomSpot, autoPosition, toBottom, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
    }

    public MakeCardInDiscardPileAction(AbstractCard card, int amount, boolean shuffleInto, boolean autoPosition) {
        this(card, amount, shuffleInto, autoPosition, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractCard c;
            int i;
            if (this.amount < 6) {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeSameInstanceOf();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardPileEffect2(c));
                }
            } else {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeSameInstanceOf();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardPileEffect2(c));
                }
            }
            this.duration -= Gdx.graphics.getDeltaTime();
            EndurancePower.CheckForEndurance();
        }

        this.tickDuration();
    }
}
