package togawasakikomod.monsters.oblivion.bosses.avemujica;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.helpers.DungeonHelper;
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.monsters.oblivion.bosses.mygo.NagasakiSoyoBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.ShiinaTakiBoss;
import togawasakikomod.powers.monsters.UnclaimedPromisePower;
import togawasakikomod.util.TextureLoader;

public class YahataUmiriBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(YahataUmiriBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String[] MOVES = monsterStrings.MOVES;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(YahataUmiriBoss.class.getSimpleName());

    private int moveCount = -1;
    private int specialCount = 0;
    private UnclaimedPromisePower power;

    private boolean isTakinResponseDone = false;

    public YahataUmiriBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);

        this.damage.add(new DamageInfo((AbstractCreature)this, 8));
        this.damage.add(new DamageInfo((AbstractCreature)this, 36));

        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        super.usePreBattleAction();
        power =  new UnclaimedPromisePower(this);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,power));
    }



    @Override
    protected void openDialogue() {
        if(DungeonHelper.checkSpecificTypeMonsterExist(NagasakiSoyoBoss.ID)){
            //"也可以算是Mygo的，请多指教。"
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        if(DungeonHelper.checkSpecificTypeMonsterExist(ShiinaTakiBoss.ID)){
            //"立希同学也想加入我们的乐队吗？",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        {
            //"我还以为Ave Mujica已经彻底停止活动了。",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 0 :
                if(DungeonHelper.checkSpecificTypeMonsterExist(ShiinaTakiBoss.ID) && !isTakinResponseDone){
                    isTakinResponseDone = true;
                    //"只是开个玩笑。"
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2]));
                }

                //8*3
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                break;

            case 1:
                if(DungeonHelper.checkSpecificTypeMonsterExist(ShiinaTakiBoss.ID) && !isTakinResponseDone){
                    isTakinResponseDone = true;
                    //"只是开个玩笑。"
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2]));
                }

                //36
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1)));
                break;

            case 2:

                if(DungeonHelper.checkSpecificTypeMonsterExist(ShiinaTakiBoss.ID) && !isTakinResponseDone){
                    isTakinResponseDone = true;
                    //"只是开个玩笑。"
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2]));
                }else{
                    //"证明你的决心，丰川同学。"
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, MOVES[1]));
                }

                //向抽牌堆加入晕眩，粘液，伤口，灼伤，虚空各1张。
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new VFXAction(new HeartMegaDebuffEffect()));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Dazed(), 1, true, false, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Slimed(), 1, true, false, false, Settings.WIDTH * 0.35F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Wound(), 1, true, false, false, Settings.WIDTH * 0.5F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Burn(), 1, true, false, false, Settings.WIDTH * 0.65F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, false, false, Settings.WIDTH * 0.8F, Settings.HEIGHT / 2.0F));
                break;
        }


        if(!this.isDead && power!=null){
            power.ApplyEffect();
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(specialCount>=2){
            moveCount = -1;
        }

        if(moveCount <0){
            moveCount = i;
            specialCount = 0;
            setMove((byte)2, Intent.STRONG_DEBUFF);
            return;
        }

        switch (moveCount%2){
            case 0:
                setMove((byte)0,Intent.ATTACK, (this.damage.get(0)).base,3,true);
                break;
            case 1:
                setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base);
                break;
        }
        moveCount ++;
        specialCount++;
    }
}
