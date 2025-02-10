package togawasakikomod.potions;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.powers.buffs.DazzlingPower;
import togawasakikomod.powers.debuffs.DazzlingDownPower;
import togawasakikomod.util.TextureLoader;

import static togawasakikomod.TogawaSakikoMod.makeID;
import static togawasakikomod.TogawaSakikoMod.potionPath;

public class HallucinationPotion extends BasePotion {
    public static final String ID = makeID( HallucinationPotion.class.getSimpleName());
    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = null;
    private static final Color SPOTS_COLOR = null;

    private static final String containerTexPath = potionPath(HallucinationPotion.class.getSimpleName()+"/container.png");
    private static final String outlineTexPath = potionPath(HallucinationPotion.class.getSimpleName()+"/outline.png");
    private static final String liquidTexPath = potionPath(HallucinationPotion.class.getSimpleName()+"/liquid.png");

    public HallucinationPotion() {
        super(ID, 5, PotionRarity.COMMON, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR, PotionEffect.RAINBOW);
        playerClass = TogawaSakiko.Meta.TOGAWA_SAKIKO;
        isThrown = false;
        targetRequired = false;

        setContainerImg(TextureLoader.getTexture(containerTexPath));
        setOutlineImg(TextureLoader.getTexture(outlineTexPath));
        setLiquidImg(TextureLoader.getTexture(liquidTexPath));
    }

    @Override
    public String getDescription() {
        return String.format(DESCRIPTIONS[0], potency,potency);
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        AbstractCreature target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new DazzlingPower(target, this.potency), this.potency));
            this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new DazzlingDownPower(target, this.potency,false), this.potency));
        }
    }
}
