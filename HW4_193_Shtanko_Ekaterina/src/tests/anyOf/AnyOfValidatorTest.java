package anyOf;

import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnyOfValidatorTest {

    private final String path = "path";

    @AnyOf({"a", "b", "c"})
    private final String correctString = "b";
    @AnyOf({"a", "b", "c"})
    private final String wrongString = "d";
    @AnyOf({"a", "b", "c"})
    private final String wrongEmptyString = "";
    @AnyOf({})
    private final String emptyAnnotation = "b";

    private final AnyOfValidator anyOfValidator = new AnyOfValidator(path, new String[]{"a", "b", "c"});
    private Set<ValidationError> validationErrors;

    /**
     * Тестирование работы метода валидации аннотации @AnyOf.
     * Если значение проходит валлидацию - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void anyOfValidatorTestCorrectEmptyReturn() {
        validationErrors = anyOfValidator.validate(correctString);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @AnyOf.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void anyOfValidatorTestCorrectFindError() {

        String errorMes = "Must be one of 'a' 'b' 'c' ";

        validationErrors = anyOfValidator.validate(wrongString);

        assertEquals(1, validationErrors.size());
        assertEquals(wrongString, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = anyOfValidator.validate(wrongEmptyString);

        assertEquals(1, validationErrors.size());
        assertEquals(wrongEmptyString, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());
    }

    /**
     * Тестирование работы метода валидации аннотации @AnyOf.
     * Если значение null - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void anyOfValidatorTestNullObject() {
        validationErrors = anyOfValidator.validate(null);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @AnyOf.
     * При условии, что список в аннотации пуст.
     */
    @Test
    void anyOfValidatorTestEmptyArray() {
        AnyOfValidator anyOfValidator = new AnyOfValidator(path, new String[]{});
        validationErrors = anyOfValidator.validate(emptyAnnotation);
        assertEquals(1, validationErrors.size());
    }
}