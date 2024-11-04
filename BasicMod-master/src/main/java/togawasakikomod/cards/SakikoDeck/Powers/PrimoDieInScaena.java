package togawasakikomod.cards.SakikoDeck.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.PlayAudioAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SakikoDeck.Skills.Perfection;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.DazzlingPower;
import togawasakikomod.powers.buffs.HypePower;
import togawasakikomod.util.CardStats;

public class PrimoDieInScaena extends BaseCard {
    public static final String ID = makeID(PrimoDieInScaena.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 2;

    private static final int HYPE = 1;
    private static final int UPG_HYPE = 0;
    public PrimoDieInScaena() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        setCustomVar("Hype", HYPE, UPG_HYPE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayAudioAction(PrimoDieInScaena.class.getSimpleName()));

        //Self - Hype
        //addToBot(new ApplyPowerAction(p,p,new Hype(p,this.customVar("Hype")),this.customVar("Hype")));
        //Monsters - Hype
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            this.addToBot(new ApplyPowerAction(mo,p,new HypePower(mo,this.customVar("Hype")),this.customVar("Hype")));
        }
        //Self - Dazzling
        addToBot(new ApplyPowerAction(p,p,new DazzlingPower(p,this.magicNumber),this.magicNumber));
    }
}