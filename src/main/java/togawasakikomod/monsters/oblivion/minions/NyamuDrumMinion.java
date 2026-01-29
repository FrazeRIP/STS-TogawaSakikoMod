package togawasakikomod.monsters.oblivion.minions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.monsters.oblivion.bosses.avemujica.WakabaMutsumiBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.ChihayaAnonBoss;
import togawasakikomod.util.TextureLoader;

public class NyamuDrumMinion extends AbstractMonster {
    public static final String ID =  TogawaSakikoMod.makeID(NyamuDrumMinion.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(NyamuDrumMinion.class.getSimpleName());


    boolean isFirstMove = true;

    public NyamuDrumMinion(float x, float y,boolean flipHorizontal)  {
        super(NAME, ID, 25, 0.0F, 200.0F, 140.0F, 130.0F, IMAGE_URL, x, y-200);
        this.damage.add(new DamageInfo(this,6));
        this.damage.add(new DamageInfo(this, 19));
        this.flipHorizontal = flipHorizontal;
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateFastAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction(AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Dazed(), 2,true,true,false));
                break;
            case 1:
                addToBot(new VFXAction(new ExplosionSmallEffect(this.hb.cX, this.hb.cY), 0.1F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addToBot(new SuicideAction((AbstractMonster)this));
                break;
        }
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(isFirstMove){
            setMove((byte)0, Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
            isFirstMove=false;
        }else{
            setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base);
        }
    }
}
