package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.cards.SakikoDeck.Attacks.SymbolIFire;

import java.util.ArrayList;

public class SymbolIFireAction extends AbstractGameAction {

    private final AbstractMonster target;
    boolean playTwice;
    AbstractCard card = null;

    private boolean initialize = false;

    public SymbolIFireAction(AbstractMonster monster, boolean isDoublePlay) {
        this.target = monster;
        playTwice = isDoublePlay;
    }


    @Override
    public void update() {
        if(!initialize){
            ArrayList<AbstractCard> list = AbstractDungeon.actionManager.cardsPlayedThisCombat;
            for (int i = list.size() - 1; i >= 0; i--) {
                if ( list.get(i).type == AbstractCard.CardType.ATTACK && !list.get(i).cardID.equals(SymbolIFire.ID)) {
                    card = list.get(i);
                    break;
                }
            }
        }
        initialize = true;

        if(card!=null){
            if(target == null || target.isDead){
                AbstractDungeon.getCurrRoom().monsters.getRandomMonster(target,true);
            }

            AbstractCard tmp = card.makeStatEquivalentCopy();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if (target != null) {
                tmp.calculateCardDamage(target);
            }
            tmp.purgeOnUse = true;
            addToTop(new ForcePlayCardAction((AbstractCreature) target,tmp,true));
            if(playTwice){
                addToTop(new ForcePlayCardAction((AbstractCreature) target,tmp.makeStatEquivalentCopy(),true)); }
        }
        this.isDone = true;
    }
}
