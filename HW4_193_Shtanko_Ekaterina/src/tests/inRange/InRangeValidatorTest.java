package inRange;

import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InRangeValidatorTest {

    final private int minVal = 1, maxVal = 100;
    private final String path = "path";

    @InRange(min = minVal, max = maxVal)
    private final Integer correctIntegerVal = 50;

    @InRange(min = minVal, max = maxVal)
    private final int correctIntVal = 20;

    @InRange(min = minVal, max = maxVal)
    private final Long correctLongVal = 10L;

    @InRange(min = minVal, max = maxVal)
    private final byte correctByteVal = 100;

    @InRange(min = minVal, max = maxVal)
    private final Short correctShortVal = 1;

    @InRange(min = minVal, max = maxVal)
    private final Integer wrongIntegerVal = 0;

    @InRange(min = minVal, max = maxVal)
    private final Long wrongLongVal = 1000L;

    @InRange(min = minVal, max = maxVal)
    private final int wrongIntVal = -1;

    @InRange(min = 0, max = -2)
    private final int wrongAnnotationVal = -1;

    private final InRangeValidator inRangeValidator = new InRangeValidator(path, 1, 100);

    private Set<ValidationError> validationErrors;

    /**
     * Тестирование работы метода валидации аннотации @InRange.
     * Если значение проходит валлидацию - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestCorrectEmptyReturn() {

        validationErrors = inRangeValidator.validate(correctIntegerVal);
        assertEquals(0, validationErrors.size());

        validationErrors = inRangeValidator.validate(correctIntVal);
        assertEquals(0, validationErrors.size());

        validationErrors = inRangeValidator.validate(correctLongVal);
        assertEquals(0, validationErrors.size());

        validationErrors = inRangeValidator.validate(correctByteVal);
        assertEquals(0, validationErrors.size());

        validationErrors = inRangeValidator.validate(correctShortVal);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @InRange.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestCorrectFindError() {

        String errorMes = String.format("Must be in range between %d and %d ", minVal, maxVal);

        validationErrors = inRangeValidator.validate(wrongIntegerVal);
        assertEquals(1, validationErrors.size());
        assertEquals(wrongIntegerVal, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = inRangeValidator.validate(wrongLongVal);
        assertEquals(1, validationErrors.size());
        assertEquals(wrongLongVal, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = inRangeValidator.validate(wrongIntVal);
        assertEquals(1, validationErrors.size());
        assertEquals(wrongIntVal, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());
    }

    /**
     * Тестирование работы метода валидации аннотации @InRange.
     * Если значение null - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestNullObject() {
        validationErrors = inRangeValidator.validate(null);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @InRange.
     * Если минимальное значение аннотации превышает максимальное.
     */
    @Test
    void sizeValidatorTestWrongAnnotation() {
        InRangeValidator inRangeValidator = new InRangeValidator(path, 0, -2);
        validationErrors = inRangeValidator.validate(wrongAnnotationVal);
        assertEquals(0, validationErrors.size());
    }
}