package togawasakikomod.monsters.oblivion.bosses.mygo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.helpers.DungeonHelper;
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.monsters.oblivion.bosses.avemujica.MisumiUikaBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;
import togawasakikomod.powers.buffs.DazzlingPower;
import togawasakikomod.powers.monsters.EarnestCryPower;
import togawasakikomod.powers.monsters.MonsterDivinityPower;
import togawasakikomod.util.TextureLoader;

public class TakamatsuTomoriBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(TakamatsuTomoriBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String[] MOVES = monsterStrings.MOVES;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(TakamatsuTomoriBoss.class.getSimpleName());

    private static final int action0AtkAmount = 1 ;
    private static final int action0AtkMultiAmount = 12;
    private static final int action1BlockAmount = 24;
    private static final int action1BuffAmount = 1;
    private static final int action2BuffReduceAmount = 2;
    private boolean isUltCasted = false;

    private boolean isDialogue1Played = false;
    private boolean isDialogue2Played = false;

    public TakamatsuTomoriBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);
        this.damage.add(new DamageInfo((AbstractCreature)this, action0AtkAmount));
        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, (AbstractPower)new EarnestCryPower(this,1),1));
    }

    @Override
    protected void openDialogue() {
        if(DungeonHelper.checkSpecificTypeMonsterExist(NagasakiSoyoBoss.ID)){
            //"小祥... NL 来组乐队吧！",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        if(DungeonHelper.checkSpecificTypeMonsterExist(MisumiUikaBoss.ID)){
            //"嗯，我也一样。"
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        {
            //"小祥... NL 我想对你说...",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 0 :
                //"我们各自向前走，一定会有答案。"
                if(hasPower(MonsterDivinityPower.POWER_ID) && !isDialogue2Played){
                    isDialogue2Played = true;
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, MOVES[2]));
                }

                //1*12
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                for(int i =0;i<action0AtkMultiAmount;i++){
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                }
                break;

            case 1:
                //"现在的我， NL 谈不上幸福与否。 NL 但是...",
                if(!isDialogue1Played){
                    isDialogue1Played = true;
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, MOVES[1]));
                }
                //24格挡
                //获得1点力量
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
