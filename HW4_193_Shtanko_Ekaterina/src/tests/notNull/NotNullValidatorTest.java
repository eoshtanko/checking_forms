package notNull;

import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotNullValidatorTest {

    private final String path = "path";

    @NotNull
    private final String correctVal = "";

    @NotNull
    private final String wrongVal = null;

    private final NotNullValidator notNullValidator = new NotNullValidator(path);
    private Set<ValidationError> validationErrors;

    /**
     * Тестирование работы метода валидации аннотации @NotNull.
     * Если значение проходит валлидацию - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void anyOfValidatorTestCorrectEmptyReturn() {
        validationErrors = notNullValidator.validate(correctVal);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @NotNull.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void anyOfValidatorTestCorrectFindError() {

        String errorMes = "Must not be null ";

        validationErrors = notNullValidator.validate(wrongVal);

        assertEquals(1, validationErrors.size());
        assertEquals(wrongVal, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());
    }
}