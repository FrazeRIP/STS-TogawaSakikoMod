package togawasakikomod.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.patches.ObtainRewardEventPatch;
import togawasakikomod.powers.buffs.DazzlingPower;

import java.util.Objects;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class CuteAnimalBandAid extends BaseRelic{
    private static final String NAME = CuteAnimalBandAid.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public CuteAnimalBandAid() {
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
