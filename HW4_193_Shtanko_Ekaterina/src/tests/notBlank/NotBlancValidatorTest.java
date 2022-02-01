package notBlank;
import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotBlancValidatorTest {

    private final String path = "path";

    @NotBlank
    private final String correctVal = "abc";

    @NotBlank
    private final String wrongVal = "";

    private final NotBlancValidator notBlancValidator = new NotBlancValidator(path);
    private Set<ValidationError> validationErrors;

    /**
     * Тестирование работы метода валидации аннотации @NotBlanc.
     * Если значение проходит валлидацию - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void anyOfValidatorTestCorrectEmptyReturn() {
        validationErrors = notBlancValidator.validate(correctVal);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @NotBlanc.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void anyOfValidatorTestCorrectFindError() {
        String errorMes = "Must not be blank ";

        validationErrors = notBlancValidator.validate(wrongVal);

        assertEquals(1, validationErrors.size());
        assertEquals("\"\"", ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());
    }

    /**
     * Тестирование работы метода валидации аннотации @NotBlanc.
     * Если значение null - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void negativeValidatorTestNullObject() {
        validationErrors = notBlancValidator.validate(null);
        assertEquals(0, validationErrors.size());
    }
}