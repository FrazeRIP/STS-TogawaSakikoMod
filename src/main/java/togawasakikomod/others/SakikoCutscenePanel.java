package togawasakikomod.others;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.ScreenShake;

public class SakikoCutscenePanel extends CutscenePanel {

    String sfx;
    public SakikoCutscenePanel(String imgUrl, String sfx) {
        super(imgUrl, sfx);
        this.sfx = sfx;
    }

    @Override
    public void render(SpriteBatch sb) {
        int oSrc = sb.getBlendSrcFunc();
        int oDst = sb.getBlendDstFunc();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        super.render(sb);
        sb.setBlendFunction(oSrc,oDst);
    }

    @Override
    public void activate() {
        if (this.sfx != null) {
            //CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
            CardCrawlGame.sound.play(this.sfx);
        }

        this.activated = true;
    }
}
