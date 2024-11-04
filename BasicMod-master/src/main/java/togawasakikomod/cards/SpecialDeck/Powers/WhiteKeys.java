package togawasakikomod.cards.SpecialDeck.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SakikoDeck.Skills.BlackAndWhiteKeys;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.DazzlingPower;
import togawasakikomod.util.CardStats;

public class WhiteKeys extends BaseCard {
    public static final String ID = makeID(WhiteKeys.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public WhiteKeys() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public  void onChoseThisOption(){
        AbstractPlayer p = AbstractDungeon.player;
        AbstractMonster m = BlackAndWhiteKeys.EnemyTarget;

        addToBot(new ApplyPowerAction(p,p,new DazzlingPower(p,this.magicNumber),this.magicNumber));
        if(m!=null){
            addToBot(new ApplyPowerAction(m,p,new StrengthPower(m,magicNumber)));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }
}