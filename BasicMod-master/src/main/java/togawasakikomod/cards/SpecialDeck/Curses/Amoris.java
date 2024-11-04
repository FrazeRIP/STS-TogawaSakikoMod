package togawasakikomod.cards.SpecialDeck.Curses;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardAtBottomOfDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

public class Amoris extends BaseCard {
    public static final String ID = makeID(Amoris.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.CURSE, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.CURSE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Amoris() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setSelfRetain(true);
        glowColor = Color.RED;
        //cardsToPreview = new VoidCard();
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        this.flash();
    }
    //
//    @Override
//    public void triggerWhenDrawn() {
//        super.triggerWhenDrawn();
//        this.addToBot(new SetDontTriggerAction(this, false));
//    }
//
//    @Override
//    public void triggerOnEndOfTurnForPlayingCard() {
//        super.triggerOnEndOfTurnForPlayingCard();
//        this.dontTriggerOnUseCard = true;
//        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  if (this.dontTriggerOnUseCard) {
            this.flash();
            //addToBot(new MakeTempCardInDiscardAction(new VoidCard(),1));
        }
    }
}