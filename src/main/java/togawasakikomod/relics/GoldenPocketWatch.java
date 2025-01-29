package togawasakikomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.DazzlingPower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class GoldenPocketWatch extends BaseRelic{
    private static final String NAME = GoldenPocketWatch.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int triggerAmount =12;

    public GoldenPocketWatch() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }


    @Override
    public void atBattleStart() {
        super.atBattleStart();
        //this.counter = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        counter++;
        CheckIfTriggered();
    }

    private void CheckIfTriggered(){
        AbstractPlayer p = AbstractDungeon.player;
        if(counter>= triggerAmount){
            counter-= triggerAmount;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,2)));
            CheckIfTriggered();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
