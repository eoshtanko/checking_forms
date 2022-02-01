package anyOf;

import mainValidator.MainValidationError;
import mainValidator.ValidationError;
import mainValidator.Validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, в котором происходит проверка поля, имеющего аннотацию @AnyOf.
 */
public class AnyOfValidator implements Validator {

    /**
     * Путь, по которому можно найти значение, не прошедшее проверку.
     */
    private final String path;
    /**
     * Значения, принадлежность к которым проверяется аннотацией.
     */
    private final String[] values;

    public AnyOfValidator(String path, String[] values) {
        this.path = path;
        this.values = values;
    }

    /**
     * Проверка поля, имеющего аннотацию @AnyOf.
     *
     * @param object объект для проверки
     * @return ошибки, выявленные при проверке
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        if (values != null) {
            if (object != null) {
                if (object instanceof String) {
                    String val = (String) object;
                    if (!Arrays.asList(values).contains(val)) {
                        StringBuilder messEr = new StringBuilder("Must be one of ");
                        for (String el : values) {
                            messEr.append("'").append(el).append("' ");
                        }
                        MainValidationError error = new MainValidationError(messEr.toString(), path, object);
                        validationErrorSet.add(error);
                    }
                } else {
                    System.out.println("Объект, помеченный аннотацией @AnyOf, не строка.");
                }
            }
        } else {
            System.out.println("Ошибка в аннотации. Массив значений, принадлежность к которым +\n" +
                    "проверяется аннотацией null");
        }
        return validationErrorSet;
    }
}
