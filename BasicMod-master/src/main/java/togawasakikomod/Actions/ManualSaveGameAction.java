package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.helpers.SaveHelper;

public class ManualSaveGameAction extends AbstractGameAction {

    @Override
    public void update() {
        SaveHelper.saveExists();
        this.isDone = true;
    }
}
