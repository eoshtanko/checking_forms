package analyzers;

import anyOf.AnyOf;
import constrained.Constrained;
import inRange.InRange;
import mainValidator.MainValidator;
import mainValidator.ValidationError;
import negative.Negative;
import notBlank.NotBlank;
import notEmpty.NotEmpty;
import notNull.NotNull;
import org.junit.jupiter.api.Test;
import positive.Positive;
import size.Size;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class MainValidatorTest {

    MainValidator mainValidator;

    /**
     * Метод, проверяющий случай:
     * Проверяемый класс помечен @Constrained.
     * В проверяемом классе есть поля помеченные @Constrained.
     * (ClassForTesting1, ClassForTesting2, ClassForTesting3, ClassForTesting4)
     */
    @Test
    void testCheckFieldWhoseClassIsConstrained() {
        ClassForTesting1 classForTesting1 = new ClassForTesting1();
        mainValidator = new MainValidator();
        Set<ValidationError> validationErrors = mainValidator.validate(classForTesting1);
        assertEquals(3, validationErrors.size());
    }

    /**
     * Метод, проверяющий случай, когда происходит попытка
     * валидировать класс, не помеченный @Constrained.
     */
    @Test
    void testConstrainedIsAbsent() {
        ClassForTesting4 classForTesting4 = new ClassForTesting4();
        mainValidator = new MainValidator();
        Set<ValidationError> validationErrors = mainValidator.validate(classForTesting4);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Метод, проверяющий случай, когда коллекция параметризуема классом, помеченным @Constrained.
     */
    @Test
    void testCheckListWhoseParameterClassIsConstrained() {
        /*
            ClassForTesting5 содержит:
            List<ClassForTesting3> testingList = Arrays.asList(new ClassForTesting3(), new ClassForTesting3());
         */
        ClassForTesting5 classForTesting5 = new ClassForTesting5();
        mainValidator = new MainValidator();
        Set<ValidationError> validationErrors = mainValidator.validate(classForTesting5);
        assertEquals(2, validationErrors.size());

        /*
            ClassForTesting6 содержит:
                List<List<List<List<List<ClassForTesting3>>>>> list
            = new ArrayList<>();
         */
        ClassForTesting6 classForTesting6 = new ClassForTesting6();
        mainValidator = new MainValidator();
        // в самом вложенном листе два элемента, в классах которых ошибка
        List<ClassForTesting3> list1 = Arrays.asList(new ClassForTesting3(), new ClassForTesting3());
        List<List<ClassForTesting3>> list2 = Collections.singletonList(list1);
        List<List<List<ClassForTesting3>>> list3 = Collections.singletonList(list2);
        List<List<List<List<ClassForTesting3>>>> list4 = Collections.singletonList(list3);
        classForTesting6.list = Collections.singletonList(list4);
        validationErrors = mainValidator.validate(classForTesting6);
        assertEquals(2, validationErrors.size());

        // Была спорная ситуация, проверять ли класс, помеченный Constrained, если
        // элемент этого класса null. Ассистент подсказал, что в этом нет необходимости.
        mainValidator = new MainValidator();
        // иначе заполняем самый вложенный лист:
        List<ClassForTesting3> lst1 = null;
        List<List<ClassForTesting3>> lst2 = Collections.singletonList(lst1);
        List<List<List<ClassForTesting3>>> lst3 = Collections.singletonList(lst2);
        List<List<List<List<ClassForTesting3>>>> lst4 = Collections.singletonList(lst3);
        classForTesting6.list = Collections.singletonList(lst4);
        validationErrors = mainValidator.validate(classForTesting6);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Метод, проверяющий случай, когда сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>).
     */
    @Test
    void testCheckListWhoseParameterIsConstrained() {
        /*
            ClassForTesting7 содержит:
            @Size(min = 2, max = 10) List<List<@NotEmpty List<@AnyOf({"a", "b"}) @Size(min = 2, max = 3) String>>> list1
            = new ArrayList<>();
         */
        ClassForTesting7 classForTesting7 = new ClassForTesting7();
        mainValidator = new MainValidator();
        List<String> lst1 = Arrays.asList("a", "b", "c");
        List<List<String>> lst2 = Arrays.asList(lst1, lst1, lst1);
        classForTesting7.list1 = Collections.singletonList(lst2);
        Set<ValidationError> validationErrors = mainValidator.validate(classForTesting7);
        assertEquals(13, validationErrors.size());

        /*
        Класс ClassForTesting8 содержит:
            @NotNull
            private List<@NotNull List<@NotNull String>> list = new ArrayList<>();
         */
        ClassForTesting8 classForTesting8 = new ClassForTesting8();
        mainValidator = new MainValidator();
        validationErrors = mainValidator.validate(classForTesting8);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Метод проверяющий случай, когда присутствуют оба способа применения аннотаций:
     * 1. когда коллекция параметризуема классом, помеченным @Constrained.
     * 2. когда сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>).
     */
    @Test
    void testMixedTypeLists() {
        Set<ValidationError> validationErrors;
        /*
            ClassForTesting9 содержит:
               List<List<List<List<@Size(min = 8, max = 9) List<ClassForTesting3>>>>> list
            = new ArrayList<>();
         */
        ClassForTesting9 classForTesting9 = new ClassForTesting9();
        mainValidator = new MainValidator();
        List<ClassForTesting3> list1 = Arrays.asList(new ClassForTesting3(), new ClassForTesting3());
        List<List<ClassForTesting3>> list2 = Collections.singletonList(list1);
        List<List<List<ClassForTesting3>>> list3 = Collections.singletonList(list2);
        List<List<List<List<ClassForTesting3>>>> list4 = Collections.singletonList(list3);
        classForTesting9.list = Collections.singletonList(list4);
        validationErrors = mainValidator.validate(classForTesting9);
        assertEquals(3, validationErrors.size());

        /*
            ClassForTesting10 содержит:
                List<List<List<List<@NotNull List<ClassForTesting3>>>>> list
            = new ArrayList<>();
         */
        ClassForTesting10 classForTesting10 = new ClassForTesting10();
        mainValidator = new MainValidator();
        List<ClassForTesting3> lst1 = null;
        List<List<ClassForTesting3>> lst2 = Collections.singletonList(lst1);
        List<List<List<ClassForTesting3>>> lst3 = Collections.singletonList(lst2);
        List<List<List<List<ClassForTesting3>>>> lst4 = Collections.singletonList(lst3);
        classForTesting10.list = Collections.singletonList(lst4);
        validationErrors = mainValidator.validate(classForTesting10);
        assertEquals(1, validationErrors.size());
    }

    /**
     * Метод, проверяющий случай, когда происходит попытка
     * валидировать объект класса с @Constrained равный null.
     */
    @Test
    void testNullParameter() {
        // Была спорная ситуация, проверять ли класс, помеченный Constrained, если
        // элемент этого класса null. Ассистент подсказал, что в этом нет необходимости.
        ClassForTesting3 classForTesting3 = null;
        mainValidator = new MainValidator();
        Set<ValidationError> validationErrors = mainValidator.validate(classForTesting3);
        assertEquals(0, validationErrors.size());
    }

    /**
     * Метод, проверяющий различные "сложные" случаи валидации.
     */
    @Test
    void testMixedCases() {
        ClassForTesting11 classForTesting11 = new ClassForTesting11();
        mainValidator = new MainValidator();
        Set<ValidationError> validationErrors = mainValidator.validate(classForTesting11);
        assertEquals(6, validationErrors.size());
    }
}

//

@Constrained
class ClassForTesting1 {
    ClassForTesting2 classForTesting2 = new ClassForTesting2();
    @Negative
    private int positiveInt = 1;
}

@Constrained
class ClassForTesting2 {
    ClassForTesting3 classForTesting3 = new ClassForTesting3();
    @Negative
    private int positiveInt = 2;
}

@Constrained
class ClassForTesting3 {
    ClassForTesting4 classForTesting4 = new ClassForTesting4();
    @Negative
    private int positiveInt = 3;
}

class ClassForTesting4 {
    @Negative
    int positiveInt = 4;
}

//
/*
 * Классы для проверки случая,  когда коллекция параметризуема классом, помеченным @Constrained.
 */

@Constrained
class ClassForTesting5 {
    List<ClassForTesting3> testingList = Arrays.asList(new ClassForTesting3(), new ClassForTesting3());
}

@Constrained
class ClassForTesting6 {
    List<List<List<List<List<ClassForTesting3>>>>> list
            = new ArrayList<>();
}

//
/*
 * Классы для проверки случая, когда сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>).
 */

@Constrained
class ClassForTesting7 {
    @Size(min = 2, max = 10) List<List<@NotEmpty List<@AnyOf({"a", "b"}) @Size(min = 2, max = 3) String>>> list1
            = new ArrayList<>();
}

/**
 * Класс для проверки случая, когда сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>).
 */
@Constrained
class ClassForTesting8 {
    @NotNull
    private List<@NotNull List<@NotNull String>> list = new ArrayList<>();
}

//
/*
 * Классы для проверки случая, когда присутствуют оба способа применения аннотаций:
 * 1. когда коллекция параметризуема классом, помеченным @Constrained.
 * 2. когда сам параметр коллекции помечен аннотациями (List<@NotNull GuestForm>).
 */

@Constrained
class ClassForTesting9 {
    List<List<List<List<@Size(min = 8, max = 9) List<ClassForTesting3>>>>> list
            = new ArrayList<>();
}


@Constrained
class ClassForTesting10 {
    List<List<List<List<@NotNull List<ClassForTesting3>>>>> list
            = new ArrayList<>();
}

//

@Constrained
class ClassForTesting11 {
    @NotNull
    @NotEmpty
    private final List<String> list = null;
    @NotNull
    @NotEmpty
    @NotBlank
    private final String str = null;
    @Positive
    @Negative
    private final Integer num = 0;
    @Positive
    @InRange(min = -5, max = -3)
    private final long numL = 0L;
}