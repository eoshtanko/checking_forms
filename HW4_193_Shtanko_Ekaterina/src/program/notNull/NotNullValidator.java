package notNull;

import mainValidator.MainValidationError;
import mainValidator.ValidationError;
import mainValidator.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, в котором происходит проверка поля, имеющего аннотацию @NotNull.
 */
public class NotNullValidator implements Validator {

    /**
     * Путь, по которому можно найти значение, не прошедшее проверку.
     */
    private final String path;

    public NotNullValidator(String path) {
        this.path = path;
    }

    /**
     * Проверка поля, имеющего аннотацию @NotNull.
     * @param object объект для проверки
     * @return ошибки, выявленные при проверке
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
            if (object == null) {
                String messEr = "Must not be null ";
                MainValidationError error = new MainValidationError(messEr, path, object);
                validationErrorSet.add(error);
            }
        return validationErrorSet;
    }
}
// Типы, у которых аннотация может находиться: любой reference-тип.
// Тк проверку на корректность типа проводить необязательно, здесь я
// не стала проверять на принадлежность к reference-типу