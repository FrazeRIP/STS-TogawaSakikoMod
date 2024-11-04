package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import togawasakikomod.powers.buffs.HypePower;

public class AuthorityRestorationAction extends AbstractGameAction {


    public AuthorityRestorationAction(int fixedAmount) {
        this.amount = fixedAmount;
    }

    @Override
    public void update() {
        for(AbstractCreature m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(m.hasPower(ArtifactPower.POWER_ID)){
                this.amount += m.getPower(ArtifactPower.POWER_ID).amount;
                addToTop(new RemoveSpecificPowerAction(m,AbstractDungeon.player,ArtifactPower.POWER_ID));
            }
        }

        if(AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)){
            this.amount += AbstractDungeon.player.getPower(ArtifactPower.POWER_ID).amount;
            addToTop(new RemoveSpecificPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    ArtifactPower.POWER_ID));
        }

        if(amount>0){
            addToTop(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new HypePower(AbstractDungeon.player,amount)));

            addToTop(new GainBlockAction(AbstractDungeon.player,amount));
        }

        this.isDone = true;
    }
}
