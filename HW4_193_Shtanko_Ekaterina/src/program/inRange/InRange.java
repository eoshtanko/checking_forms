package inRange;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Значение лежит в диапазоне [min; max]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface InRange {
    /**
     * Начальная граница диапозона.
     */
    long min();
    /**
     * Конечная граница диапозона.
     */
    long max();
}
