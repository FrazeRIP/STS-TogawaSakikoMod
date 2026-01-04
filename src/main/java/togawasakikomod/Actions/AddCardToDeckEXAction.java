package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class AddCardToDeckEXAction extends AbstractGameAction {
    AbstractCard cardToObtain;

    public AddCardToDeckEXAction(AbstractCard card) {
        this.cardToObtain = card;
        this.duration = 0.25F;
    }

    public void update() {
        if(!cardToObtain.upgraded){
            switch (cardToObtain.type){
                case ATTACK:
                    if(AbstractDungeon.player.hasRelic("Molten Egg 2")){
                        cardToObtain.upgrade();
                    }
                    break;

                case POWER:
                    if(AbstractDungeon.player.hasRelic("Frozen Egg 2")){
                        cardToObtain.upgrade();
                    }
                    break;

                case SKILL:
                    if(AbstractDungeon.player.hasRelic("Toxic Egg 2")){
                        cardToObtain.upgrade();
                    }
                    break;
            }
        }
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.cardToObtain, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        this.isDone = true;
    }
}