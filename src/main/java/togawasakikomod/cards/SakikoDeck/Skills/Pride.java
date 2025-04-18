package togawasakikomod.cards.SakikoDeck.Skills;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.PirdeAction;
import togawasakikomod.Actions.PlayAudioAction;
import togawasakikomod.Actions.RandomCardToHandByTypeAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SakikoDeck.Attacks.AveMujica;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

public class Pride extends BaseCard {
    public static final String ID = makeID(Pride.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Pride() {
        super(ID, info);
        setInnate(true);
        setCostUpgrade(0);
        setExhaust(true);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayAudioAction(Pride.class.getSimpleName()));

        for(int i = 0; i<magicNumber;i++){
            addToTop(new RandomCardToHandByTypeAction(AbstractCard.CardType.ATTACK));
        }
        addToBot(new PirdeAction(this.makeStatEquivalentCopy()));
    }
}