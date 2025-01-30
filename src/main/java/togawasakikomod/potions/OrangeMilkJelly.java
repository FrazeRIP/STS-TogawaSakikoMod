package togawasakikomod.potions;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.DoubleTapPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import togawasakikomod.character.TogawaSakiko;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class OrangeMilkJelly extends BasePotion {
    public static final String ID = makeID( OrangeMilkJelly.class.getSimpleName());
    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color SPOTS_COLOR = null;

    public OrangeMilkJelly() {
        super(ID, 1, PotionRarity.UNCOMMON, PotionSize.MOON, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        //playerClass = TogawaSakiko.Meta.TOGAWA_SAKIKO;
        isThrown = false;
        targetRequired = false;
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
            this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new DoubleTapPower(target, this.potency), this.potency));
        }
    }
}
