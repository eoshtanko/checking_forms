package mainValidator;

// У меня были сомнения, делать ли мне отдельный класс Error для каждой аннотации
// или же сделать один общий.
// Я приняла решения сделать один класс, так как логика в реализации между
// ошибками разных аннотаций различна разве что в тексте сообщений.
public class MainValidationError implements ValidationError {
    private final String message;
    private final String path;
    private final Object failedValue;

    public MainValidationError(String message, String path, Object failedValue) {
        this.message = message;
        this.path = path;
        this.failedValue = failedValue;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getFailedValue() {
        return failedValue;
    }
}
