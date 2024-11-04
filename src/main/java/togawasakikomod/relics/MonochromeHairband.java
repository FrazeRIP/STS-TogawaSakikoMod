package togawasakikomod.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import togawasakikomod.Actions.ManualSaveGameAction;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.effects.ShowCardAndObtainEffect2;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class MonochromeHairband extends BaseRelic{
    private static final String NAME = MonochromeHairband.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.


    public MonochromeHairband() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.player.masterDeck.group.add(new Desire());
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect2(new Desire(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+DESCRIPTIONS[1];
    }
}
