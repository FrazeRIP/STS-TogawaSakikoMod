package togawasakikomod.powers.monsters;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import org.apache.logging.log4j.util.TriConsumer;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.effects.EnemyDivinityParticleEffect;
import togawasakikomod.effects.EnemyStanceAuraEffect;
import togawasakikomod.powers.BasePower;

import static togawasakikomod.TogawaSakikoMod.makeID;


//From downfall https://github.com/mikemayhemdev/DownfallSTS/tree/master
@CharismaticFormCopyEnable(enable = false)
public class MonsterDivinityPower extends BasePower {
    public static final String POWER_ID = makeID(MonsterDivinityPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static long sfxId = -1L;
    protected float angle; protected float particleTimer = 0.0F; protected float particleTimer2 = 0.0F;

    public MonsterDivinityPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        this.updateDescription();
        this.loadRegion("mantra");
        this.type = PowerType.BUFF;
    }


    @Override
    public void onRemove() {
        super.onRemove();
        CardCrawlGame.sound.stop("STANCE_LOOP_DIVINITY", sfxId);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_DIVINITY");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type) {
        if (type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL) {
            return damage * 3.0F;
        }
        return damage;
    }

    @Override
    public void update(int slot) {
        super.update(slot);

        if(!(this.owner instanceof AbstractMonster)){return;}

        if (!com.megacrit.cardcrawl.core.Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.2F;
                AbstractDungeon.effectsQueue.add(new EnemyDivinityParticleEffect(owner));
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new EnemyStanceAuraEffect("Divinity",owner));
        }
    }
}
