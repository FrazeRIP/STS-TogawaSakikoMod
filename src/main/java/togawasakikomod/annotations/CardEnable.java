package togawasakikomod.annotations;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CardEnable {
    boolean enable() default true;
}
