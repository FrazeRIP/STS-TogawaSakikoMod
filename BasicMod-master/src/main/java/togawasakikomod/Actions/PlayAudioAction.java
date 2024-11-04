package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import togawasakikomod.character.TogawaSakiko;

public class PlayAudioAction extends AbstractGameAction {

    private String className;

    public  PlayAudioAction(String className){
        this.className = className;
    }

    @Override
    public void update() {
        TogawaSakiko.TryPlayCardAudio(className);
        this.isDone = true;
    }
}
