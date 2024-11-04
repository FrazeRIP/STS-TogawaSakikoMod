//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import java.util.Iterator;

public class ShowCardAndObtainEffect2 extends AbstractGameEffect {
    private static final float EFFECT_DUR = 2.0F;
    private static final float FAST_DUR = 0.5F;
    private AbstractCard card;
    private static final float PADDING;
    private boolean converge;

    public ShowCardAndObtainEffect2(AbstractCard card, float x, float y, boolean convergeCards) {
        if (card.color == CardColor.CURSE && AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0) {
            ((Omamori)AbstractDungeon.player.getRelic("Omamori")).use();
            this.duration = 0.0F;
            this.isDone = true;
            this.converge = convergeCards;
        }

        UnlockTracker.markCardAsSeen(card.cardID);
        //CardHelper.obtain(card.cardID, card.rarity, card.color);
        this.card = card;
        if (Settings.FAST_MODE) {
            this.duration = 0.5F;
        } else {
            this.duration = 2.0F;
        }

        this.identifySpawnLocation(x, y);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
    }

    public ShowCardAndObtainEffect2(AbstractCard card, float x, float y) {
        this(card, x, y, true);
    }

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        Iterator var4 = AbstractDungeon.effectList.iterator();

        while(var4.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)var4.next();
            if (e instanceof ShowCardAndObtainEffect2) {
                ++effectCount;
            }
        }

        this.card.current_x = x;
        this.card.current_y = y;
        if (this.converge) {
            this.card.target_y = (float)Settings.HEIGHT * 0.5F;
            switch (effectCount) {
                case 0:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F;
                    break;
                case 1:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
                    break;
                case 2:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
                    break;
                case 3:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                    break;
                case 4:
                    this.card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                    break;
                default:
                    this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
                    this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
            }
        } else {
            this.card.target_x = this.card.current_x;
            this.card.target_y = this.card.current_y;
        }

    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }

    }

    public void dispose() {
    }

    static {
        PADDING = 30.0F * Settings.scale;
    }
}
