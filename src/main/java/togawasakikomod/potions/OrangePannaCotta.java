package togawasakikomod.potions;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import togawasakikomod.character.TogawaSakiko;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class OrangePannaCotta extends BasePotion {
    public static final String ID = makeID( OrangePannaCotta.class.getSimpleName());
    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color SPOTS_COLOR = null;

    public OrangePannaCotta() {
        super(ID, 0, PotionRarity.COMMON, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        playerClass = TogawaSakiko.Meta.TOGAWA_SAKIKO;
        isThrown = false;
        targetRequired = false;
    }

    @Override
    public String getDescription() {
        return String.format(DESCRIPTIONS[0],"");
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if(AbstractDungeon.player!=null){
            this.potency = AbstractDungeon.player.masterDeck.size();
        }
        if(AbstractDungeon.player!=null){
        AbstractDungeon.player.heal(potency);
     }
    }
}
