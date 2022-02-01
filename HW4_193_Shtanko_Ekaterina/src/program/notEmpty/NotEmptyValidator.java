package notEmpty;

import mainValidator.MainValidationError;
import mainValidator.ValidationError;
import mainValidator.Validator;

import java.util.*;

/**
 * Класс, в котором происходит проверка поля, имеющего аннотацию @NotEmpty.
 */
public class NotEmptyValidator implements Validator {

    /**
     * Путь, по которому можно найти значение, не прошедшее проверку.
     */
    private final String path;

    public NotEmptyValidator(String path) {
        this.path = path;
    }

    /**
     * Проверка поля, имеющего аннотацию @NotEmpty.
     * @param object объект для проверки
     * @return ошибки, выявленные при проверке
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        if(object != null) {
            boolean isEmpty = false;
            if (object instanceof String) {
                if (((String) object).isEmpty())
                    isEmpty = true;
            } else if (object instanceof Set) {
                if (((Set) object).isEmpty())
                    isEmpty = true;
            } else if (object instanceof List) {
                if (((List) object).isEmpty())
                    isEmpty = true;
            } else if (object instanceof Map) {
                if (((Map) object).isEmpty())
                    isEmpty = true;
                // И List и Set относятся к Collection. B казалось бы можно проверить
                // их заодно (object instanceof Collection)
                // Но! К Collection также относится Queue, о котором в условии
                // ничего нет, так что проверка происходит по отдельности
            } else {
                System.out.println("Объект, помеченный аннотацией @NotEmpty, " +
                        "не List<T>, Set<T>, Map<K, V>, String.");
                return validationErrorSet;
            }
            if (isEmpty) {
                String messEr = "Must not be empty ";
                MainValidationError error = new MainValidationError(messEr, path, object);
                validationErrorSet.add(error);
            }
        }
        return validationErrorSet;
    }
}
