package togawasakikomod;

import actlikeit.RazIntent.CustomIntent;
import actlikeit.dungeons.CustomDungeon;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import togawasakikomod.Actions.CharismaticIntangibleAction;
import togawasakikomod.annotations.CardEnable;
import togawasakikomod.annotations.CharismaticFormCopyEnable;
import togawasakikomod.annotations.RelicEnable;
import togawasakikomod.cards.BaseCard;
import togawasakikomod.Actions.DazzlingDamageAction;
import togawasakikomod.cards.SakikoDeck.Attacks.Kao;
import togawasakikomod.cards.SakikoDeck.Skills.Veritas;
import togawasakikomod.cards.SpecialDeck.Curses.Oblivionis;
import togawasakikomod.character.TogawaSakiko;
import togawasakikomod.dungeons.TheOblivion;
import togawasakikomod.effects.DazzlingAttackEffect;
import togawasakikomod.events.TheOblivionEvent;
import togawasakikomod.intents.MutsumiAttackIntent;
import togawasakikomod.monsters.oblivion.bosses.avemujica.MisumiUikaBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.WakabaMutsumiBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YahataUmiriBoss;
import togawasakikomod.monsters.oblivion.bosses.avemujica.YuutenjiNyamuBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.ChihayaAnonBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.NagasakiSoyoBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.ShiinaTakiBoss;
import togawasakikomod.monsters.oblivion.bosses.mygo.TakamatsuTomoriBoss;
import togawasakikomod.patches.CustomEnumPatch;
import togawasakikomod.patches.ObtainRewardEventPatch;
import togawasakikomod.potions.BasePotion;
import togawasakikomod.powers.buffs.*;
import togawasakikomod.relics.BaseRelic;
import togawasakikomod.rewards.PurgeReward;
import togawasakikomod.saveable.KingsSaveable;
import togawasakikomod.util.GeneralUtils;
import togawasakikomod.util.KeywordInfo;
import togawasakikomod.util.TextureLoader;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.nio.charset.StandardCharsets;
import java.util.*;

