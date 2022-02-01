package usageExample;

import mainValidator.MainValidator;
import mainValidator.ValidationError;
import mainValidator.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {
    @Test
    void testUsageExample() {
        Validator validator = new MainValidator();
        Set<ValidationError> validationErrors = Program.test(validator);
        assertEquals(14, validationErrors.size());
    }
}