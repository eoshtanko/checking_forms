package anyOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Значение находится в массиве, указанном в аннотации
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface AnyOf {
    /**
     * Значения, принадлежность к которым проверяется аннотацией.
     */
    String[] value();
}