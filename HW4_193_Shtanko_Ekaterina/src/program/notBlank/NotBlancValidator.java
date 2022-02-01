package notBlank;

import mainValidator.MainValidationError;
import mainValidator.ValidationError;
import mainValidator.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, в котором происходит проверка поля, имеющего аннотацию @NotBlanc.
 */
public class NotBlancValidator implements Validator {

    /**
     * Путь, по которому можно найти значение, не прошедшее проверку.
     */
    private final String path;

    public NotBlancValidator(String path){
        this.path = path;
    }

    /**
     * Проверка поля, имеющего аннотацию @NotBlanc.
     * @param object объект для проверки
     * @return ошибки, выявленные при проверке
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        if(object != null) {
            if (object instanceof String) {
                String val = (String) object;
                if (val.isBlank()) {
                    String messEr = "Must not be blank ";
                    // Был вопрос о выводе значения, не прошедшего проверку NotBlanc.
                    // Выводить сам объект, не прошедший проверку(то есть пустоту)
                    // или как в примере задания("")?
                    // Ассистент подсказал, что второй вариант лучше.
                    MainValidationError error = new MainValidationError(messEr, path, "\"\"");
                    validationErrorSet.add(error);
                }
            } else {
                System.out.println("Объект, помеченный аннотацией @NotBlanc, не строка.");
            }
        }
        return validationErrorSet;
    }
}
