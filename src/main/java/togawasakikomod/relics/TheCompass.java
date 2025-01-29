package togawasakikomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.DazzlingPower;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class TheCompass extends BaseRelic{
    private static final String NAME = TheCompass.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public TheCompass() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p,p,new DazzlingPower(p,2)));
        for(AbstractCreature c : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(c,p,new DazzlingPower(c,1)));
        }
    }
}
