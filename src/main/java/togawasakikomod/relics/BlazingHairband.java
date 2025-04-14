package togawasakikomod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import togawasakikomod.cards.SpecialDeck.Skills.Desire;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.effects.ShowCardAndObtainEffect2;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class BlazingHairband extends BaseRelic{
    private static final String NAME = BlazingHairband.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.


    public BlazingHairband() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void obtain() {
        //super.obtain();
        if(AbstractDungeon.player.hasRelic(MonochromeHairband.ID)){
//            AbstractRelic relic = AbstractDungeon.player.getRelic(MonochromeHairband.ID);
//            AbstractDungeon.player.relics.remove(relic);
            instantObtain(AbstractDungeon.player,0,true);
        }else{
            super.obtain();
        }
    }

    @Override
    public void onVictory() {
        this.flash();
        AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat();
        AbstractDungeon.player.masterDeck.group.add(card);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect2(card,
                (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(MonochromeHairband.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+DESCRIPTIONS[1];
    }
}
