package togawasakikomod.rooms;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowRoom;
import togawasakikomod.events.TheOblivionEvent;

public class TheOblivionEventRoom  extends NeowRoom {
    public TheOblivionEventRoom() {
        super(true);
    }

    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new TheOblivionEvent();
        this.event.onEnterRoom();
    }
}

