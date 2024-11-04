package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class CustomEnumPatch {
    @SpireEnum public static RewardItem.RewardType TOGAWASAKIKO_PURGEREWARD;

    @SpireEnum public static AbstractCard.CardTags TOGAWASAKIKO_UNREMOVEABLE;
    @SpireEnum public static AbstractCard.CardTags TOGAWASAKIKO_UTOPIA;
}
