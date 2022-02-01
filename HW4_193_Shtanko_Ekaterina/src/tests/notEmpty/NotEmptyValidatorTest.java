package notEmpty;

import mainValidator.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NotEmptyValidatorTest {

    private final String path = "path";

    @NotEmpty()
    private final String wrongTestString = "";

    @NotEmpty()
    private final String correctTestString = "String for testing";

    @NotEmpty()
    private final List<String> wrongTestList = new ArrayList<>();

    @NotEmpty()
    private final List<String> correctTestList = Collections.singletonList("a");

    @NotEmpty()
    private final Set<String> wrongTestSet = new HashSet<>();

    @NotEmpty()
    private final Set<String> correctTestSet = new HashSet<>();

    @NotEmpty()
    private final Map<String, String> wrongTestMap = new HashMap<>();

    @NotEmpty()
    private final Map<String, String> correctTestMap = new HashMap<>();

    private final NotEmptyValidator sizeValidator = new NotEmptyValidator(path);
    private Set<ValidationError> validationErrors;

    private void fullTestValues() {
        correctTestSet.addAll(Arrays.asList("a", "b"));
        correctTestMap.put("a", "b");
    }

    /**
     * Тестирование работы метода валидации аннотации @NotEmpty.
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
     * Тестирование работы метода валидации аннотации @NotEmpty.
     * Если значение не проходит валлидацию - метод
     * возвращает непустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestCorrectFindError() {

        String errorMes = "Must not be empty ";

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
     * Тестирование работы метода валидации аннотации @NotEmpty.
     * Если значение null - метод
     * возвращает пустой Set<ValidationError>
     */
    @Test
    void sizeValidatorTestNullObject() {
        validationErrors = sizeValidator.validate(null);
        assertEquals(0, validationErrors.size());
    }
}