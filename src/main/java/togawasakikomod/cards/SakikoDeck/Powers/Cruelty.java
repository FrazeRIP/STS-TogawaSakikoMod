package togawasakikomod.cards.SakikoDeck.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import togawasakikomod.Actions.PlayAudioAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.CrueltyPower;
import togawasakikomod.util.CardStats;

public class Cruelty extends BaseCard {
    public static final String ID = makeID(Cruelty.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = -1;

    public Cruelty() {
        super(ID, info);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        cardsToPreview = new Desire();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayAudioAction(Cruelty.class.getSimpleName()));

        this.addToBot(new ApplyPowerAction(p, p, new VulnerablePower(AbstractDungeon.player, magicNumber,false), magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new CrueltyPower(AbstractDungeon.player, 1), 1));


    }
}