package togawasakikomod.saveable;

import basemod.abstracts.CustomSavable;

import java.lang.reflect.Type;

public class KingsSaveable implements CustomSavable<Boolean> {
    public static boolean IsKing = false;
    @Override
    public Boolean onSave() {
        System.out.println("---------------------Save:"+KingsSaveable.class.getSimpleName()+":"+IsKing);
        return IsKing;
    }

    @Override
    public void onLoad(Boolean isKing) {
        IsKing = isKing;
        System.out.println("---------------------Load:"+KingsSaveable.class.getSimpleName()+":"+IsKing);
    }

    @Override
    public Type savedType() {
        return Boolean.TYPE;
    }
}
