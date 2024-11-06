//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class UpgradeAllCardInDrawPileAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList();

    public UpgradeAllCardInDrawPileAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            var1 = this.p.drawPile.group.iterator();
            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                }
            }
            this.isDone = true;
            return;

        }
    }
}
