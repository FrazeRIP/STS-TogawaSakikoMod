package togawasakikomod.others;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;

public class SakikoCutscenePanel extends CutscenePanel {

    public SakikoCutscenePanel(String imgUrl, String sfx) {
        super(imgUrl, sfx);
    }

    @Override
    public void render(SpriteBatch sb) {
        int oSrc = sb.getBlendSrcFunc();
        int oDst = sb.getBlendDstFunc();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        super.render(sb);
        sb.setBlendFunction(oSrc,oDst);
    }
}
