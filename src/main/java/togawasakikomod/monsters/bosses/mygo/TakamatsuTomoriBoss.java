package togawasakikomod.monsters.bosses.mygo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.util.TriConsumer;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.monsters.SurroundedMonster;
import togawasakikomod.monsters.bosses.FinalBossMonster;
import togawasakikomod.powers.buffs.DazzlingPower;
import togawasakikomod.powers.monsters.EarnestCryPower;
import togawasakikomod.powers.monsters.MonsterDivinityPower;
import togawasakikomod.powers.monsters.RestlessIdealPower;
import togawasakikomod.util.TextureLoader;

public class TakamatsuTomoriBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(TakamatsuTomoriBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(TakamatsuTomoriBoss.class.getSimpleName());

    private static final int action0AtkAmount = 1 ;
    private static final int action0AtkMultiAmount = 12;
    private static final int action1BlockAmount = 30;
    private static final int action1BuffAmount = 2;
    private static final int action2BuffReduceAmount = 2;

    private static final String DAMAGE_MSG = DIALOG[0];
    private static final String DAMAGE_AND_BUFF_MSG = DIALOG[1];

    private boolean isUltCasted = false;

    public TakamatsuTomoriBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);
        this.damage.add(new DamageInfo((AbstractCreature)this, action0AtkAmount));
        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, (AbstractPower)new EarnestCryPower(this,1),1));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 0 :
                //1*12
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_AND_BUFF_MSG));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                for(int i =0;i<action0AtkMultiAmount;i++){
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                }
                break;

            case 1:
                //30格挡
                //获得2点力量
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_MSG));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this,action1BlockAmount));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new StrengthPower(this,action1BuffAmount)));
                break;

            case 2:
                //减少玩家2层力量，2层敏捷
                //以及2层闪耀
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player,-action2BuffReduceAmount), -action2BuffReduceAmount));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player,-action2BuffReduceAmount), -action2BuffReduceAmount));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ReducePowerAction(AbstractDungeon.player, this, DazzlingPower.POWER_ID, action2BuffReduceAmount));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void applyStartOfTurnPostDrawPowers() {
        super.applyStartOfTurnPostDrawPowers();
        if(hasPower(MonsterDivinityPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        }
    }

    @Override
    protected void getMove(int i) {
        System.out.println("!!!!!!!!!!!!!!Triggered");
        if(hasPower(MonsterDivinityPower.POWER_ID)){
            setMove((byte)0,Intent.ATTACK, (this.damage.get(0)).base,action0AtkMultiAmount,true);
            createIntent();
            return;
        }

        if(!isUltCasted){
            switch (i%3){
                case 0:
                    setMove((byte)0,Intent.ATTACK, (this.damage.get(0)).base,action0AtkMultiAmount,true);
                    break;
                case 1:
                    setMove((byte)1, Intent.DEFEND_BUFF);
                    break;
                case 2:
                    setMove((byte)2, Intent.STRONG_DEBUFF);
                    isUltCasted = true;
                    break;
            }
        }else{
            switch (i%2){
                case 0:
                    setMove((byte)0,Intent.ATTACK, (this.damage.get(0)).base,action0AtkMultiAmount,true);
                    break;
                case 1:
                    setMove((byte)1, Intent.DEFEND_BUFF);
                    break;
            }
        }
    }

}
