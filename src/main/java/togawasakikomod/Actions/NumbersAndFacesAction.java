package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.powers.buffs.HypePower;

public class NumbersAndFacesAction extends AbstractGameAction {

    private final AbstractCard card;

    public NumbersAndFacesAction(AbstractCard card){
        this.card = card;
    }

    @Override
    public void update() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HypePower(AbstractDungeon.player, card.magicNumber), card.magicNumber));
        card.resetAttributes();
        card.misc = 0;
        this.isDone = true;
    }
}
