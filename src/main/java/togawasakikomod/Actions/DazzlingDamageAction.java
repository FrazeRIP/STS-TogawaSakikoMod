package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import togawasakikomod.effects.DazzlingAttackEffect;

public class DazzlingDamageAction extends DamageRandomEnemyAction {
    private final DamageInfo info;
    public DazzlingDamageAction(DamageInfo info, AttackEffect effect) {
        super(info, effect);
        this.info = info;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, AttackEffect.NONE));
            this.addToTop(new VFXAction(new DazzlingAttackEffect(target,false)));
        }

        this.isDone = true;
    }

}