@SpireInitializer
public class TogawaSakikoMod implements
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        AddAudioSubscriber,
        PostInitializeSubscriber,
        PostPowerApplySubscriber,
        OnCardUseSubscriber,
        OnStartBattleSubscriber,
        OnPlayerTurnStartSubscriber,
        PostExhaustSubscriber,
        OnPlayerLoseBlockSubscriber,
        StartGameSubscriber,
        StartActSubscriber
    {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new TogawaSakikoMod();
        TogawaSakiko.Meta.registerColor();
        BaseMod.addSaveField("IsKing", new KingsSaveable());
    }

    @Override
    public void receiveEditCharacters() {
        TogawaSakiko.Meta.registerCharacter();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .any(AbstractCard.class,(info,card)->{
                    if(card.getClass().isAnnotationPresent(CardEnable.class)){
                        CardEnable enable = card.getClass().getAnnotation(CardEnable.class);
                        if(enable.enable())
                        {
                            BaseMod.addCard(card);
                            UnlockTracker.unlockCard(card.cardID);
                        }
                    }else{
                        BaseMod.addCard(card);
                        UnlockTracker.unlockCard(card.cardID);
                    }
                }); //Adds the cards
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if(relic.getClass().isAnnotationPresent(RelicEnable.class)){
                        RelicEnable enable = relic.getClass().getAnnotation(RelicEnable.class);
                        if(!enable.enable()){
                            return;
                        }
                    }

                    if (relic.pool != null){
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    }
                    else{
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic
                    }

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen){
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }


    public TogawaSakikoMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        registerPotions();
        registerMonsters();
        registerIntents();
        registerActs();
        registerEvents();

        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.

        //If you want to set up a config panel, that will be done here.
        //The Mod Badges page has a basic example of this, but setting up config is overall a bit complex.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);

        BaseMod.registerCustomReward(
                CustomEnumPatch.TOGAWASAKIKO_PURGEREWARD,
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    return new PurgeReward();
                },
                (customReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(customReward.type.toString(), null, 0, 0);
                });

        BaseMod.getModdedCharacters().forEach(character ->{
            if(character instanceof TogawaSakiko){
                Prefs prefs = character.getPrefs();
                if(prefs.getInteger("WIN_COUNT",0)<20){
                    prefs.getInteger("WIN_COUNT",20);
                }
                prefs.putBoolean(character.chosenClass.name()+"_WIN",true);
                prefs.putBoolean("ASCEND_0",true);
                prefs.putInteger("ASCENSION_LEVEL",20);
                //prefs.putInteger("LAST_ASCENSION_LEVEL",20);
                prefs.flush();
            }
        });
    }

    private  static void registerMonsters(){
        String iocnPath = "togawasakikomod/images/ui/map/boss/TheOblivion.png";
        String iocnOutlinePath = "togawasakikomod/images/ui/map/bossOutline/TheOblivion.png";

        //Register
        BaseMod.addBoss(
                TheEnding.ID,
                TheOblivion.ID,
                iocnPath,
                iocnOutlinePath
                );

        BaseMod.addMonster(ChihayaAnonBoss.ID,()-> new ChihayaAnonBoss(0,0));
        BaseMod.addMonster(ShiinaTakiBoss.ID,()-> new ShiinaTakiBoss(0,0));
        BaseMod.addMonster(NagasakiSoyoBoss.ID,()-> new NagasakiSoyoBoss(0,0));
        BaseMod.addMonster(TakamatsuTomoriBoss.ID,()-> new TakamatsuTomoriBoss(0,0));
        BaseMod.addMonster(WakabaMutsumiBoss.ID,()-> new WakabaMutsumiBoss(0,0));
        BaseMod.addMonster(YahataUmiriBoss.ID,()-> new YahataUmiriBoss(0,0));
        BaseMod.addMonster(YuutenjiNyamuBoss.ID,()-> new YuutenjiNyamuBoss(0,0));
        BaseMod.addMonster(MisumiUikaBoss.ID,()-> new MisumiUikaBoss(0,0));
    }

    private static void registerPotions() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BasePotion.class) //In the same package as this class
                .any(BasePotion.class, (info, potion) -> { //Run this code for any classes that extend this class
                    //These three null parameters are colors.
                    //If they're not null, they'll overwrite whatever color is set in the potions themselves.
                    //This is an old feature added before having potions determine their own color was possible.
                    BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass);
                    //playerClass will make a potion character-specific. By default, it's null and will do nothing.
                });
    }

    private static void registerIntents(){
        CustomIntent.add(new MutsumiAttackIntent());
    }
    private static void registerActs(){
        CustomDungeon.addAct(TheEnding.ID, new TheOblivion());
    }

    private  static void registerEvents(){
        BaseMod.addEvent(TheOblivionEvent.ID, TheOblivionEvent.class);
    }
    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "zhs";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                localizationPath(lang, "MonsterStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
        public static String vfxPath(String file) {
            return resourcesFolder + "/images/vfx/" + file;
        }
    public static String audioPath(String file) {
        return resourcesFolder + "/audio/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    public static String potionPath(String file) {
            return resourcesFolder + "/images/potions/" + file;
    }
    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = TogawaSakikoMod.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);
        if (resources.child("images").exists() && resources.child("localization").exists()) {
            return name;
        }

        throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                "\tat the top of the " + TogawaSakikoMod.class.getSimpleName() + " java file.");
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(TogawaSakikoMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public int receiveOnPlayerLoseBlock(int i) {
        AbstractPower p = AbstractDungeon.player.getPower(HypePower.POWER_ID);
        if(p != null && p.amount>0){ i = 0; }
        return i;
    }

        @FunctionalInterface
        public interface TriConsumer<A, B, C> {
            void accept(A a, B b, C c);
        }

        private static final List<TriConsumer<AbstractPower, AbstractCreature, AbstractCreature>> listeners
                = new ArrayList<>();

        public static void addListener(TriConsumer<AbstractPower, AbstractCreature, AbstractCreature> listener) {
            listeners.add(listener);
        }

        public static void removeListener(TriConsumer<AbstractPower, AbstractCreature, AbstractCreature> listener) {
            listeners.remove(listener);
        }

       //@Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        DazzlingEvent(abstractPower,target,source);
        KaoEvent(abstractPower,target,source);
        CharismaticFormEvent(abstractPower,target,source);
        for (TriConsumer<AbstractPower, AbstractCreature, AbstractCreature> listener : listeners) {
            if(listener!=null){
            listener.accept(abstractPower, target, source);
            }
        }
    }

    private void DazzlingEvent(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source){
        if(target.hasPower(DazzlingPower.POWER_ID)&& abstractPower.type == AbstractPower.PowerType.BUFF){
            abstractPower.flash();
            if(abstractPower.owner == target){
                if(abstractPower.owner == AbstractDungeon.player){
                    AbstractDungeon.actionManager.addToTop(new DazzlingDamageAction(new DamageInfo(abstractPower.owner, abstractPower.owner.getPower(DazzlingPower.POWER_ID).amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
                }else{
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new DazzlingAttackEffect(AbstractDungeon.player,false)));
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player,new DamageInfo(abstractPower.owner, abstractPower.owner.getPower(DazzlingPower.POWER_ID).amount, DamageInfo.DamageType.THORNS)));
                }

                if(abstractPower.owner.hasPower(GirlOfSpringPower.POWER_ID)){
                    System.out.println("----------------------Found");
                    AbstractPower power = abstractPower.owner.getPower(GirlOfSpringPower.POWER_ID);
                    power.flash();
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(power.owner,power.amount));
                }
            }
        }
    }

    private void KaoEvent(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source){
        if(target!= AbstractDungeon.player && abstractPower.type == AbstractPower.PowerType.BUFF){

            ArrayList<AbstractCard> allCards = new ArrayList<>(AbstractDungeon.player.hand.group);
            //allCards.addAll(AbstractDungeon.player.discardPile.group);
            //allCards.addAll(AbstractDungeon.player.drawPile.group);

            for(AbstractCard card : allCards){
                if(card.cardID.equals(Kao.ID)){
                    card.flash();
                    card.glowColor = Color.YELLOW;
                    //card.isMagicNumberModified = true;
                    card.misc+=1;
                    card.applyPowers();
                }
            }
        }
    }

    private void CharismaticFormEvent(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source){
        if(target!= AbstractDungeon.player && abstractPower.type == AbstractPower.PowerType.BUFF){
            if(AbstractDungeon.player.hasPower(CharismaticFormPower.POWER_ID)){
                //Exceptions
                if(Objects.equals(abstractPower.ID, ModeShiftPower.POWER_ID)){return;}
                if(Objects.equals(abstractPower.ID, PainfulStabsPower.POWER_ID)){return;}
                if(Objects.equals(abstractPower.ID, BeatOfDeathPower.POWER_ID)){return;}
                if(Objects.equals(abstractPower.ID, SharpHidePower.POWER_ID)){return;}
                if(Objects.equals(abstractPower.ID, BackAttackPower.POWER_ID)){return;}
                if(Objects.equals(abstractPower.ID, SurroundedPower.POWER_ID)){return;}
                if(Objects.equals(abstractPower.ID, MinionPower.POWER_ID)){return;}

                if(abstractPower.getClass().isAnnotationPresent(CharismaticFormCopyEnable.class)){
                    CharismaticFormCopyEnable enable = abstractPower.getClass().getAnnotation(CharismaticFormCopyEnable.class);
                    if(!enable.enable()) {return;}
                }

                AbstractPower copy = null;

                if (abstractPower.ID.equals(IntangiblePower.POWER_ID)) {
                    copy = new IntangiblePlayerPower(AbstractDungeon.player, abstractPower.amount);
                } else {
                    try {
                        copy = ((CloneablePowerInterface) abstractPower).makeCopy();
                    } catch (ClassCastException e) {
                        logger.error("e: ", e);
                    }
                }

//                if(abstractPower.ID.equals(FreeAttackPower.POWER_ID)){
//                    int freeAttackAmount = abstractPower.amount;
//                    if(freeAttackAmount <= 0){freeAttackAmount = 1;}
//                    copy = new FreeAttackPower(AbstractDungeon.player,abstractPower.amount);
//                }

                if(copy!=null){
                    //Flash
                    AbstractPower form = AbstractDungeon.player.getPower(CharismaticFormPower.POWER_ID);
                    form.flash();
                    for(int i = 0; i<form.amount;i++){
                        //Apply copy
                        copy.owner = AbstractDungeon.player;
                        if (copy.ID.equals(IntangiblePlayerPower.POWER_ID)) {
                            AbstractDungeon.actionManager.addToTop(new CharismaticIntangibleAction(copy));
                        }else if(copy.ID.equals(FlightPower.POWER_ID)){
                            AbstractPower flight = new PlayerFilightPower(AbstractDungeon.player,copy.amount);
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, flight));
                        }
                        else {
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, copy, copy.amount));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        AbstractCard oblivionis = null;
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card.cardID == Oblivionis.ID){
                oblivionis = card;
                break;
            }
        }

        if(oblivionis !=null && abstractCard.type == AbstractCard.CardType.ATTACK){
            oblivionis.flash(Color.RED);
            abstractCard.exhaustOnUseOnce = true;
        }
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        TogawaSakiko.LastRoundPowerLost.clear();
        TogawaSakiko.LastRoundPowerLost = new ArrayList<>(TogawaSakiko.CurrentRoundPowerLost);
        TogawaSakiko.CurrentRoundPowerLost.clear();
        System.out.println("Turn change buff rotate. Current:"+TogawaSakiko.CurrentRoundPowerLost.size()+" .Previous:"+TogawaSakiko.LastRoundPowerLost.size());
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(makeID("Sakiko-Intro"),audioPath("sakiko/Intro.wav"));
        BaseMod.addAudio(makeID("Sakiko-Accomplice"),audioPath("sakiko/Accomplice.wav"));
        BaseMod.addAudio(makeID("Sakiko-AreTheseLyrics"),audioPath("sakiko/AreTheseLyrics.wav"));
        BaseMod.addAudio(makeID("Sakiko-AuthorityRestoration"),audioPath("sakiko/AuthorityRestoration.wav"));
        BaseMod.addAudio(makeID("Sakiko-AveMujica"),audioPath("sakiko/AveMujica.wav"));
        BaseMod.addAudio(makeID("Sakiko-BandInvitation"),audioPath("sakiko/BandInvitation.wav"));
        BaseMod.addAudio(makeID("Sakiko-Cruelty"),audioPath("sakiko/Cruelty.wav"));
        BaseMod.addAudio(makeID("Sakiko-Doloris"),audioPath("sakiko/Doloris.wav"));
        BaseMod.addAudio(makeID("Sakiko-General1"),audioPath("sakiko/General1.wav"));
        BaseMod.addAudio(makeID("Sakiko-General2"),audioPath("sakiko/General2.wav"));
        BaseMod.addAudio(makeID("Sakiko-Greetings"),audioPath("sakiko/Greetings.wav"));
        BaseMod.addAudio(makeID("Sakiko-InnerCry"),audioPath("sakiko/InnerCry.wav"));
        BaseMod.addAudio(makeID("Sakiko-Masks"),audioPath("sakiko/Masks.wav"));
        BaseMod.addAudio(makeID("Sakiko-Mortis"),audioPath("sakiko/Mortis.wav"));
        BaseMod.addAudio(makeID("Sakiko-Oblivionis"),audioPath("sakiko/Oblivionis.wav"));
        BaseMod.addAudio(makeID("Sakiko-Others1"),audioPath("sakiko/Others1.wav"));
        BaseMod.addAudio(makeID("Sakiko-OurSong"),audioPath("sakiko/OurSong.wav"));
        BaseMod.addAudio(makeID("Sakiko-PhantomOfMutsumi"),audioPath("sakiko/PhantomOfMutsumi.wav"));
        BaseMod.addAudio(makeID("Sakiko-PhantomOfSoyo"),audioPath("sakiko/PhantomOfSoyo.wav"));
        BaseMod.addAudio(makeID("Sakiko-PhantomOfTaki"),audioPath("sakiko/PhantomOfTaki.wav"));
        BaseMod.addAudio(makeID("Sakiko-PhantomOfTomori"),audioPath("sakiko/PhantomOfTomori.wav"));
        BaseMod.addAudio(makeID("Sakiko-RhinocerosBeetle"),audioPath("sakiko/RhinocerosBeetle.wav"));
        BaseMod.addAudio(makeID("Sakiko-SharedDestiny"),audioPath("sakiko/SharedDestiny.wav"));
        BaseMod.addAudio(makeID("Sakiko-StayElegance"),audioPath("sakiko/StayElegance.wav"));
        BaseMod.addAudio(makeID("Sakiko-Timoris"),audioPath("sakiko/Timoris.wav"));
        BaseMod.addAudio(makeID("Sakiko-WishToBecomeHuman"),audioPath("sakiko/WishToBecomeHuman.wav"));
        BaseMod.addAudio(makeID("Sakiko-WishYouGoodLuck"),audioPath("sakiko/WishYouGoodLuck.wav"));
        BaseMod.addAudio(makeID("Sakiko-Worldview"),audioPath("sakiko/Worldview.wav"));
        BaseMod.addAudio(makeID("Sakiko-PrimoDieInScaena"),audioPath("sakiko/PrimoDieInScaena.wav"));
        BaseMod.addAudio(makeID("Sakiko-Tiredness"),audioPath("sakiko/Tiredness.wav"));
        BaseMod.addAudio(makeID("Sakiko-Perfection"),audioPath("sakiko/Perfection.wav"));
        BaseMod.addAudio(makeID("Sakiko-AsYourHeartDesires"),audioPath("sakiko/AsYourHeartDesires.wav"));
        BaseMod.addAudio(makeID("Sakiko-Carefree"),audioPath("sakiko/Carefree.wav"));
        BaseMod.addAudio(makeID("Sakiko-ASplitMoment"),audioPath("sakiko/ASplitMoment.wav"));
        BaseMod.addAudio(makeID("Sakiko-AveMujica"),audioPath("sakiko/AveMujica.wav"));
        BaseMod.addAudio(makeID("Sakiko-CountingStars"),audioPath("sakiko/CountingStars.wav"));
        BaseMod.addAudio(makeID("Sakiko-EdgeOfBreakdown"),audioPath("sakiko/EdgeOfBreakdown.wav"));
        BaseMod.addAudio(makeID("Sakiko-Fearless"),audioPath("sakiko/Fearless.wav"));
        BaseMod.addAudio(makeID("Sakiko-HeartsBarrier"),audioPath("sakiko/HeartsBarrier.wav"));
        BaseMod.addAudio(makeID("Sakiko-MasqueradeRhapsodyRequest"),audioPath("sakiko/MasqueradeRhapsodyRequest.wav"));
        BaseMod.addAudio(makeID("Sakiko-PerdereOmnia"),audioPath("sakiko/PerdereOmnia.wav"));
        BaseMod.addAudio(makeID("Sakiko-Pride"),audioPath("sakiko/Pride.wav"));
        BaseMod.addAudio(makeID("Sakiko-TheGirlWithFlaxenHair"),audioPath("sakiko/TheGirlWithFlaxenHair.wav"));
        BaseMod.addAudio(makeID("Sakiko-WishFulfilled"),audioPath("sakiko/WishFulfilled.wav"));
        BaseMod.addAudio(makeID("Sakiko-ClockOut"),audioPath("sakiko/ClockOut.wav"));
        BaseMod.addAudio(makeID("Sakiko-DesuWa"),audioPath("sakiko/DesuWa.wav"));

        BaseMod.addAudio(makeID("Sakiko-Cutscene-Ending1"),audioPath("cutscene/ending1.wav"));
        BaseMod.addAudio(makeID("Sakiko-Cutscene-Ending2"),audioPath("cutscene/ending2.wav"));
        BaseMod.addAudio(makeID("Sakiko-Cutscene-Ending3"),audioPath("cutscene/ending3.wav"));

        BaseMod.addAudio(makeID("MusicPulseAttackEffect"),audioPath("vfx/MusicPulseAttackEffect.wav"));
        BaseMod.addAudio(makeID("DazzlingAttackEffect"),audioPath("vfx/DazzlingAttackEffect.wav"));

        BaseMod.addAudio(makeID("Sakiko-Hurt1"),audioPath("sakiko/Hurt1.wav"));
        BaseMod.addAudio(makeID("Sakiko-Hurt2"),audioPath("sakiko/Hurt2.wav"));
        BaseMod.addAudio(makeID("Sakiko-Hurt3"),audioPath("sakiko/Hurt3.wav"));

