package togawasakikomod.cards.SakikoDeck.Skills;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.Actions.CrychicAction;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.cards.SakikoDeck.Attacks.*;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.CrychicPower;
import togawasakikomod.util.CardStats;

import java.util.ArrayList;

public class Crychic extends BaseCard {
    public static final String ID = makeID(Crychic.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC_NUMBER = 0;
    private static final int UPG_MAGIC_NUMBER = 1;

    int index = 0;
    float cooldown = 1.0f;
    float timer = 0;
    ArrayList<AbstractCard> phantoms = new ArrayList<AbstractCard>();


    public Crychic() {
        super(ID, info);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        setExhaust(true);

        phantoms.add(new PhantomOfMutsumi());
        phantoms.add(new PhantomOfSakiko());
        phantoms.add(new PhantomOfSoyo());
        phantoms.add(new PhantomOfTaki());
        phantoms.add(new PhantomOfTomori());
    }

    @Override
    public void update() {
        super.update();
        if(this.hb.hovered){
            if(timer<=0){
                timer = cooldown;
                index++;
                if(index > phantoms.size()-1){index = 0;}
                cardsToPreview = phantoms.get(index);
            }else{
                timer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //this.addToBot(new ApplyPowerAction(p, p, new CrychicPower(AbstractDungeon.player, energyOnUse+magicNumber), energyOnUse+magicNumber));
        addToBot(new CrychicAction(energyOnUse));
        addToBot(new LoseEnergyAction(energyOnUse));
    }
}