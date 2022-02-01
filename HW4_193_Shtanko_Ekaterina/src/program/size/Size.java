package size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Размер аннотированног о элемента должен находиться в диапазоне [min, max]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Size {
    /**
     * Начальная граница диапозона.
     */
    int min();
    /**
     * Конечная граница диапозона.
     */
    int max();
}