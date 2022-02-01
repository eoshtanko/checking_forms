package negative;

import mainValidator.MainValidationError;
import mainValidator.ValidationError;
import mainValidator.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, в котором происходит проверка поля, имеющего аннотацию @Negative.
 */
public class NegativeValidator implements Validator {

    /**
     * Путь, по которому можно найти значение, не прошедшее проверку.
     */
    private final String path;

    public NegativeValidator(String path){
        this.path = path;
    }

    /**
     * Проверка поля, имеющего аннотацию @Negative.
     * @param object объект для проверки
     * @return ошибки, выявленные при проверке
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        if(object != null) {
            if (object instanceof Number) {
                Number num = (Number) object;
                if (num.longValue() >= 0) {
                    String messEr = "Must be negative ";
                    MainValidationError error = new MainValidationError(messEr, path, object);
                    validationErrorSet.add(error);
                }
            } else {
                System.out.println("Объект, помеченный аннотацией @Negative, " +
                        "не число.");
            }
        }
        return validationErrorSet;
    }
}
// Типы, у которых аннотация может находиться: byte, short, int, long и их обёртки
// Моя проверка проверяет лишь принадлежность к Number(то есть числа с плав.запятой ее тоже пройдут)
// Но, так как данную проверку вовсе не обязательно было проводить, я решила
// закрыть на это глаза.