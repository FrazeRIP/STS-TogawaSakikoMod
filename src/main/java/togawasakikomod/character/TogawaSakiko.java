package togawasakikomod.character;

import basemod.BaseMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import javassist.CtBehavior;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.cards.SakikoDeck.Skills.Defend;
import togawasakikomod.cards.SakikoDeck.Attacks.Strike;
import togawasakikomod.cards.SakikoDeck.Attacks.TheMoonlightSonata;
import togawasakikomod.others.SakikoCutscenePanel;
import togawasakikomod.relics.MonochromeHairband;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static togawasakikomod.TogawaSakikoMod.*;

public class TogawaSakiko extends CustomPlayer {
    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 72;
    public static final int STARTING_GOLD = 50;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    //Strings
    private static final String ID = makeID(TogawaSakiko.class.getSimpleName()); //This should match whatever you have in the CharacterStrings.json file
    private static String[] getNames() { return CardCrawlGame.languagePack.getCharacterString(ID).NAMES; }
    private static String[] getText() { return CardCrawlGame.languagePack.getCharacterString(ID).TEXT; }

    //Buffs
    public static ArrayList<AbstractPower> LastRoundPowerLost= new ArrayList<>();
    public static ArrayList<AbstractPower> CurrentRoundPowerLost= new ArrayList<>();

    //Audio
    public static final float CARD_AUDIO_COOLDOWN = 5f;
    public static float CardAudioTimer = 0f;

    public static final float HURT_AUDIO_COOLDOWN = 1f;
    public static float HurtAudioTimer = 0f;

    //This static class is necessary to avoid certain quirks of Java classloading when registering the character.
    public static class Meta {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static PlayerClass TOGAWA_SAKIKO;
        @SpireEnum(name = "TOGAWA_SAKIKO_GRAY_COLOR") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "TOGAWA_SAKIKO_GRAY_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        //Character select images
        private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
        private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

        //Character card images
        private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
        private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
        private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
        private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
        private static final String BG_POWER = characterPath("cardback/bg_power.png");
        private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
        private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
        private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
        private static final String SMALL_ORB = characterPath("cardback/small_orb.png");

        //This is used to color *some* images, but NOT the actual cards. For that, edit the images in the cardback folder!
        private static final Color cardColor = new Color(128f/255f, 128f/255f, 128f/255f, 1f);

