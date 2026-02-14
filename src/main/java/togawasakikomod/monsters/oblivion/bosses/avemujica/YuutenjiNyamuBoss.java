package togawasakikomod.monsters.oblivion.bosses.avemujica;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import togawasakikomod.Actions.TrueWaitAction;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.helpers.DungeonHelper;
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.monsters.oblivion.bosses.mygo.ChihayaAnonBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.ShiinaTakiBoss;
import togawasakikomod.monsters.oblivion.minions.NyamuDrumMinion;
import togawasakikomod.powers.monsters.UnfadingYearningPower;
import togawasakikomod.util.TextureLoader;

public class YuutenjiNyamuBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(YuutenjiNyamuBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String[] MOVES = monsterStrings.MOVES;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(YuutenjiNyamuBoss.class.getSimpleName());

    private NyamuDrumMinion[] minions = new NyamuDrumMinion[4];
    private int minionPerSpawn = 2;

    public boolean hasBackAttack = false;

    public static final float[] POSX = new float[] { 210.0F, -220.0F, 180.0F, -250.0F };
    public static final float[] POSY = new float[] { 75.0F, 115.0F, 345.0F, 335.0F };

    public YuutenjiNyamuBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);

        this.damage.add(new DamageInfo((AbstractCreature)this, 14));
        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,new UnfadingYearningPower(this)));
    }

    @Override
    protected void openDialogue() {
        if(DungeonHelper.checkSpecificTypeMonsterExist(ChihayaAnonBoss.ID)){
            //"啊啦，是可爱的小粉丝呢~",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
            AbstractDungeon.actionManager.addToBottom(new TrueWaitAction(FIRST_DIALOGUE_LENGTH/2));
            //"就用我们的合奏作今天的特别节目吧~",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        if(DungeonHelper.checkSpecificTypeMonsterExist(ShiinaTakiBoss.ID)){
            //"咱的努力总算有了回报，喵姆超感动！"
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
        else
        {
            //"被爱的人， NL 可没有权利选择自己被爱的方式。",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[0],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
            AbstractDungeon.actionManager.addToBottom(new TrueWaitAction(FIRST_DIALOGUE_LENGTH/2));
            //"能被爱就已经很幸运了喵姆。",
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[1],FIRST_DIALOGUE_LENGTH,FIRST_DIALOGUE_LENGTH));
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 0 :
                //14*2
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                break;

            case 1:
                //14
                //给予2层易伤
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,this, new VulnerablePower(AbstractDungeon.player,2,true),2));
                break;

            case 2:
                //14
                //20格挡
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this,20));
                break;

            case 3:
                //"这个BPM，小菜一碟！"
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MOVES[2]));

                //生成鼓
                int minionsSpawned = 0, i;
                for (i = 0; minionsSpawned < this.minionPerSpawn && i < this.minions.length; i++) {
                    if (this.minions[i] == null || this.minions[i].isDeadOrEscaped()) {
                        NyamuDrumMinion minionToSpawn = new NyamuDrumMinion(POSX[i], POSY[i],this.flipHorizontal);
                        this.minions[i] = minionToSpawn;
                        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SpawnMonsterAction(minionToSpawn, true));
                        minionsSpawned++;
                    }
                }
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        hasBackAttack = hasPower(BackAttackPower.POWER_ID);
        System.out.println("----------------------"+hasBackAttack);

        int minionsSpawned = 0;

        for(NyamuDrumMinion drum : minions){
            if(drum!=null && !drum.isDeadOrEscaped()){
                minionsSpawned ++;
            }
        }

        if(minionsSpawned<4 && !hasBackAttack){
            setMove((byte)3, Intent.UNKNOWN);
            return;
        }

        switch (i%3){
            case 0:
                setMove((byte)0,Intent.ATTACK, (this.damage.get(0)).base,2,true);
                break;
            case 1:
                setMove((byte)1, Intent.ATTACK_DEBUFF, (this.damage.get(0)).base);
                break;
            case 2:
                setMove((byte)2, Intent.ATTACK_DEFEND, (this.damage.get(0)).base);
                break;
        }
    }

    @Override
    public void die() {
        for(AbstractMonster m : minions){
            if(m!=null  && !m.isDeadOrEscaped()){
                addToTop(new SuicideAction(m));
                addToTop(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
            }
        }
        super.die();
    }
}
