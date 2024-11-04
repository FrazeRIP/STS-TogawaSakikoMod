package togawasakikomod.cards.SakikoDeck.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import togawasakikomod.Actions.PlayAudioAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SakikoDeck.Attacks.WishToBecomeHuman;
import togawasakikomod.cards.SpecialDeck.Attacks.Melody;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.WorldviewPower;
import togawasakikomod.util.CardStats;

public class Worldview extends BaseCard {
    public static final String ID = makeID(Worldview.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Worldview() {
        super(ID, info);
        cardsToPreview = new Melody();
        setInnate(false,true);
    }

    @Override
    public void upgrade(){
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayAudioAction(Worldview.class.getSimpleName()));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WorldviewPower(AbstractDungeon.player, -1), 0));
    }
}