package togawasakikomod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.rewards.PurgeReward;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class MasqueradeMask extends BaseRelic{
    private static final String NAME = MasqueradeMask.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int triggerAmount =3;

    public MasqueradeMask() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void onEquip() {
        super.onEquip();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void OnRemoveCard(){
        this.counter++;
        CheckIfTriggered();
    }

    private void CheckIfTriggered(){
        if(counter>= triggerAmount){
            counter-= triggerAmount;
            this.flash();
            AbstractDungeon.getCurrRoom().rewards.add(new PurgeReward());
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            CheckIfTriggered();
        }
    }
}
