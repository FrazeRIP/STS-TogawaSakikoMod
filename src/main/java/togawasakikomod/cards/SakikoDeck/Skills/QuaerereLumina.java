package togawasakikomod.cards.SakikoDeck.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.patches.ScryCallbackPatch;
import togawasakikomod.util.CardStats;

public class QuaerereLumina extends BaseCard {
    public static final String ID = makeID(QuaerereLumina.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC_NUMBER = 7;
    private static final int UPG_MAGIC_NUMBER = 2;

    public QuaerereLumina() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER); //Sets the card's damage and how much it changes when upgraded.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(ScryCallbackPatch.scryWithCallback(magicNumber, (cards)->{
            int amount = 0;
            for (int i = 0; i < cards.size(); ++i){
                amount ++;
            }
            addToTop(new GainBlockAction(p, p, amount));
        }));
    }
}