package togawasakikomod.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.patches.ReducedPowerRecorderPatch;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class FountainDrink extends BaseRelic{
    private static final String NAME = FountainDrink.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public FountainDrink() {
        super(ID, NAME, TogawaSakiko.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onVictory() {
        boolean hasEmpty = false;
        for(AbstractPotion potion : AbstractDungeon.player.potions){
            if(potion instanceof PotionSlot){
                hasEmpty = true;
                break;
            }
        }
        if(!hasEmpty){
            this.flash();
            //AbstractDungeon.getCurrRoom().addPotionToRewards();
        }
        super.onVictory();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @SpirePatch(clz = AbstractRoom.class, method = "addPotionToRewards", paramtypes = {})
    public  static class FountainDrinkPatch{
        @SpireInsertPatch( locator= Locator.class, localvars = {"chance"})
        public static void DoIt(AbstractRoom _inst, @ByRef int[] chance) {
            if (AbstractDungeon.player.hasRelic(FountainDrink.ID)) {
                boolean hasEmpty = false;
                for(AbstractPotion potion : AbstractDungeon.player.potions){
                    if(potion instanceof PotionSlot){
                        hasEmpty = true;
                        break;
                    }
                }

                if(!hasEmpty){
                    chance[0] = 100;
                }
            }
        }

        public static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher); }
        }

    }
}
