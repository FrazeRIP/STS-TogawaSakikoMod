package togawasakikomod.monsters.bosses.avemujica;

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
import togawasakikomod.Actions.LoseBlockForEveryoneThenAttackAction;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.monsters.bosses.FinalBossMonster;
import togawasakikomod.powers.monsters.SilentWoundPower;
import togawasakikomod.util.TextureLoader;

import java.util.Objects;

public class WakabaMutsumiBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(WakabaMutsumiBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(WakabaMutsumiBoss.class.getSimpleName());

    private static final String DAMAGE_MSG = DIALOG[0];
    private static final String DAMAGE_AND_BUFF_MSG = DIALOG[1];

    private int moveCount = -1;
    private DamageInfo blockRemoveAttackInfo = new DamageInfo(this,0);


    public WakabaMutsumiBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);

        this.damage.add(new DamageInfo((AbstractCreature)this, 10));
        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    @Override
    public boolean hasPower(String targetID) {
        boolean result = super.hasPower(targetID);
        if(Objects.equals(targetID, "Barricade")){
            result = true;
        }
        return result;
    }

    @Override
    public void applyEndOfTurnTriggers() {
       if (!isDying && !isEscaping) {
           boolean hasBarricade = false;
           for(AbstractPower power : this.powers){
               if(Objects.equals(power.ID, "Barricade")){
                   hasBarricade = true;
                   break;
               }
           }
           if(!hasBarricade){
                this.loseBlock();
           }
         }
        super.applyEndOfTurnTriggers();
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SilentWoundPower(this),0));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this,3),3));
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove){
            case 0 :
                //获得7层多重护甲
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(this,this,new PlatedArmorPower(this, 7), 7));
                break;

            case 1:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_MSG));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                //10
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                //获得2点荆棘
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(this,this,new ThornsPower(this, 2), 2));
                break;

            case 2:
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_AND_BUFF_MSG));
                //清除所有角色的格挡，
                //造成等量伤害。
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new LoseBlockForEveryoneThenAttackAction(AbstractDungeon.player,this,DamageInfo.DamageType.NORMAL,AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(this.hasPower(PlatedArmorPower.POWER_ID)){
            AbstractPower p = getPower(PlatedArmorPower.POWER_ID);
            if(p.amount>=30){
                setMove((byte)2, Intent.ATTACK_DEBUFF, blockRemoveAttackInfo.base);
                return;
            }
        }

        if(i>=50){
            setMove((byte)1, Intent.ATTACK_BUFF, (this.damage.get(0)).base);
        }else{
            setMove((byte)0, Intent.BUFF);
        }
        moveCount ++;
    }

    public void updateBlockRemoveAttackAmount(){
        int totalBlock = 0;

        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster.isDead){continue;}
            totalBlock += monster.currentBlock;
        }
        totalBlock += AbstractDungeon.player.currentBlock;
        blockRemoveAttackInfo.base = totalBlock;

        if(this.nextMove == 2){
            setMove((byte)2, Intent.ATTACK_DEBUFF, blockRemoveAttackInfo.base);
            createIntent();
        }
    }
}
