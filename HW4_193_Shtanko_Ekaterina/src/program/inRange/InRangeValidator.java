package inRange;

import mainValidator.MainValidationError;
import mainValidator.ValidationError;
import mainValidator.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, в котором происходит проверка поля, имеющего аннотацию @InRange.
 */
public class InRangeValidator implements Validator {

    /**
     * Путь, по которому можно найти значение, не прошедшее проверку.
     */
    private final String path;
    /**
     * Начальная граница диапозона.
     */
    private final long min;
    /**
     * Конечная граница диапозона.
     */
    private final long max;

    public InRangeValidator(String path, long min, long max) {
        this.path = path;
        this.min = min;
        this.max = max;
    }

    /**
     * Проверка поля, имеющего аннотацию @InRange.
     *
     * @param object объект для проверки
     * @return ошибки, выявленные при проверке
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        if (min <= max) {
            if (object != null) {
                if (object instanceof Number) {
                    Number num = (Number) object;
                    if (num.longValue() < min || num.longValue() > max) {
                        String messEr = String.format("Must be in range between %d and %d ", min, max);
                        MainValidationError error = new MainValidationError(messEr, path, object);
                        validationErrorSet.add(error);
                    }
                } else {
                    System.out.println("Объект, помеченный аннотацией @InRange, " +
                            "не число.");
                }
            }
        } else {
            System.out.println("Ошибка в аннотации. Минимальное значение превышает максимальное.");
        }
        return validationErrorSet;
    }
}
// Типы, у которых аннотация может находиться: byte, short, int, long и их обёртки
// Моя проверка проверяет лишь принадлежность к Number(то есть числа с плав.запятой ее тоже пройдут)
// Но, так как данную проверку вовсе не обязательно было проводить, я решила
// закрыть на это глаза.