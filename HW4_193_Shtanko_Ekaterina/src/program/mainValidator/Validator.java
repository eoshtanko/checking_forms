package mainValidator;

import java.util.Set;

/**
 * Интерфейс program.mainValidator.Validator имеет один единственный метод validate
 */
public interface Validator {
    /**
     * Производит проверку произвольных объектов в соответствии с выставленными аннотациями.
     *
     * @param object объект для проверки (вызов данного метода с объектом,
     *               тип которого не имеет аннотации @Constrained -
     *               ошибочная ситуация)
     * @return Set обнаруженных program.mainValidator.ValidationError'ов
     */
    Set<ValidationError> validate(Object object);
}