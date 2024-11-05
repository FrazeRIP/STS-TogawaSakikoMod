package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.powers.buffs.PridePower;

public class PirdeAction extends AbstractGameAction {

    private  AbstractCard card;

    public PirdeAction(AbstractCard card){
        this.card = card;
    }
    
    @Override
    public void update() {
        PridePower.copies.add(card);
        addToTop(new RandomCardFromDrawPileToHandAction(AbstractCard.CardType.ATTACK));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PridePower(AbstractDungeon.player, 1), 1));
        isDone = true;
    }
}
