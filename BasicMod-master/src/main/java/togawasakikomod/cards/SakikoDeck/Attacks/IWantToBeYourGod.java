package togawasakikomod.cards.SakikoDeck.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.stances.DivinityStance;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.DazzlingPower;
import togawasakikomod.util.CardStats;

import java.util.Objects;

//月光奏鸣曲
public class IWantToBeYourGod extends BaseCard {
    public static final String ID = makeID(IWantToBeYourGod.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TogawaSakiko.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;

    public IWantToBeYourGod() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));

        if(!Objects.equals(p.stance.ID, DivinityStance.STANCE_ID) && p.hasPower(DazzlingPower.POWER_ID)){
            AbstractPower power = p.getPower(DazzlingPower.POWER_ID);
            //Get maximum amount available
            int consumeAmount = Math.min(power.amount, 10);
            //If already have Mantra
            if(p.hasPower(MantraPower.POWER_ID)){
                AbstractPower mantra = p.getPower(MantraPower.POWER_ID);
                //Mantra already 10 or above
                if(mantra.amount>=10){
                    addToTop(new ChangeStanceAction(new DivinityStance()));
                    addToTop(new ReducePowerAction(p,p,mantra.ID,10));
                //If current Mantra + consumeAmount = 10 or above
                }else if(mantra.amount + consumeAmount >=10){
                    consumeAmount = 10-mantra.amount;
                    addToTop(new RemoveSpecificPowerAction(p,p,mantra));
                    addToTop(new ReducePowerAction(p,p, DazzlingPower.POWER_ID,consumeAmount));
                    addToTop(new ChangeStanceAction(new DivinityStance()));
                }else{
                    addToTop(new ApplyPowerAction(p,p,new MantraPower(p,consumeAmount)));
                    addToTop(new ReducePowerAction(p,p, DazzlingPower.POWER_ID,consumeAmount));
                }
            }else{
                if(consumeAmount >=10){
                    addToTop(new ReducePowerAction(p,p, DazzlingPower.POWER_ID,consumeAmount));
                    addToTop(new ChangeStanceAction(new DivinityStance()));
                }else{
                    addToTop(new ApplyPowerAction(p,p,new MantraPower(p,consumeAmount)));
                    addToTop(new ReducePowerAction(p,p, DazzlingPower.POWER_ID,consumeAmount));
                }
            }
        }

//        addToBot(new AbstractGameAction() {
//            @Override
//            public void update() {
//                calculateCardDamage(m);
//                addToTop(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
//                isDone = true;
//            }
//        });
    }
}