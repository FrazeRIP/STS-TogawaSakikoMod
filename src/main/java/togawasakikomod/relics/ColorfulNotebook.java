package togawasakikomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.effects.ShowCardAndObtainEffect2;
import togawasakikomod.powers.buffs.DazzlingPower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class ColorfulNotebook extends BaseRelic{
    private static final String NAME = ColorfulNotebook.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.


    public ColorfulNotebook() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DazzlingPower(AbstractDungeon.player,1)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
