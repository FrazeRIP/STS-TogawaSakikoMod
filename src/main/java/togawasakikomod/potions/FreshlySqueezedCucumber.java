package togawasakikomod.potions;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.FreshlySqueezedCucumberPower;
import togawasakikomod.util.TextureLoader;

import static togawasakikomod.TogawaSakikoMod.makeID;
import static togawasakikomod.TogawaSakikoMod.potionPath;

public class FreshlySqueezedCucumber extends BasePotion {
    public static final String ID = makeID( FreshlySqueezedCucumber.class.getSimpleName());
    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = null;
    private static final Color SPOTS_COLOR = null;

    private static final String containerTexPath = potionPath(FreshlySqueezedCucumber.class.getSimpleName()+"/container.png");
    private static final String outlineTexPath = potionPath(FreshlySqueezedCucumber.class.getSimpleName()+"/outline.png");

    public FreshlySqueezedCucumber() {
        super(ID, 20, PotionRarity.UNCOMMON, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        //playerClass = TogawaSakiko.Meta.TOGAWA_SAKIKO;
        isThrown = false;
        targetRequired = false;
        setLiquidImg(TextureLoader.getTexture(emptyTexPath));
        setContainerImg(TextureLoader.getTexture(containerTexPath));
        setOutlineImg(TextureLoader.getTexture(outlineTexPath));
    }

    @Override
    public String getDescription() {
        return String.format(DESCRIPTIONS[0], potency);
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        AbstractCreature target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new FreshlySqueezedCucumberPower(target, this.potency), this.potency));
        }
    }
}
