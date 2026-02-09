package togawasakikomod.events;

import basemod.CustomEventRoom;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import togawasakikomod.TogawaSakikoMod;
import togawasakikomod.dungeons.TheOblivion;
import togawasakikomod.helpers.DungeonHelper;

import java.util.ArrayList;

public class TheOblivionEvent  extends AbstractImageEvent {
    public static final String ID = TogawaSakikoMod.makeID(TheOblivionEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    //This text should be set up through loading an event localization json file
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private static final String IMAGE_PATH = "";

    public TheOblivionEvent() {
        super(NAME, DESCRIPTIONS[0], IMAGE_PATH);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (i){
            case 0:{
                //Jump to The Obvilion
                DungeonHelper.goToAct(TheOblivion.ID);
                break;
            }
            case 1:{
                //Nothng happens
                break;
            }
        }
    }

}
