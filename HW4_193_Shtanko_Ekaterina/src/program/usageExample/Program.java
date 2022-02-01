package usageExample;

import mainValidator.ValidationError;
import mainValidator.MainValidator;
import mainValidator.Validator;

import java.util.List;
import java.util.Set;

public class Program {
    public static void main(String[] args) {
        Validator validator = new MainValidator();
        test(validator);
    }

    public static Set<ValidationError> test(Validator validator) {

        List<Food> foodForMon = List.of(
                new Food(""/*не должна быть пустой*/, 1000),
                new Food("sausages", 1000),
                new Food("sausages", -1/*должно быть положительно*/),
                new Food("sausages", 1000)
        );

        List<Food> foodForSun = List.of(
                new Food(null/*не должна быть null*/, 1000),
                new Food("sausages", 1000),
                new Food("sausages", 0/*должно быть положительно*/),
                new Food("sausages", 1000)
        );

        // элементов должно было быть 7!
        List<List<Food>> diet = List.of(
                foodForMon,
                foodForSun
        );

        List<PetsAwards> awards = List.of(
                new PetsAwards(0/*должно быть от 1 до 5*/, null/*не должно быть null*/)
        );
        Pet pet = new Pet(null/*не должно быть null*/, 101/*должно быть от 0 до 30*/,
                "i"/*должно быть из {"s", "mid", "l"}*/, 7/*должно быть отрицательно*/,
                30, awards, diet);

        List<Pet> pets = List.of(pet);

        PersonForm personForm = new PersonForm("Kar", ""/*не должна быть пустой*/,
                -1/*должно быть от 0 до 200*/, pets);

        Set<ValidationError> validationErrors = validator.validate(personForm);

        for (ValidationError e : validationErrors) {
            System.out.println(e.getPath() + " | " + e.getMessage() + "| " + e.getFailedValue());
        }
        return validationErrors;
    }
}