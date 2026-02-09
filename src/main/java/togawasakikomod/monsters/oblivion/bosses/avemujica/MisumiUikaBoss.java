package togawasakikomod.monsters.oblivion.bosses.avemujica;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.cards.SpecialDeck.Curses.*;
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.powers.monsters.UnclaimedPromisePower;
import togawasakikomod.powers.monsters.VoicedGazePower;
import togawasakikomod.util.TextureLoader;

public class MisumiUikaBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(MisumiUikaBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(MisumiUikaBoss.class.getSimpleName());

    private int moveCount = 0;

    public MisumiUikaBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);

        this.damage.add(new DamageInfo((AbstractCreature)this, 10));
        this.damage.add(new DamageInfo((AbstractCreature)this, 20));

        this.flipHorizontal = flipHorizontal;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,new VoicedGazePower(this)));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){

            case 0:
                //向抽牌堆加入死亡，遗忘，
                //恐惧，爱，悲伤各1张
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new VFXAction(new HeartMegaDebuffEffect()));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Doloris(), 1, true, false, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Timoris(), 1, true, false, false, Settings.WIDTH * 0.35F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Oblivionis(), 1, true, false, false, Settings.WIDTH * 0.5F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Mortis(), 1, true, false, false, Settings.WIDTH * 0.65F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(new Amoris(), 1, true, false, false, Settings.WIDTH * 0.8F, Settings.HEIGHT / 2.0F));
                break;

            case 1 :
                //10*4
                //AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_AND_BUFF_MSG));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                break;

            case 2:
                //给队友99格挡
                //AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_MSG));
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(m!= this&&m instanceof FinalBossMonster && !m.isDeadOrEscaped()){
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m,99));
                    }
                }
                break;

            case 3:
                //20伤害
                //AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_MSG));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1)));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        switch (moveCount%4){
            case 0:
                setMove((byte)0, Intent.STRONG_DEBUFF);
                break;
            case 1:
            case 3:
                setMove((byte)1,Intent.ATTACK, (this.damage.get(0)).base,4,true);
                break;
            case 2:
                int monsterCount = 0;
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(m instanceof FinalBossMonster && !m.isDeadOrEscaped()){
                        monsterCount++;
                    }
                }

                if(monsterCount>1){
                    setMove((byte)2, Intent.DEFEND);
                }else{
                    setMove((byte)3, Intent.ATTACK, (this.damage.get(1)).base);
                }
                break;
        }
        moveCount ++;
    }
}
