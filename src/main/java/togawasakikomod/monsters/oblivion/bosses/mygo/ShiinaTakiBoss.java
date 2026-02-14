package togawasakikomod.monsters.oblivion.bosses.mygo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.helpers.DungeonHelper;
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.monsters.oblivion.bosses.avemujica.MisumiUikaBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YahataUmiriBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;
import togawasakikomod.powers.monsters.RestlessIdealPower;
import togawasakikomod.util.TextureLoader;

public class ShiinaTakiBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(ShiinaTakiBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String[] MOVES = monsterStrings.MOVES;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(ShiinaTakiBoss.class.getSimpleName());

    private static final int action0AtkAmount = 4 ;
    private static final int action1AtkAmount = 9;
    private static final int action2BuffAmount = 3;
    private int moveCount = -1;

    public ShiinaTakiBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);
        this.damage.add(new DamageInfo((AbstractCreature)this, action0AtkAmount));
        this.damage.add(new DamageInfo((AbstractCreature)this, action1AtkAmount));
        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, (AbstractPower)new RestlessIdealPower(this,1),1));
    }

    @Override
    protected void openDialogue() {
        if(DungeonHelper.checkSpecificTypeMonsterExist(NagasakiSoyoBoss.ID)){
            //"正有此意。",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        if(DungeonHelper.checkSpecificTypeMonsterExist(YahataUmiriBoss.ID)){
            //"哈？"
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[3],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        if(DungeonHelper.checkSpecificTypeMonsterExist(YuutenjiNyamuBoss.ID)){
            //"对面的鼓手，感觉听爱音提起过..."
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[4],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        {
            //"你的事，我听说了。",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 0 :
                if(DungeonHelper.checkSpecificTypeMonsterExist(TakamatsuTomoriBoss.ID)){
                    if(this.flipHorizontal!= AbstractDungeon.player.flipHorizontal){
                        //"不许无视灯！",
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1]));
                    }else{
                        //"不许伤害灯！",
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2]));
                    }
                }else{
                    if(AbstractDungeon.player.currentBlock <=0){
                        //"别想逃跑。"
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[2]));
                    }
                }

                //4*3

                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                break;

            case 1:
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[1]));
                //9
                //在手牌中加入一张凡庸
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1)));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Normality()));
                break;

            case 2:
                //获得仪式3
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(this, this, new RitualPower(this, action2BuffAmount,false), action2BuffAmount));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(moveCount <0){
            moveCount = i%2;
        }

        switch (moveCount%3){
            case 0:
                setMove((byte)0,Intent.ATTACK, (this.damage.get(0)).base,3,true);
                break;

            case 1:
                setMove((byte)1, Intent.ATTACK_DEBUFF, (this.damage.get(1)).base);
                break;

            case 2:
                setMove((byte)2, Intent.BUFF);
                break;
        }
        moveCount ++;
    }
}
