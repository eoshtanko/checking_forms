package positive;

import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PositiveValidatorTest {

    private final String path = "path";

    @Positive
    private final Integer correctValueInteger = 10;
    @Positive
    private final int correctValueInt = 10;
    @Positive
    private final Byte correctValueByte = 1;
    @Positive
    private final long correctValueLong = 100;
    @Positive
    private final Short correctValueShort = 1000;
    @Positive
    private final int wrongValueInt = 0;
    @Positive
    private final Long wrongValueLong = -100L;

    private final PositiveValidator positiveValidator = new PositiveValidator(path);
    private Set<ValidationError> validationErrors;

    /**
     * Тестирование работы метода валидации аннотации @Positive.
     * Если значение проходит валлидацию - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void positiveValidatorTestCorrectEmptyReturn() {

        validationErrors = positiveValidator.validate(correctValueInteger);

        assertEquals(0, validationErrors.size());

        validationErrors = positiveValidator.validate(correctValueInt);

        assertEquals(0, validationErrors.size());

        validationErrors = positiveValidator.validate(correctValueByte);

        assertEquals(0, validationErrors.size());

        validationErrors = positiveValidator.validate(correctValueShort);

        assertEquals(0, validationErrors.size());

        validationErrors = positiveValidator.validate(correctValueLong);

        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @Positive.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void positiveValidatorTestCorrectFindError() {

        String errorMes = "Must be positive ";

        validationErrors = positiveValidator.validate(wrongValueInt);

        assertEquals(1, validationErrors.size());
        assertEquals(wrongValueInt, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = positiveValidator.validate(wrongValueLong);

        assertEquals(1, validationErrors.size());
        assertEquals(wrongValueLong, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());
    }


    /**
     * Тестирование работы метода валидации аннотации @Positive.
     * Если значение null - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void positiveValidatorTestNullObject() {
        validationErrors = positiveValidator.validate(null);
        assertEquals(0, validationErrors.size());
    }
}