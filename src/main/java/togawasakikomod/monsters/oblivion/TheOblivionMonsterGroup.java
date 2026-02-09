package togawasakikomod.monsters.oblivion;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import togawasakikomod.monsters.oblivion.bosses.FinalBossMonster;
import togawasakikomod.monsters.oblivion.bosses.avemujica.MisumiUikaBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.WakabaMutsumiBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YahataUmiriBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.ChihayaAnonBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.NagasakiSoyoBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.ShiinaTakiBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.TakamatsuTomoriBoss;

import java.util.Objects;

public class TheOblivionMonsterGroup extends MonsterGroup {
    FinalBossMonster leftMonster;
    FinalBossMonster rightMonster;
    Random monsterRng = new Random(Settings.seed);

    String leftMonsterID = null;
    String rightMonsterID = null;

    String[] monsterPool = new String[]{
            MisumiUikaBoss.ID,
            WakabaMutsumiBoss.ID,
            YahataUmiriBoss.ID,
            YuutenjiNyamuBoss.ID,
            ChihayaAnonBoss.ID,
            NagasakiSoyoBoss.ID,
            ShiinaTakiBoss.ID,
            TakamatsuTomoriBoss.ID
    };

    public TheOblivionMonsterGroup() {
        super(new AbstractMonster[0]);
        getBosses();
    }

    public boolean shouldFlipVfx() {
        return ((AbstractMonster) this.monsters.get(1)).isDying;
    }

    public void getBosses(){
        int firstIndex = monsterRng.random(0,monsterPool.length-1);
        int secondIndex = monsterRng.random(0,monsterPool.length-1);
        while (firstIndex == secondIndex){
            secondIndex = monsterRng.random(0,monsterPool.length-1);
        }
        leftMonsterID = monsterPool[firstIndex];
        rightMonsterID = monsterPool[secondIndex];
        if(Objects.equals(rightMonsterID, WakabaMutsumiBoss.ID)){
            String temp = rightMonsterID;
            rightMonsterID = leftMonsterID;
            leftMonsterID = temp;
        }
        leftMonster = getBossByID(leftMonsterID,true);
        rightMonster = getBossByID(rightMonsterID,false);
        addMonster(0,leftMonster);
        addMonster(1,rightMonster);
    }


    public FinalBossMonster getBossByID(String id, boolean isLeft){
        FinalBossMonster monster = null;
        float leftOffsetX = -1000f;
        float rightOffsetX = 0f;
        if(Objects.equals(id, MisumiUikaBoss.ID)){
            monster = new MisumiUikaBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }

        if(Objects.equals(id, WakabaMutsumiBoss.ID)){
            monster = new WakabaMutsumiBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }

        if(Objects.equals(id, YahataUmiriBoss.ID)){
            monster = new YahataUmiriBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }

        if(Objects.equals(id, YuutenjiNyamuBoss.ID)){
            monster = new YuutenjiNyamuBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }

        if(Objects.equals(id, ChihayaAnonBoss.ID)){
            monster = new ChihayaAnonBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }

        if(Objects.equals(id, NagasakiSoyoBoss.ID)){
            monster = new NagasakiSoyoBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }

        if(Objects.equals(id, ShiinaTakiBoss.ID)){
            monster = new ShiinaTakiBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }

        if(Objects.equals(id, TakamatsuTomoriBoss.ID)){
            monster = new TakamatsuTomoriBoss(isLeft? leftOffsetX:rightOffsetX,0);
            monster.flipHorizontal = !isLeft;
        }
        return monster;
    }
}
