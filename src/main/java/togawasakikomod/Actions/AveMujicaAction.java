//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import togawasakikomod.cards.SakikoDeck.Attacks.*;

import java.util.ArrayList;

public class AveMujicaAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean returnColorless = false;
    private AbstractCard.CardType cardType = null;

    ArrayList<AbstractCard> symbols = new ArrayList<AbstractCard>();

    public AveMujicaAction(AbstractMonster target, boolean isUpgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
        this.target= target;

        symbols.add(new SymbolIFire());
        symbols.add(new SymbolIIAir());
        symbols.add(new SymbolIIIWater());
        symbols.add(new SymbolIVEarth());
        symbols.add(new Ether());
        if(isUpgraded){
            for(AbstractCard card : symbols){
                card.upgrade();
            }
        }
    }

    public void update() {

        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.group = symbols;
            ArrayList<AbstractCard> pool = new ArrayList<>();

            for(int i = 0; i<3;i++){
                AbstractCard card = group.getRandomCard(AbstractDungeon.cardRandomRng);
                pool.add(card);
                group.removeCard(card);
            }
            AbstractDungeon.cardRewardScreen.customCombatOpen(pool, CardRewardScreen.TEXT[1], this.cardType != null);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

                    disCard.current_x = -1000.0F * Settings.xScale;

                    addToTop(new ForcePlayCardAction(target,disCard.makeSameInstanceOf()));
                    addToTop(new WaitAction(0.5f));
//                    addToBot(new MakeCardInDiscardPileAction(disCard,1,true,true,false));
//                    addToTop(new WaitAction(0.5f));
                    addToTop(new AddCardToDeckAction(disCard));

//                    if (this.amount == 1) {
//                        if (AbstractDungeon.player.hand.size() < 10) {
//                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
//                        } else {
//                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
//                        }
//                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }


}
