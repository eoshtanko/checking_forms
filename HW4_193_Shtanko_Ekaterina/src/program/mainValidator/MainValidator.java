package mainValidator;

import anyOf.*;
import constrained.*;
import inRange.*;
import negative.*;
import notBlank.*;
import notEmpty.*;
import notNull.*;
import positive.*;
import size.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Производит проверку произвольных объектов в соответствии с выставленными аннотациями.
 */
public class MainValidator implements Validator {

    /**
     * Позволяет получить путь до поля в случае вложенности
     */
    private String pathPart = "";

    /**
     * Производит проверку произвольных объектов в соответствии с выставленными аннотациями.
     *
     * @param object объект для проверки (вызов данного метода с объектом,
     *               тип которого не имеет аннотации @Constrained -
     *               ошибочная ситуация)
     * @return ошибки валидации
     */
    @Override
    public Set<ValidationError> validate(Object object) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        try {
            Class<?> objectClass = requireNonNull(object).getClass();
            if (objectClass.isAnnotationPresent(Constrained.class)) {
                for (Field field : objectClass.getDeclaredFields()) {
                    // Доступ к private полям осуществляется напрямую, с использованием setAccessible(true)
                    field.setAccessible(true);
                    // Случай, когда класс поля помечен @Constrained.
                    if (field.get(object) != null && field.get(object).getClass().isAnnotationPresent(Constrained.class)) {
                        validationErrorSet.addAll(checkFieldWhoseClassIsConstrained(field, object));
                    }
                    //  Если поле является коллекцией
                    if (field.get(object) != null && field.get(object) instanceof Collection) {
                        validationErrorSet.addAll(checkCollection(field, object));
                    }
                    validationErrorSet.addAll(checkAnnotations(field, object));
                }
            } else {
                System.out.println("Объект не имеет аннотации @Constrained.");
            }
        } catch (NullPointerException e) {
            System.out.println("Ошибка: Объект был null.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return validationErrorSet;
    }

    /**
     * Метод, рассматривающий случай, когда проверяемое поле - коллекция.
     * В нем рассматриваются два случая:
     * 1) Если коллекция параметризуема классом, помеченным @Constrained
     * 2) Если сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>)
     *
     * @param field  проверяемое поле
     * @param object объект, из которого извлекается значение поля
     * @return ошибки, выявленные при проверке
     * @throws IllegalAccessException если поле недоступно.
     */
    private Set<ValidationError> checkCollection(Field field, Object object) throws IllegalAccessException {

        Set<ValidationError> validationErrorSet = new HashSet<>();
        Collection collection = (Collection) field.get(object);

        // Если коллекция параметризуема классом, помеченным @Constrained
        // Была спорная ситуация, проверять ли класс, помеченный Constrained, если
        // элемент этого класса null. Ассистент подсказал, что в этом нет необходимости.
        validationErrorSet.addAll(checkListWhoseParameterClassIsConstrained(collection, field.getName()));

        // Если сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>)
        validationErrorSet.addAll(checkListWhoseParameterIsConstrained(collection, field.getName(),
                field.getAnnotatedType()));
        return validationErrorSet;
    }

    /**
     * Метод, рассматривающий случай, когда сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>).
     *
     * @param collection    проверяемая коллекция
     * @param path          параметр для конструирования пути до проверяемого объекта
     * @param annotatedType объект, представляющий тип поля поля, представленного этим Field
     * @return ошибки, выявленные при проверке
     */
    private Set<ValidationError> checkListWhoseParameterIsConstrained(Collection collection, String path,
                                                                      AnnotatedType annotatedType) {
        Set<ValidationError> validationErrorSet = new HashSet<>();
        // Если коллекция параметризуема классом, помеченным @Constrained
        AnnotatedType annoType = ((AnnotatedParameterizedType) annotatedType)
                .getAnnotatedActualTypeArguments()[0];
        var innerAnnotations = annoType.getAnnotations();
        String initialPath = pathPart;
        if (innerAnnotations.length != 0) {
            for (var annotation : innerAnnotations) {
                for (int i = 0; i < collection.size(); i++) {
                    pathPart += path + "[" + i + "]";
                    validationErrorSet.addAll(validateAnnotation(annotation, pathPart, collection.toArray()[i]));
                    if (collection.toArray()[i] != null && collection.toArray()[i] instanceof Collection) {
                        validationErrorSet.addAll(checkListWhoseParameterIsConstrained
                                ((Collection) collection.toArray()[i], "", annoType));
                    }
                    pathPart = initialPath;
                }
            }
        } else {
            for (int i = 0; i < collection.size(); i++) {
                pathPart += path + "[" + i + "]";
                if (collection.toArray()[i] != null && collection.toArray()[i] instanceof Collection) {
                    validationErrorSet.addAll(checkListWhoseParameterIsConstrained
                            ((Collection) collection.toArray()[i], "", annoType));
                } else if (collection.toArray()[i] == null) {
                    AnnotatedType innerAnnoType = ((AnnotatedParameterizedType) annoType)
                            .getAnnotatedActualTypeArguments()[0];
                    var secInnerAnnotations = innerAnnoType.getAnnotations();
                    if (secInnerAnnotations.length != 0) {
                        for (var annotation : secInnerAnnotations) {
                            validationErrorSet.addAll(validateAnnotation(annotation, pathPart, collection.toArray()[i]));
                        }
                    }
                }

                pathPart = initialPath;
            }
        }
        return validationErrorSet;
    }

    /**
     * Метод, рассматривающий случай, когда коллекция параметризуема классом, помеченным @Constrained.
     *
     * @param collection проверяемая коллекция
     * @param path       параметр для конструирования пути до проверяемого объекта
     * @return ошибки, выявленные при проверке
     */
    private Set<ValidationError> checkListWhoseParameterClassIsConstrained(Collection collection, String path) {
        // Была спорная ситуация, проверять ли класс, помеченный Constrained, если
        // элемент этого класса null. Ассистент подсказал, что в этом нет необходимости.
        Set<ValidationError> validationErrorSet = new HashSet<>();
        // Если коллекция параметризуема классом, помеченным @Constrained
        String initialPath = pathPart;
        if (collection.size() > 0 && collection.toArray()[0] != null && collection.toArray()[0].getClass().isAnnotationPresent(Constrained.class)) {
            for (int i = 0; i < collection.size(); i++) {
                pathPart += path + "[" + i + "].";
                validationErrorSet.addAll(validate(collection.toArray()[i]));
                pathPart = initialPath;
            }
        } else {
            for (int i = 0; i < collection.size(); i++) {
                pathPart += path + "[" + i + "]";
                if (collection.toArray()[i] != null && collection.toArray()[i] instanceof Collection) {
                    validationErrorSet.addAll(checkListWhoseParameterClassIsConstrained
                            ((Collection) collection.toArray()[i], ""));
                }
                pathPart = initialPath;
            }
        }
        return validationErrorSet;
    }

    /**
     * Метод, рассматривающий случай, когда класс поля помечен @Constrained.
     *
     * @param field  проверяемое поле
     * @param object объект, из которого извлекается значение поля
     * @return ошибки, выявленные при проверке
     * @throws IllegalAccessException если поле недоступно.
     */
    private Set<ValidationError> checkFieldWhoseClassIsConstrained(Field field, Object object)
            throws IllegalAccessException {
        Set<ValidationError> validationErrorSet;
        // если поля класса еще не были проверены
        String initialPath = pathPart;
        pathPart += field.getName() + ".";
        validationErrorSet = new HashSet<>(validate(field.get(object)));
        pathPart = initialPath;
        return validationErrorSet;
    }

    /**
     * Метод, проверяющий наличие у поля ряда аннотаций и в случае обнаружения аннотации
     * вызывающий метод-обработчик
     *
     * @param field  проверяемое поле
     * @param object объект, из которого извлекается значение поля
     * @return ошибки, выявленные при проверке
     * @throws IllegalAccessException если поле недоступно.
     */
    private Set<ValidationError> checkAnnotations(Field field, Object object) throws IllegalAccessException {
        AnnotatedType annotatedType = field.getAnnotatedType();
        Set<ValidationError> validationErrorSet = new HashSet<>();
        String path = pathPart + field.getName();
        Object obj = field.get(object);
        // Массив всех проверяемых аннотаций.
        Class[] annotations = {Positive.class, InRange.class, Negative.class, AnyOf.class,
                NotBlank.class, NotEmpty.class, NotNull.class, Size.class};
        // Проверка наличия аннотации
        for (var annotation : annotations) {
            if (annotatedType.isAnnotationPresent(annotation)) {
                validationErrorSet.addAll(validateAnnotation(annotatedType.getAnnotation(annotation), path, obj));
            }
        }
        return validationErrorSet;
    }

    /**
     * Метод вызывающий специфированный обработчик для определнной аннотации.
     *
     * @param annotation аннотация, которой помечено проверяемое поле
     * @param path       путь до проверяемого поля
     * @param value      значение проверяемого поля
     * @return ошибки, выявленные при проверке
     */
    private Set<ValidationError> validateAnnotation(Annotation annotation, String path, Object value) {

        Set<ValidationError> validationErrorSet = new HashSet<>();

        if (annotation.annotationType().equals(Positive.class)) {
            PositiveValidator positiveValidator = new PositiveValidator(path);
            validationErrorSet.addAll(positiveValidator.validate(value));
        }

        if (annotation.annotationType().equals(InRange.class)) {
            long min = ((InRange) annotation).min();
            long max = ((InRange) annotation).max();
            InRangeValidator inRangeValidator = new InRangeValidator(path, min, max);
            validationErrorSet.addAll(inRangeValidator.validate(value));
        }

        if (annotation.annotationType().equals(Negative.class)) {
            NegativeValidator negativeValidator = new NegativeValidator(path);
            validationErrorSet.addAll(negativeValidator.validate(value));
        }

        if (annotation.annotationType().equals(AnyOf.class)) {
            String[] values = ((AnyOf) annotation).value();
            AnyOfValidator anyOfValidator = new AnyOfValidator(path, values);
            validationErrorSet.addAll(anyOfValidator.validate(value));
        }

        if (annotation.annotationType().equals(NotBlank.class)) {
            NotBlancValidator notBlancValidator = new NotBlancValidator(path);
            validationErrorSet.addAll(notBlancValidator.validate(value));
        }

        if (annotation.annotationType().equals(NotEmpty.class)) {
            NotEmptyValidator notEmptyValidator = new NotEmptyValidator(path);
            validationErrorSet.addAll(notEmptyValidator.validate(value));
        }

        if (annotation.annotationType().equals(NotNull.class)) {
            NotNullValidator notNullValidator = new NotNullValidator(path);
            validationErrorSet.addAll(notNullValidator.validate(value));
        }

        if (annotation.annotationType().equals(Size.class)) {
            int min = ((Size) annotation).min();
            int max = ((Size) annotation).max();
            SizeValidator sizeValidator = new SizeValidator(path, min, max);
            validationErrorSet.addAll(sizeValidator.validate(value));
        }

        return validationErrorSet;
    }
}