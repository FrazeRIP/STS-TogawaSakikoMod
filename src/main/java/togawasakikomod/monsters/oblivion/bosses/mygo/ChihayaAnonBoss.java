package togawasakikomod.monsters.oblivion.bosses.mygo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.helpers.DungeonHelper;
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.monsters.oblivion.bosses.avemujica.MisumiUikaBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.WakabaMutsumiBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;
import togawasakikomod.powers.buffs.StrengthUpPower;
import togawasakikomod.powers.debuffs.TimorisPower;
import togawasakikomod.powers.monsters.ForwardResolvePower;
import togawasakikomod.util.TextureLoader;

public class ChihayaAnonBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(ChihayaAnonBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String[] MOVES = monsterStrings.MOVES;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(ChihayaAnonBoss.class.getSimpleName());

    private static final int triAtkAmount = 6 ;
    private static final int quadAtkAmount = 8;
    private static final int strAmt = 2;

    private int moveCount = -1;

    public boolean isCChordPlayed = false;
    public boolean isLastDialoguePlayed = false;
    public boolean isUikaDialoguePlayed = false;

    public ChihayaAnonBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);
        this.damage.add(new DamageInfo((AbstractCreature)this, triAtkAmount));
        this.damage.add(new DamageInfo((AbstractCreature)this, quadAtkAmount));
        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, (AbstractPower)new ForwardResolvePower(this,2),2));
    }

    @Override
    protected void openDialogue() {
        if(DungeonHelper.checkSpecificTypeMonsterExist(MisumiUikaBoss.ID)){
            //"是不是打扰到你们的私人时间了？",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        if(DungeonHelper.checkSpecificTypeMonsterExist(YuutenjiNyamuBoss.ID)){
            //"是喵梦亲！ NL 我是你频道的超忠实粉丝！"
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[4],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        {
            //"诶...? NL 我打祥子？！",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 2 :
                if(oppositeMonster!=null && !oppositeMonster.isDeadOrEscaped()&& oppositeMonster instanceof WakabaMutsumiBoss){
                    WakabaMutsumiBoss mutsumi = (WakabaMutsumiBoss) oppositeMonster;
                    if(mutsumi.isTeachPlayed && !isLastDialoguePlayed){
                        //"这，这样啊...",
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1]));
                        isLastDialoguePlayed = true;
                    }
                }
                else{

                    if(!DungeonHelper.checkSpecificTypeMonsterExist(MisumiUikaBoss.ID)){
                        //"哼哼，如何，很地道吧？"
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[2]));
                    }
                }

                //6*3
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                //给予所有敌人2点力量
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (m == this) {
                        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)this, (AbstractPower)new StrengthUpPower((AbstractCreature)m, this.strAmt), this.strAmt));
                        continue;
                    }

                    if (!m.isDying) {
                        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)this, (AbstractPower)new StrengthUpPower((AbstractCreature)m, this.strAmt), this.strAmt));
                    }
                }
                break;

            case 1:
                //8*4
                if(oppositeMonster!=null && !oppositeMonster.isDeadOrEscaped()&& oppositeMonster instanceof WakabaMutsumiBoss){
                    WakabaMutsumiBoss mutsumi = (WakabaMutsumiBoss) oppositeMonster;
                    if(!mutsumi.isTeachPlayed && !isCChordPlayed){
                        //"哼哼，如何，我的C和弦？",
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0]));
                        isCChordPlayed = true;
                    }
                }
                else{
                    if(!DungeonHelper.checkSpecificTypeMonsterExist(MisumiUikaBoss.ID)){
                        //"~But~ ~the~ ~eternal~ ~summer~ ~shall~ ~not~ ~fade~ ~",
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[1]));
                    }
                }

                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1)));
                break;

            case 0:
                //"不好意思！",
                if(DungeonHelper.checkSpecificTypeMonsterExist(MisumiUikaBoss.ID) && !isUikaDialoguePlayed){
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[3]));
                    isUikaDialoguePlayed = true;
                }

                //给予玩家2层易伤，
                //2层虚弱和2层脆弱
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)this, (AbstractPower)new TimorisPower(AbstractDungeon.player, 2,true), 2));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)this, (AbstractPower)new WeakPower((AbstractCreature)AbstractDungeon.player, 2,true), 2));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)this, (AbstractPower)new FrailPower((AbstractCreature)AbstractDungeon.player, 2,true), 2));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(moveCount <0){
            moveCount = i%3;
        }

        switch (moveCount%3){
            case 0:
                setMove((byte)0, Intent.DEBUFF);
                break;
            case 1:
                setMove((byte)1, Intent.ATTACK, (this.damage.get(1)).base,4,true);
                break;
            case 2:
                setMove((byte)2, Intent.ATTACK_BUFF, (this.damage.get(0)).base,3,true);
                break;
        }
        moveCount ++;
    }
}
