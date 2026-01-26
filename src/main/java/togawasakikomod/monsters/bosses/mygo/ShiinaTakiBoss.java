package togawasakikomod.monsters.bosses.mygo;

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
import togawasakikomod.monsters.SurroundedMonster;
import togawasakikomod.monsters.bosses.FinalBossMonster;
import togawasakikomod.powers.monsters.RestlessIdealPower;
import togawasakikomod.util.TextureLoader;

public class ShiinaTakiBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(ShiinaTakiBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(ShiinaTakiBoss.class.getSimpleName());

    private static final int action0AtkAmount = 5 ;
    private static final int action1AtkAmount = 12;
    private static final int action2BuffAmount = 3;

    private static final String DAMAGE_MSG = DIALOG[0];
    private static final String DAMAGE_AND_BUFF_MSG = DIALOG[1];

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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, (AbstractPower)new RestlessIdealPower(this,1),1));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 0 :
                //5*3
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_AND_BUFF_MSG));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                break;

            case 1:
                //12
                //在手牌中加入一张凡庸
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_MSG));
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
