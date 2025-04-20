package togawasakikomod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import togawasakikomod.TogawaSakikoMod;

import static togawasakikomod.TogawaSakikoMod.makeID;

public class DazzlingAttackEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private final Texture img; // Changed from TextureAtlas.AtlasRegion to Texture

    private AbstractCreature target;

    private boolean targetTinted = false;
    private boolean mute = false;

    private boolean isPlayed = false;

    public DazzlingAttackEffect(AbstractCreature c, boolean mute) {
        this.img = new Texture(TogawaSakikoMod.vfxPath(DazzlingAttackEffect.class.getSimpleName()+".png")); // Replace with the actual path
        this.x = c.hb.cX - this.img.getWidth() / 2.0F;
        this.y = c.hb.cY - this.img.getHeight() / 2.0F;
        this.duration = 0.6F;
        this.startingDuration = 0.6F;
        this.target = c;
        this.color = Color.WHITE.cpy();
        this.scale = Settings.scale;
        this.targetTinted = false;
        this.mute = mute;
    }

    public void update() {
        super.update();
        if(!isPlayed){
            if (!mute) {
                CardCrawlGame.sound.play(makeID(DazzlingAttackEffect.class.getSimpleName()));
                isPlayed = true;
            }
        }
        if(this.duration<=this.startingDuration*.95f && !targetTinted){
            targetTinted = true;
            target.tint.color.set(Color.YELLOW.cpy());
            target.tint.changeColor(Color.WHITE.cpy());
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float) this.img.getWidth() /2, (float) this.img.getHeight() /2,this.img.getWidth(),this.img.getHeight(),scale,scale,0,0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
    }

    public void dispose() {
        this.img.dispose(); // Dispose of the texture to avoid memory leaks
    }
}