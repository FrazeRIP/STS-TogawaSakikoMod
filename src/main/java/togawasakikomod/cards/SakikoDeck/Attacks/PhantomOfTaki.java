package togawasakikomod.cards.SakikoDeck.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.MakeCardInDiscardPileAction;
import togawasakikomod.Actions.PlayAudioAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SpecialDeck.Powers.Ideal;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

//月光奏鸣曲
public class PhantomOfTaki extends BaseCard {
    public static final String ID = makeID(PhantomOfTaki.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 12;

    public PhantomOfTaki() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        cardsToPreview = new Ideal();
        setExhaust(true);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayAudioAction(PhantomOfTaki.class.getSimpleName()));

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        AbstractCard card = new Ideal();
        if(upgraded){
            card.upgrade();
        }
        addToBot(new AddCardToDeckAction(card));
        addToBot(new MakeCardInDiscardPileAction(card,1,true,true,false));

    }
}