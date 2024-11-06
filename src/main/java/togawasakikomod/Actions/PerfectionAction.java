package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.Iterator;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class PerfectionAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(PerfectionAction.class.getSimpleName()));
    private static final String[] TEXT = uiStrings.TEXT;


    public PerfectionAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    @Override
    public void update() {
        int theSize = AbstractDungeon.player.hand.size();
        for(int i =0;i < theSize;i++){
            this.addToTop(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), 1));
        }
        this.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
        this.isDone = true;
    }
}
