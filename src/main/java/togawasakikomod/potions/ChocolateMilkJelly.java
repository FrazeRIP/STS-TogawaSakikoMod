package togawasakikomod.potions;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.DoubleTapPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.util.TextureLoader;

import static togawasakikomod.TogawaSakikoMod.makeID;
import static togawasakikomod.TogawaSakikoMod.potionPath;

public class ChocolateMilkJelly extends BasePotion {
    public static final String ID = makeID( ChocolateMilkJelly.class.getSimpleName());
    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = null;
    private static final Color SPOTS_COLOR = null;

    private static final String containerTexPath = potionPath(ChocolateMilkJelly.class.getSimpleName()+"/container.png");
    private static final String outlineTexPath = potionPath(ChocolateMilkJelly.class.getSimpleName()+"/outline.png");

    public ChocolateMilkJelly() {
        super(ID, 1, PotionRarity.UNCOMMON, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        //playerClass = TogawaSakiko.Meta.TOGAWA_SAKIKO;
        isThrown = false;
        targetRequired = false;
        setContainerImg(TextureLoader.getTexture(containerTexPath));
        setOutlineImg(TextureLoader.getTexture(outlineTexPath));
        setLiquidImg(TextureLoader.getTexture(emptyTexPath));
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
            this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new FreeAttackPower(target, this.potency), this.potency));
        }
    }
}
