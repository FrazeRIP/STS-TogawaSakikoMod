package togawasakikomod.saveable;

import basemod.abstracts.CustomSavable;

import java.lang.reflect.Type;

public class MasqueradeSaveable implements CustomSavable<Integer> {
    public static int RemoveCount = 0;
    @Override
    public Integer onSave() {
        System.out.println("---------------------Save:"+MasqueradeSaveable.class.getSimpleName()+":"+RemoveCount);
        return RemoveCount;
    }

    @Override
    public void onLoad(Integer removeCount) {
        RemoveCount = removeCount;
        System.out.println("---------------------Load:"+MasqueradeSaveable.class.getSimpleName()+":"+RemoveCount);
    }

    @Override
    public Type savedType() {
        return Integer.TYPE;
    }
}
