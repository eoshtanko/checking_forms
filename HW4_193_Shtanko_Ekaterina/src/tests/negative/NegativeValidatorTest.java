package negative;

import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NegativeValidatorTest {

    private final String path = "path";

    @Negative
    private final Integer correctValueInteger = -10;
    @Negative
    private final int correctValueInt = -10;
    @Negative
    private final Byte correctValueByte = -1;
    @Negative
    private final long correctValueLong = -100;
    @Negative
    private final Short correctValueShort = -1000;
    @Negative
    private final int wrongValueInt = 0;
    @Negative
    private final Long wrongValueLong = 100L;

    private final NegativeValidator negativeValidator = new NegativeValidator(path);
    private Set<ValidationError> validationErrors;

    /**
     * Тестирование работы метода валидации аннотации @Negative.
     * Если значение проходит валлидацию - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void negativeValidatorTestCorrectEmptyReturn() {

        validationErrors = negativeValidator.validate(correctValueInteger);

        assertEquals(0, validationErrors.size());

        validationErrors = negativeValidator.validate(correctValueInt);

        assertEquals(0, validationErrors.size());

        validationErrors = negativeValidator.validate(correctValueByte);

        assertEquals(0, validationErrors.size());

        validationErrors = negativeValidator.validate(correctValueShort);

        assertEquals(0, validationErrors.size());

        validationErrors = negativeValidator.validate(correctValueLong);

        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @Negative.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void negativeValidatorTestCorrectFindError() {
        String errorMes = "Must be negative ";

        validationErrors = negativeValidator.validate(wrongValueInt);

        assertEquals(1, validationErrors.size());
        assertEquals(wrongValueInt, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = negativeValidator.validate(wrongValueLong);

        assertEquals(1, validationErrors.size());
        assertEquals(wrongValueLong, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());
    }


    /**
     * Тестирование работы метода валидации аннотации @Negative.
     * Если значение null - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void negativeValidatorTestNullObject() {
        validationErrors = negativeValidator.validate(null);
        assertEquals(0, validationErrors.size());
    }

}