//        BaseMod.addAudio(makeID("Music-GMGU"),audioPath("music/GMGU.wav"));
//        BaseMod.addAudio(makeID("Music-Haruhikage"),audioPath("music/Haruhikage.wav"));
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        KingsSaveable.IsKing = false;
        PridePower.copies.clear();
       TogawaSakiko.LastRoundPowerLost.clear();
       TogawaSakiko.CurrentRoundPowerLost.clear();
    }

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(Objects.equals(card.cardID, Veritas.ID)){
                AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(card));
            }
        }
    }

        @Override
        public void receiveStartGame() {
            removeItems(AbstractDungeon.commonRelicPool,DuVuDoll.ID);
            removeItems(AbstractDungeon.commonRelicPool, DarkstonePeriapt.ID);
            removeItems(AbstractDungeon.uncommonRelicPool,DuVuDoll.ID);
            removeItems(AbstractDungeon.uncommonRelicPool,DarkstonePeriapt.ID);
            removeItems(AbstractDungeon.rareRelicPool,DuVuDoll.ID);
            removeItems(AbstractDungeon.rareRelicPool,DarkstonePeriapt.ID);
            ObtainRewardEventPatch.removeAll();
        }

        @Override
        public void receiveStartAct() {
            removeItems(AbstractDungeon.commonRelicPool,DuVuDoll.ID);
            removeItems(AbstractDungeon.commonRelicPool, DarkstonePeriapt.ID);
            removeItems(AbstractDungeon.uncommonRelicPool,DuVuDoll.ID);
            removeItems(AbstractDungeon.uncommonRelicPool,DarkstonePeriapt.ID);
            removeItems(AbstractDungeon.rareRelicPool,DuVuDoll.ID);
            removeItems(AbstractDungeon.rareRelicPool,DarkstonePeriapt.ID);
        }

//    @Override
//    public void receiveCardUsed(AbstractCard abstractCard) {
//        if(AbstractDungeon.player.hasPower(OblivionisPower.POWER_ID)){
//            AbstractPower power = AbstractDungeon.player.getPower(OblivionisPower.POWER_ID);
//            if(abstractCard.type == AbstractCard.CardType.ATTACK){
//                power.flash();
//                abstractCard.exhaustOnUseOnce = true;
//            }
//        }
//
//    }

        public static void removeItems(ArrayList<String> list, String typeToRemove) {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (Objects.equals(typeToRemove, item)) {
                    iterator.remove();
                }
            }
        }

    }
