package togawasakikomod.relics;

import togawasakikomod.character.TogawaSakiko;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class WarmthInfusedPorcelainCup extends BaseRelic{
    private static final String NAME = WarmthInfusedPorcelainCup.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public WarmthInfusedPorcelainCup() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.flash();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

