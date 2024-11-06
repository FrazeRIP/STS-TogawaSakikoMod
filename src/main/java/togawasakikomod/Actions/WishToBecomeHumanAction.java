//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package togawasakikomod.Actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.function.Consumer;

public class WishToBecomeHumanAction extends AbstractGameAction {
    private DamageInfo info;
    Consumer<Integer> callback;
    int attackCount = 0;

    public WishToBecomeHumanAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, Consumer<Integer> callback, int amount, int attackCount) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.callback = callback;
        this.attackCount = attackCount-1;
        this.amount= amount;

    }

    public void update() {
        if (this.shouldCancelAction() && this.info.type != DamageType.THORNS) {
            this.isDone = true;
        } else {
            if (this.duration == 0.1F) {
                if (this.info.type != DamageType.THORNS && (this.info.owner.isDying || this.info.owner.halfDead)) {
                    this.isDone = true;
                    return;
                }
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            }

            this.tickDuration();
            if (this.isDone) {
                if (this.attackEffect == AttackEffect.POISON) {
                    this.target.tint.color.set(Color.CHARTREUSE.cpy());
                    this.target.tint.changeColor(Color.WHITE.cpy());
                } else if (this.attackEffect == AttackEffect.FIRE) {
                    this.target.tint.color.set(Color.RED);
                    this.target.tint.changeColor(Color.WHITE.cpy());
                }


                this.target.damage(this.info);
                this.amount += this.target.lastDamageTaken;

                if(attackCount>0){addToTop(new WishToBecomeHumanAction(target,info,attackEffect,callback,amount,attackCount));}else{
                    this.callback.accept(amount);
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }
                }
            }
        }

    }
}
