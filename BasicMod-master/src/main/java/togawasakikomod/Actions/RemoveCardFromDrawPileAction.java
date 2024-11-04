package togawasakikomod.Actions;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.relicInterfaces.OnRemoveCardFromMasterDeckPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import togawasakikomod.cards.CustomTags;
import togawasakikomod.modifiers.UnremoveableModifier;
import togawasakikomod.patches.CustomEnumPatch;

import java.util.Iterator;

public class RemoveCardFromDrawPileAction extends AbstractGameAction {

    private float startingDuration;

    public RemoveCardFromDrawPileAction(int amount){

        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        }
        else
        {
            Iterator var1;
            AbstractCard c;

            if (this.duration == this.startingDuration) {
            var1 = AbstractDungeon.player.powers.iterator();

            while(var1.hasNext()) {
                AbstractPower p = (AbstractPower)var1.next();
                p.onScry();
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (this.amount != -1) {
                for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                    AbstractCard card = (AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1);
                    if(!CardModifierManager.hasModifier(card, UnremoveableModifier.ID)){
                        tmpGroup.addToTop(card);
                    }
                }
            } else {
                Iterator var5 = AbstractDungeon.player.drawPile.group.iterator();

                while(var5.hasNext()) {
                    AbstractCard card = (AbstractCard)var5.next();
                    if(!CardModifierManager.hasModifier(card, UnremoveableModifier.ID)){
                        tmpGroup.addToTop(card);
                    }
                    tmpGroup.addToBottom(card);
                }
            }

            for(AbstractCard card : tmpGroup.group){
                    addToTop(new RemoveCardFromDeckAction(card));
                }
        }
        }

        this.isDone = true;
    }
}
