package togawasakikomod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import togawasakikomod.cards.SakikoDeck.Attacks.*;

import java.util.ArrayList;

public class CricifuxXAction extends AbstractGameAction {

    AbstractPlayer p;
    AbstractCreature m;

    int damage;
    int block;

    public CricifuxXAction(int amount, int damage, int block, AbstractPlayer p, AbstractCreature m){
        this.amount = amount;
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.block = block;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            amount += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }

        for(int i = 0; i< this.amount;i++){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            addToBot(new GainBlockAction(p,p,block));
        }
        this.isDone = true;
    }
}
