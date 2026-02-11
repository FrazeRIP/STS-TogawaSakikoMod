package togawasakikomod.dungeons;

import actlikeit.dungeons.CustomDungeon;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import togawasakikomod.monsters.oblivion.bosses.mygo.ChihayaAnonBoss;
import togawasakikomod.scenes.TheOblivionScene;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.saveFile;
import static togawasakikomod.TogawaSakikoMod.audioPath;
import static togawasakikomod.TogawaSakikoMod.makeID;

public class TheOblivion extends CustomDungeon {
    public static final String ID = makeID(TheOblivion.class.getSimpleName()); //From the main mod file for best practices
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String NAME = TEXT[0];

    public TheOblivion() {
        super(NAME, ID,false);
        finalAct = true;
        if (scene != null)
            scene.dispose();
        scene = (AbstractScene)new TheOblivionScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        initializeLevelSpecificChances();
        mapRng = new Random(Settings.seed + (AbstractDungeon.actNum * 300L));
        setUpMusic();
    }

    public TheOblivion(CustomDungeon cd, AbstractPlayer p, ArrayList<String> emptyList) {
        super(cd, p, emptyList);
        //this.finalAct = true;
        if (scene != null){
            scene.dispose();
        }
        scene = (AbstractScene)new TheOblivionScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        initializeLevelSpecificChances();
        mapRng = new Random(Settings.seed + (AbstractDungeon.actNum * 300L));
        setUpMusic();
    }

    public TheOblivion(CustomDungeon cd, AbstractPlayer p, SaveFile sf) {
        super(cd, p, sf);
        CardCrawlGame.dungeon = this;
        if (scene != null)
            scene.dispose();
        scene = (AbstractScene)new TheOblivionScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        initializeLevelSpecificChances();
        miscRng = new Random(Long.valueOf(Settings.seed.longValue() + saveFile.floor_num));
        CardCrawlGame.music.changeBGM(id);
        mapRng = new Random(Long.valueOf(Settings.seed.longValue() + (saveFile.act_num * 300)));
        firstRoomChosen = true;
        populatePathTaken(saveFile);
    }

    private void setUpMusic(){
        this.setMainMusic(audioPath("music/GMGU.ogg"));
        this.addTempMusic("Haruhikage", (audioPath("music/Haruhikage.ogg")));
    }

    @Override
    public AbstractScene DungeonScene() {
        return new TheOblivionScene();
    }

    public String getBodyText() {
        return  TEXT[3];
    }

    public String getOptionText() {
        return  TEXT[2];
    }

    public String getActNumberText() {
        return TEXT[1];
    }

    protected void initializeLevelSpecificChances() {
        shopRoomChance = 0.05F;
        restRoomChance = 0.12F;
        treasureRoomChance = 0.0F;
        eventRoomChance = 0.22F;
        eliteRoomChance = 0.08F;
        smallChestChance = 0;
        mediumChestChance = 100;
        largeChestChance = 0;
        commonRelicChance = 0;
        uncommonRelicChance = 100;
        rareRelicChance = 0;
        colorlessRareChance = 0.3F;
        if (AbstractDungeon.ascensionLevel >= 12) {
            cardUpgradedChance = 0.25F;
        } else {
            cardUpgradedChance = 0.5F;
        }
    }

    @Override
    protected void makeMap() {
        long startTime = System.currentTimeMillis();
        map = new ArrayList<>();
        ArrayList<MapRoomNode> row1 = new ArrayList<>();
        MapRoomNode restNode = new MapRoomNode(3, 0);
        restNode.room = new RestRoom();
        MapRoomNode shopNode = new MapRoomNode(3, 1);
        shopNode.room = new ShopRoom();
        MapRoomNode bossNode = new MapRoomNode(3, 2);
        bossNode.room = new MonsterRoomBoss();
        MapRoomNode victoryNode = new MapRoomNode(3, 3);
        victoryNode.room = new TrueVictoryRoom();
        connectNode(restNode, shopNode);
        shopNode.addEdge(new MapEdge(shopNode.x, shopNode.y, shopNode.offsetX, shopNode.offsetY, bossNode.x, bossNode.y, bossNode.offsetX, bossNode.offsetY, false));
        row1.add(new MapRoomNode(0, 0));
        row1.add(new MapRoomNode(1, 0));
        row1.add(new MapRoomNode(2, 0));
        row1.add(restNode);
        row1.add(new MapRoomNode(4, 0));
        row1.add(new MapRoomNode(5, 0));
        row1.add(new MapRoomNode(6, 0));
        ArrayList<MapRoomNode> row2 = new ArrayList<>();
        row2.add(new MapRoomNode(0, 1));
        row2.add(new MapRoomNode(1, 1));
        row2.add(new MapRoomNode(2, 1));
        row2.add(shopNode);
        row2.add(new MapRoomNode(4, 1));
        row2.add(new MapRoomNode(5, 1));
        row2.add(new MapRoomNode(6, 1));
        ArrayList<MapRoomNode> row3 = new ArrayList<>();
        row3.add(new MapRoomNode(0, 2));
        row3.add(new MapRoomNode(1, 2));
        row3.add(new MapRoomNode(2, 2));
        row3.add(bossNode);
        row3.add(new MapRoomNode(4, 2));
        row3.add(new MapRoomNode(5, 2));
        row3.add(new MapRoomNode(6, 2));
        ArrayList<MapRoomNode> row4 = new ArrayList<>();
        row4.add(new MapRoomNode(0, 3));
        row4.add(new MapRoomNode(1, 3));
        row4.add(new MapRoomNode(2, 3));
        row4.add(victoryNode);
        row4.add(new MapRoomNode(4, 3));
        row4.add(new MapRoomNode(5, 3));
        row4.add(new MapRoomNode(6, 3));
        map.add(row1);
        map.add(row2);
        map.add(row3);
        map.add(row4);
        logger.info("Generated the following dungeon map:");
        logger.info(MapGenerator.toString(map, Boolean.valueOf(true)));
        logger.info("Game Seed: " + Settings.seed);
        logger.info("Map generation time: " + (System.currentTimeMillis() - startTime) + "ms");
        firstRoomChosen = false;
        fadeIn();
    }

    private void connectNode(MapRoomNode src, MapRoomNode dst) {
        src.addEdge(new MapEdge(src.x, src.y, src.offsetX, src.offsetY, dst.x, dst.y, dst.offsetX, dst.offsetY, false));
    }

    protected void initializeBoss() {
        bossList.add(TheOblivion.ID);
    }

    @Override
    public void nextRoomTransition(SaveFile saveFile) {
        super.nextRoomTransition(saveFile);
        if (getCurrRoom() instanceof MonsterRoom && lastCombatMetricKey.equals(TheOblivion.ID)) {
            player.movePosition(Settings.WIDTH / 2.0F, floorY);
        }
    }

    protected void generateMonsters() {
        monsterList = new ArrayList<>();
        monsterList.add(TheOblivion.ID);
        monsterList.add(TheOblivion.ID);
        monsterList.add(TheOblivion.ID);
        eliteMonsterList = new ArrayList<>();
        eliteMonsterList.add(TheOblivion.ID);
        eliteMonsterList.add(TheOblivion.ID);
        eliteMonsterList.add(TheOblivion.ID);
    }

    protected void initializeEventList() {}

    protected void initializeEventImg() {
        if (eventBackgroundImg != null) {
            eventBackgroundImg.dispose();
            eventBackgroundImg = null;
        }
        eventBackgroundImg = ImageMaster.loadImage("images/ui/event/panel.png");
    }

    protected void initializeShrineList() {}
}
