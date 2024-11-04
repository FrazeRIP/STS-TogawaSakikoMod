package togawasakikomod.cards.SakikoDeck.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.MakeCardInDiscardPileAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SpecialDeck.Curses.Mortis;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

//月光奏鸣曲
public class SymbolIVEarth extends BaseCard {
    public static final String ID = makeID(SymbolIVEarth.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int BLOCK = 15;
    private static final int UPG_BLOCK = 5;

    public SymbolIVEarth() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK,UPG_BLOCK);
        cardsToPreview = new Mortis();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.currentBlock + block;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription +  cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));

        this.baseDamage = p.currentBlock + block;
        this.calculateCardDamage(m);
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();



        AbstractCard card = new Mortis();
        addToBot(new AddCardToDeckAction(card));
        addToBot(new MakeCardInDiscardPileAction(card,1,true,true,false));

    }
}