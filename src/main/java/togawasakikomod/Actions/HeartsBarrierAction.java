package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class HeartsBarrierAction extends AbstractGameAction {


    private final AbstractCard card;

    public HeartsBarrierAction(AbstractCard card, AbstractCreature target){
        this.card = card;
        this.target = target;
    }

    @Override
    public void update() {
        card.baseBlock = AbstractDungeon.player.masterDeck.size();
        card.calculateCardDamage(AbstractDungeon.getRandomMonster());
        addToBot(new GainBlockAction(target, card.block));
        card.initializeDescription();
        isDone = true;
    }
}

