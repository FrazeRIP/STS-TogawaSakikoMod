package togawasakikomod.cards.SakikoDeck.Attacks;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import togawasakikomod.Actions.TheMoonlightSonataAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.relics.TheThirdMovement;
import togawasakikomod.util.CardStats;

//月光奏鸣曲
public class TheMoonlightSonata extends BaseCard {
    public static final String ID = makeID(TheMoonlightSonata.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;

    public TheMoonlightSonata() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(true);
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(AbstractDungeon.player.hasRelic(TheThirdMovement.ID)){
            AbstractRelic relic = AbstractDungeon.player.getRelic(TheThirdMovement.ID);
            if(!relic.usedUp && relic.counter>0){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }
    }

    @Override
    public void applyPowers() {
        if(AbstractDungeon.player.hasRelic(TheThirdMovement.ID)){
            AbstractRelic relic = AbstractDungeon.player.getRelic(TheThirdMovement.ID);
                if(!relic.usedUp && relic.counter>0){
                   this.baseDamage = !upgraded? DAMAGE*3:(DAMAGE+UPG_DAMAGE)*3;
                }else{
                    this.baseDamage = !upgraded? DAMAGE:(DAMAGE+UPG_DAMAGE);
                }
        }else{
            this.baseDamage = !upgraded? DAMAGE:(DAMAGE+UPG_DAMAGE);
        }
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        applyPowers();
        super.calculateCardDamage(m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TheMoonlightSonataAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }

}