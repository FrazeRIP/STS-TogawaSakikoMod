package togawasakikomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class FreeAttackPowerReducePatch {

    @SpirePatch(
            clz = FreeAttackPower.class,  // Replace with the actual class containing onUseCard
            method = "onUseCard"          // The target method
    )
    public static class ReplaceAmountPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    // Intercept the field modification for 'this.amount'
                    if (f.getFieldName().equals("amount") && f.isWriter()) {
                        f.replace("{ this.addToBot(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(this.owner, this.owner, this.ID, 1));  }");
                    }
                }
            };
        }
    }
}
