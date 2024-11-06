package togawasakikomod.relics;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import togawasakikomod.character.TogawaSakiko;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class TheThirdMovement extends BaseRelic{
    private static final String NAME = TheThirdMovement.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public TheThirdMovement()
    {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void reduceCounter(int reduceAmount) {
        this.counter -= reduceAmount;
        if (this.counter  <= 0) {
            this.usedUp();
        }
    }

    public AbstractRelic makeCopy() {
        return new TheThirdMovement();
    }

    @Override
    public void usedUp(){
        this.grayscale = true;
        this.usedUp = true;
        this.description = DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
}
