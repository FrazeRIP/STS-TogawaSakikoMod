package togawasakikomod.monsters.oblivion.bosses.mygo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.powers.monsters.TemporalLongingPower;
import togawasakikomod.util.TextureLoader;

public class NagasakiSoyoBoss extends FinalBossMonster {

    public static final String ID =  TogawaSakikoMod.makeID(NagasakiSoyoBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final int MAX_HEALTH = 550;
    private static final float hb_x = 0;
    private static final float hb_y = 50;
    private static final float hb_w = 164;
    private static final float hb_h = 350;
    private static final String IMAGE_URL = TextureLoader.getMonsterTextureString(NagasakiSoyoBoss.class.getSimpleName());

    private static final String DAMAGE_MSG = DIALOG[0];
    private static final String DAMAGE_AND_BUFF_MSG = DIALOG[1];

    private int moveCount = -1;
    TemporalLongingPower power;

    public NagasakiSoyoBoss(float offsetX, float offsetY) {
        super(NAME, ID, MAX_HEALTH, hb_x, hb_y, hb_w, hb_h, IMAGE_URL, offsetX, offsetY-35);

        this.damage.add(new DamageInfo((AbstractCreature)this, 15));
        this.flipHorizontal = true;
        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
    }

    public void usePreBattleAction() {
        super.usePreBattleAction();
        power = new TemporalLongingPower(this);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,power));
    }

    @Override
    public void takeTurn() {
        int xAmount = 0;
        if(this.hasPower(TemporalLongingPower.POWER_ID)){
            TemporalLongingPower power = (TemporalLongingPower)this.getPower(TemporalLongingPower.POWER_ID);
            xAmount = power.amount2;
        }

        switch (this.nextMove){
            case 0 :
                //8*3
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_AND_BUFF_MSG));

                //给予所有敌人2+N点力量
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (!m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m,30));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 2+xAmount), 2+xAmount));
                    }
                }
                break;

            case 1:
                //15*2
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DAMAGE_MSG));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0)));

                //给予所有敌人2+N层多重护甲
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (!m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new PlatedArmorPower(m, 2+xAmount), 2+xAmount));
                    }
                }
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        if(moveCount <0){
            moveCount = i%2;
        }

        switch (moveCount%2){
            case 0:
                setMove((byte)0, Intent.DEFEND_BUFF);
                break;
            case 1:
                setMove((byte)1, Intent.ATTACK_BUFF, (this.damage.get(0)).base,2,true);
                break;
        }
        moveCount ++;
    }
}
