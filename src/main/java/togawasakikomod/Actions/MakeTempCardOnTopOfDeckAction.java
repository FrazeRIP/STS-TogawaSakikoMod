package togawasakikomod.Actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class MakeTempCardOnTopOfDeckAction extends AbstractGameAction {

    AbstractCard cardToMake;
    private boolean randomSpot =false;
    private boolean autoPosition = false;
    private boolean toBottom = false;
    private float x;
    private float y;

    public MakeTempCardOnTopOfDeckAction(AbstractCard card, int amount){
        this.cardToMake = card;
        this.amount = amount;
        this.x = (float) Settings.WIDTH / 2.0F;
        this.y = (float)Settings.HEIGHT / 2.0F;
    }

    @Override
    public void update() {
        if (this.amount == 0 || AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == this.startDuration) {
                AbstractCard c;
                int i;
                if (this.amount < 6) {
                    for(i = 0; i < this.amount; ++i) {
                        c = this.cardToMake.makeStatEquivalentCopy();


                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, this.x, this.y, this.randomSpot, this.autoPosition, this.toBottom));

                    }
                } else {
                    for(i = 0; i < this.amount; ++i) {
                        c = this.cardToMake.makeStatEquivalentCopy();


                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, this.randomSpot, this.toBottom));
                    }
                }

                this.duration -= Gdx.graphics.getDeltaTime();
            }

            this.tickDuration();
        }
    }
}