        //Methods that will be used in the main mod file
        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, cardColor,
                    BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                    BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                    SMALL_ORB);
        }

        public static void registerCharacter() {
            BaseMod.addCharacter(new TogawaSakiko(), CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT);
        }
    }


    //In-game images
    private static final String SHOULDER_1 = characterPath("shoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.

    //Textures used for the energy orb
    private static final String[] orbTextures = {
            characterPath("energyorb/layer1.png"), //When you have energy
            characterPath("energyorb/layer2.png"),
            characterPath("energyorb/layer3.png"),
            characterPath("energyorb/layer4.png"),
            characterPath("energyorb/layer5.png"),
            characterPath("energyorb/cover.png"), //"container"
            characterPath("energyorb/layer1d.png"), //When you don't have energy
            characterPath("energyorb/layer2d.png"),
            characterPath("energyorb/layer3d.png"),
            characterPath("energyorb/layer4d.png"),
            characterPath("energyorb/layer5d.png")
    };

    //Speeds at which each layer of the energy orb texture rotates. Negative is backwards.
    private static final float[] layerSpeeds = new float[]{
            -20.0F,
            20.0F,
            -40.0F,
            40.0F,
            0F
    };


    //Actual character class code below this point

    public TogawaSakiko() {
        super(getNames()[0], Meta.TOGAWA_SAKIKO,
                new CustomEnergyOrb(orbTextures, characterPath("energyorb/vfx.png"), layerSpeeds), //Energy Orb
                new AbstractAnimation() { //Change the Animation line to this
                    @Override
                    public Type type() {
                        return Type.NONE; //A NONE animation results in the image given in initializeClass being used
                    }
                });

        initializeClass(characterPath("image.png"), //The image to use. The rest of the method is unchanged.
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                -10F, -10.0F, 164, 323, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

//        super(getNames()[0], Meta.TOGAWA_SAKIKO,
//                new CustomEnergyOrb(orbTextures, characterPath("energyorb/vfx.png"), layerSpeeds), //Energy Orb
//                new SpriterAnimation(characterPath("animation/default.scml"))); //Animation
//
//        initializeClass(null,
//                SHOULDER_2,
//                SHOULDER_1,
//                CORPSE,
//                getLoadout(),
//                20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
//                new EnergyManager(ENERGY_PER_TURN));

        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);

    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(TheMoonlightSonata.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);

        return retVal;
    }
    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(MonochromeHairband.ID);

        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //This card is used for the Gremlin card matching game.
        //It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Strike_Red();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA(getCustomModeCharacterButtonSoundKey(), 0);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return makeID("Sakiko-Intro");
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return getNames()[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return getNames()[1];
    }
    @Override
    public String getSpireHeartText() {
        return getText()[1];
    }
    @Override
    public String getVampireText() {
        return getText()[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(getNames()[0], getText()[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Meta.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new TogawaSakiko();
    }

    @Override
    public void update(){
        super.update();
        if(CardAudioTimer >0){
            CardAudioTimer -= Gdx.graphics.getDeltaTime();
        }
        if(HurtAudioTimer >0){
            HurtAudioTimer -= Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("images/scenes/blueBg.jpg");
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new SakikoCutscenePanel(characterPath("cutscene/ending1.png"),makeID("Sakiko-Cutscene-Ending1")));
        panels.add(new SakikoCutscenePanel(characterPath("cutscene/ending2.png"),makeID("Sakiko-Cutscene-Ending2")));
        panels.add(new SakikoCutscenePanel(characterPath("cutscene/ending3.png"),makeID("Sakiko-Cutscene-Ending2")));
        return panels;
    }

    public static void TryPlayCardAudio(String className){
        if( AbstractDungeon.player.chosenClass != TogawaSakiko.Meta.TOGAWA_SAKIKO){return;}
        if(CardAudioTimer <=0 ){
            CardAudioTimer = CARD_AUDIO_COOLDOWN;
            CardCrawlGame.sound.playA(makeID("Sakiko-"+className), 0);
        }
    }

    public static void TryPlayHurtAudio(String audioName){
        if( AbstractDungeon.player.chosenClass != TogawaSakiko.Meta.TOGAWA_SAKIKO){return;}
        if(HurtAudioTimer <=0 ){
            HurtAudioTimer = HURT_AUDIO_COOLDOWN;
            CardCrawlGame.sound.playA(makeID("Sakiko-"+audioName), 0);
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class PlayerDamagedSFX{
        @SpireInsertPatch(locator= PlayerDamagedLocator.class, localvars = {"damageAmount"})
        public static SpireReturn<Integer> CheckPlayerDamaged(AbstractPlayer __instance, int damageAmount)
        {
            if(damageAmount>0){
                int min = 1;
                int max = 3;
                Random random = new Random();
                int randomNumber = random.nextInt(max - min) + min;
                TogawaSakiko.TryPlayHurtAudio("Hurt"+getRandomNumber(1,3));
            }
            return SpireReturn.Continue();
        }

        public static class PlayerDamagedLocator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "damageReceivedThisTurn");
                return LineFinder.findInOrder(ctBehavior, (Matcher)fieldAccessMatcher);
            }
        }
    }


    private static int lastNumber = Integer.MIN_VALUE;
    private static final Random random = new Random();

    public static int getRandomNumber(int min, int max) {
        int randomNumber;
        do {
            randomNumber = random.nextInt(max - min) + min;
        } while (randomNumber == lastNumber);
        lastNumber = randomNumber;
        return randomNumber;
    }
}
