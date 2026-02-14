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
        FinalBossMonster.isBGMPlayed = false;
    }

    public TheOblivionMonsterGroup(String leftMonsterID, String rightMonsterID) {
        super(new AbstractMonster[0]);
        this.leftMonsterID = leftMonsterID;
        this.rightMonsterID = rightMonsterID;
        getBosses();
        FinalBossMonster.isBGMPlayed = false;
    }

    public boolean shouldFlipVfx() {
        return ((AbstractMonster) this.monsters.get(1)).isDying;
    }

    public void getBosses(){
        if(leftMonsterID == null || rightMonsterID == null){
            int firstIndex = monsterRng.random(0,monsterPool.length-1);
            int secondIndex = monsterRng.random(0,monsterPool.length-1);
            while (firstIndex == secondIndex){
                secondIndex = monsterRng.random(0,monsterPool.length-1);
            }
            leftMonsterID = monsterPool[firstIndex];
            rightMonsterID = monsterPool[secondIndex];
        }

        //睦左
        if(Objects.equals(rightMonsterID, WakabaMutsumiBoss.ID)){
            String temp = rightMonsterID;
            rightMonsterID = leftMonsterID;
            leftMonsterID = temp;
        }

        //猫右
        if(Objects.equals(leftMonsterID, YuutenjiNyamuBoss.ID)){
            String temp = leftMonsterID;
            leftMonsterID = rightMonsterID;
            rightMonsterID = temp;
        }

        //素世左 海玲右
        if(Objects.equals(rightMonsterID, NagasakiSoyoBoss.ID) && Objects.equals(leftMonsterID, YahataUmiriBoss.ID)){
            String temp = leftMonsterID;
            leftMonsterID = rightMonsterID;
            rightMonsterID = temp;
        }

        //素世左 立希右
        if(Objects.equals(rightMonsterID, NagasakiSoyoBoss.ID) && Objects.equals(leftMonsterID, ShiinaTakiBoss.ID)){
            String temp = leftMonsterID;
            leftMonsterID = rightMonsterID;
            rightMonsterID = temp;
        }

        //灯左 素世右
        if(Objects.equals(rightMonsterID, TakamatsuTomoriBoss.ID) && Objects.equals(leftMonsterID, NagasakiSoyoBoss.ID)){
            String temp = leftMonsterID;
            leftMonsterID = rightMonsterID;
            rightMonsterID = temp;
        }

        //初华左 灯右
        if(Objects.equals(rightMonsterID, MisumiUikaBoss.ID) && Objects.equals(leftMonsterID, TakamatsuTomoriBoss.ID)){
            String temp = leftMonsterID;
            leftMonsterID = rightMonsterID;
            rightMonsterID = temp;
        }

        //海玲左 立希右
        if(Objects.equals(rightMonsterID, YahataUmiriBoss.ID) && Objects.equals(leftMonsterID, ShiinaTakiBoss.ID)){
            String temp = leftMonsterID;
            leftMonsterID = rightMonsterID;
            rightMonsterID = temp;
        }

        //爱音左 初华右
        if(Objects.equals(rightMonsterID, ChihayaAnonBoss.ID) && Objects.equals(leftMonsterID, MisumiUikaBoss.ID)){
            String temp = leftMonsterID;
            leftMonsterID = rightMonsterID;
            rightMonsterID = temp;
        }

        leftMonster = getBossByID(leftMonsterID,true);
        rightMonster = getBossByID(rightMonsterID,false);
        leftMonster.setOppositeMonster(rightMonster);
        rightMonster.setOppositeMonster(leftMonster);
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
