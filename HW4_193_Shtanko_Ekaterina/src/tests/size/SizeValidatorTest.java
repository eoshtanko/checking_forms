package size;

import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SizeValidatorTest {

    private final String path = "path";
    final private int minVal = 1, maxVal = 3;

    @Size(min = minVal, max = maxVal)
    private final String wrongTestString = "String for testing";

    @Size(min = minVal, max = maxVal)
    private final String correctTestString = "Str";

    @Size(min = minVal, max = maxVal)
    private final List<String> wrongTestList = Arrays.asList("a", "b", "c", "o");

    @Size(min = minVal, max = maxVal)
    private final List<String> correctTestList = Collections.singletonList("a");

    @Size(min = minVal, max = maxVal)
    private final Set<String> wrongTestSet = new HashSet<>();

    @Size(min = minVal, max = maxVal)
    private final Set<String> correctTestSet = new HashSet<>();

    @Size(min = minVal, max = maxVal)
    private final Map<String, String> wrongTestMap = new HashMap<>();

    @Size(min = minVal, max = maxVal)
    private final Map<String, String> correctTestMap = new HashMap<>();

    @Size(min = 10, max = 3)
    private final String wrongAnnotationTestString = "St";

    private final SizeValidator sizeValidator = new SizeValidator(path, 1, 3);
    private Set<ValidationError> validationErrors;

    private void fullTestValues() {
        correctTestSet.addAll(Arrays.asList("a", "b"));
        correctTestMap.put("a", "b");
    }

    /**
     * Тестирование работы метода валидации аннотации @Size.
     * Если значение проходит валлидацию - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestCorrectEmptyReturn() {

        fullTestValues();

        validationErrors = sizeValidator.validate(correctTestString);
        assertEquals(0, validationErrors.size());

        validationErrors = sizeValidator.validate(correctTestList);
        assertEquals(0, validationErrors.size());

        validationErrors = sizeValidator.validate(correctTestMap);
        assertEquals(0, validationErrors.size());

        validationErrors = sizeValidator.validate(correctTestSet);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @Size.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestCorrectFindError() {

        String errorMes = String.format("Size must be in range between %d and %d ", minVal, maxVal);

        validationErrors = sizeValidator.validate(wrongTestString);
        assertEquals(1, validationErrors.size());
        assertEquals(wrongTestString, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = sizeValidator.validate(wrongTestList);
        assertEquals(1, validationErrors.size());
        assertEquals(wrongTestList, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = sizeValidator.validate(wrongTestMap);
        assertEquals(1, validationErrors.size());
        assertEquals(wrongTestMap, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());

        validationErrors = sizeValidator.validate(wrongTestSet);
        assertEquals(1, validationErrors.size());
        assertEquals(wrongTestSet, ((ValidationError) validationErrors.toArray()[0]).getFailedValue());
        assertEquals(errorMes, ((ValidationError) validationErrors.toArray()[0]).getMessage());
        assertEquals(path, ((ValidationError) validationErrors.toArray()[0]).getPath());
    }

    /**
     * Тестирование работы метода валидации аннотации @Size.
     * Если значение null - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestNullObject() {
        validationErrors = sizeValidator.validate(null);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Тестирование работы метода валидации аннотации @Size.
     * Если минимальное значение аннотации превышает максимальное.
     */
    @Test
    void sizeValidatorTestWrongAnnotation() {
        SizeValidator sizeValidator = new SizeValidator(path, 10, 3);
        validationErrors = sizeValidator.validate(wrongAnnotationTestString);
        assertEquals(0, validationErrors.size());
    }
}