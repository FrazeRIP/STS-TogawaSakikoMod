package togawasakikomod.cards.SakikoDeck.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.MakeCardInDiscardPileAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SpecialDeck.Curses.Doloris;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

//月光奏鸣曲
public class SymbolIIIWater extends BaseCard {
    public static final String ID = makeID(SymbolIIIWater.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 16;

    public SymbolIIIWater() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCostUpgrade(1);
        cardsToPreview = new Doloris();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageRandomEnemyAction(new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        AbstractCard card = new Doloris();
        addToBot(new AddCardToDeckAction(card));
        addToBot(new MakeCardInDiscardPileAction(card,1,true,true,false));

        this.addToBot(new SkipEnemiesTurnAction());
        this.addToBot(new PressEndTurnButtonAction());
    }
}