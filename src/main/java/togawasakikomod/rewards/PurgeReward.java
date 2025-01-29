package togawasakikomod.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import togawasakikomod.patches.CustomEnumPatch;
import togawasakikomod.relics.CuteAnimalBandAid;

import java.util.Objects;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class PurgeReward extends CustomReward {
    private static final Texture ICON = new Texture(Gdx.files.internal("togawasakikomod/images/ui/purgeReward.png"));
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("PurgeReward"));
    private static final String[] TEXT = uiStrings.TEXT;
    boolean isDoneDone = false;
    public PurgeReward() {
        super(ICON, TEXT[0], CustomEnumPatch.TOGAWASAKIKO_PURGEREWARD);
        isDoneDone = false;
    }

    @Override
    public boolean claimReward() {
        if(isDoneDone){
            for(AbstractRelic relic : AbstractDungeon.player.relics){
                if(Objects.equals(relic.relicId, CuteAnimalBandAid.ID)){
                    AbstractDungeon.player.heal(1);
                }
            }
            return true;
        }else{

            if (!CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).isEmpty()) {
                AbstractDungeon.gridSelectScreen.open(
                        CardGroup.getGroupWithoutBottledCards(
                                AbstractDungeon.player.masterDeck.getPurgeableCards()),
                        1,
                        TEXT[1],
                        false,
                        false,
                        true,
                        true);

            }
            return false;
        }
        }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
            //AbstractEvent.logMetricCardRemovalAtCost("The Cleric", "Card Removal", c, this.purifyCost);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
            isDoneDone = true;
            isDone = true;
//            for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards ){
//                if(reward.)
//            }
//            AbstractDungeon.combatRewardScreen.rewards.remove(this);
        }
    }
}