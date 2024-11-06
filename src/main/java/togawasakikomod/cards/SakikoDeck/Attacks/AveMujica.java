package togawasakikomod.cards.SakikoDeck.Attacks;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.AveMujicaAction;
import togawasakikomod.Actions.PlayAudioAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.CardStats;

import java.util.ArrayList;

//月光奏鸣曲
public class AveMujica extends BaseCard {
    public static final String ID = makeID(AveMujica.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    int index = 0;
    float cooldown = 1.0f;
    float timer = 0;
    ArrayList<AbstractCard> symbols = new ArrayList<AbstractCard>();

    public AveMujica() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.

        symbols.add(new SymbolIFire());
        symbols.add(new SymbolIIAir());
        symbols.add(new SymbolIIIWater());
        symbols.add(new SymbolIVEarth());
        symbols.add(new Ether());
    }

    @Override
    public void update() {
        super.update();
        if(this.hb.hovered){
            if(timer<=0){
                timer = cooldown;
                index++;
                if(index > symbols.size()-1){index = 0;}
                cardsToPreview = symbols.get(index);
            }else{
                timer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        for(AbstractCard card : symbols){
            card.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayAudioAction(AveMujica.class.getSimpleName()));

        addToBot(new AveMujicaAction(m,upgraded));

    }
}