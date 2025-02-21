package togawasakikomod.cards.SakikoDeck.Powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.NumbersAndFacesAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

import java.util.List;

public class NumbersAndFaces extends BaseCard {
    public static final String ID = makeID(NumbersAndFaces.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 2;

    public NumbersAndFaces() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        this.misc = 0;
        setSelfRetain(true);
    }

    @Override
    public List<String> getCardDescriptors() {
        return super.getCardDescriptors();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NumbersAndFacesAction(this));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.magicNumber = this.misc+(upgraded?UPG_MAGIC_NUMBER+MAGIC_NUMBER:MAGIC_NUMBER);
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        super.applyPowers();
    }

    public void ResetMagicNumber(){
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
    }
}