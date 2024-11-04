package togawasakikomod.cards.SakikoDeck.Skills;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.AuthorityRestorationAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SpecialDeck.Powers.BlackKeys;
import togawasakikomod.cards.SpecialDeck.Powers.WhiteKeys;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

import java.util.ArrayList;
import java.util.Iterator;

public class BlackAndWhiteKeys extends BaseCard {
    public static final String ID = makeID(BlackAndWhiteKeys.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF_AND_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public  static AbstractMonster EnemyTarget = null;

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 1;
    public BlackAndWhiteKeys() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        EnemyTarget = m;
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new BlackKeys());
        stanceChoices.add(new WhiteKeys());
        if (this.upgraded) {
            Iterator var4 = stanceChoices.iterator();

            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                c.upgrade();
            }
        }
        this.addToBot(new ChooseOneAction(stanceChoices));
    }
}