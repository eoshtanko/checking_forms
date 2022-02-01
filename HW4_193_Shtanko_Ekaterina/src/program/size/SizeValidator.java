package size;

import mainValidator.MainValidationError;
import mainValidator.ValidationError;
import mainValidator.Validator;

import java.util.*;

/**
 * Класс, в котором происходит проверка поля, имеющего аннотацию @Size.
 */
public class SizeValidator implements Validator {

    /**
     * Путь, по которому можно найти значение, не прошедшее проверку.
     */
    private final String path;
    /**
     * Начальная граница диапозона.
     */
    private final int min;
    /**
     * Конечная граница диапозона.
     */
    private final int max;

    public SizeValidator(String path, int min, int max) {
        this.path = path;
        this.min = min;
        this.max = max;
    }

    /**
     * Проверка поля, имеющего аннотацию @Size.
     *
     * @param object объект для проверки
     * @return ошибки, выявленные при проверке
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        if (min <= max) {
            if (object != null) {
                int sizeOfVal;
                if (object instanceof String) {
                    sizeOfVal = ((String) object).length();
                } else if (object instanceof List) {
                    sizeOfVal = ((List) object).size();
                } else if (object instanceof Set) {
                    sizeOfVal = ((Set) object).size();
                } else if (object instanceof Map) {
                    sizeOfVal = ((Map) object).size();
                    // И List и Set относятся к Collection. B казалось бы можно проверить
                    // их заодно (object instanceof Collection)
                    // Но! К Collection также относится Queue, о котором в условии
                    // ничего нет, так что проверка происходит по отдельности
                } else {
                    System.out.println("Объект, помеченный аннотацией @Size, " +
                            "не List<T>, Set<T>, Map<K, V>, String.");
                    return validationErrorSet;
                }
                if (sizeOfVal < min || sizeOfVal > max) {
                    String messEr = String.format("Size must be in range between %d and %d ", min, max);
                    MainValidationError error = new MainValidationError(messEr, path, object);
                    validationErrorSet.add(error);
                }
            }
        } else {
            System.out.println("Ошибка в аннотации. Минимальное значение превышает максимальное.");
        }
        return validationErrorSet;
    }
}
