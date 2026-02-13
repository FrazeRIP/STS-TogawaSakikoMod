package togawasakikomod.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.character.TogawaSakiko;

public class SpeicialVictoryScreen extends VictoryScreen {

    private final Texture mainTexture = new Texture(TogawaSakikoMod.imagePath("character/ending/true_ending.png"));;
    private Sprite mainSprite = null;

    private final Texture textTexture = new Texture(TogawaSakikoMod.imagePath("character/ending/true_ending_text.png"));;
    private Sprite textSprite = null;

    private static float fadeInDelay = 3.0f;
    private static float fadeInTime = 10.0f;
    private float fadeInTimer;


    public SpeicialVictoryScreen(MonsterGroup m) {
        super(m);
        mainSprite = new Sprite(mainTexture);
        mainSprite.setPosition(0,0);
        mainSprite.setSize((float) Settings.WIDTH, (float)Settings.HEIGHT);

        textSprite = new Sprite(textTexture);
        textSprite.setPosition(0,0);
        textSprite.setSize((float) Settings.WIDTH, (float)Settings.HEIGHT);
        textSprite.setColor(1f,1f,1f,0f);
        CardCrawlGame.fadeIn(1.5F);
        fadeInTimer = -fadeInDelay;

        if(AbstractDungeon.player instanceof TogawaSakiko){
            CardCrawlGame.music.playTempBgmInstantly("GMGU_Short");
        }
        TogawaSakikoMod.isGameClear = true;
    }


    public static void setFadeInDelay(float fadeInDelay) {
        SpeicialVictoryScreen.fadeInDelay = fadeInDelay;
    }

    @Override
    public void render(SpriteBatch sb) {
        if(AbstractDungeon.player instanceof TogawaSakiko){
            mainSprite.draw(sb);
            textSprite.draw(sb);
        }
        super.render(sb);
    }

    @Override
    public void update() {
        super.update();
        updateFadeIn();
    }

    private void updateFadeIn() {
        this.fadeInTimer += Gdx.graphics.getDeltaTime();
        if(fadeInTimer<0){
            textSprite.setColor(1,1,1,0f);
        }else if(fadeInTimer <=fadeInTime){
            textSprite.setColor(1f,1f,1f,fadeInTimer/fadeInTime);
        }else{
            textSprite.setColor(1,1,1,1);
        }
    }
}
