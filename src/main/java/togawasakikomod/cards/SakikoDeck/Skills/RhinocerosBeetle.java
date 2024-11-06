package togawasakikomod.cards.SakikoDeck.Skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import togawasakikomod.Actions.PlayAudioAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.DazzlingPower;
import togawasakikomod.util.CardStats;

public class RhinocerosBeetle extends BaseCard {
    public static final String ID = makeID(RhinocerosBeetle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 3;

    public RhinocerosBeetle() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's damage and how much it changes when upgraded.
        setMagic(BLOCK,UPG_BLOCK);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        int dazzlingAmount =0;
        if(AbstractDungeon.player.hasPower(DazzlingPower.POWER_ID)){
            AbstractPower power = AbstractDungeon.player.getPower(DazzlingPower.POWER_ID);
            dazzlingAmount = power.amount/2;
        }
        this.magicNumber = baseBlock + dazzlingAmount;
        this.isMagicNumberModified = dazzlingAmount>0;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);

        int dazzlingAmount =0;
        if(AbstractDungeon.player.hasPower(DazzlingPower.POWER_ID)){
            AbstractPower power = AbstractDungeon.player.getPower(DazzlingPower.POWER_ID);
            dazzlingAmount = power.amount;
        }
        this.baseMagicNumber = baseBlock + dazzlingAmount;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayAudioAction(RhinocerosBeetle.class.getSimpleName()));
        applyPowers();
        addToBot(new GainBlockAction(p,magicNumber));
    }
}