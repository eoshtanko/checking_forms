package usageExample;

import constrained.Constrained;
import inRange.InRange;
import notBlank.NotBlank;
import notEmpty.NotEmpty;
import notNull.NotNull;

import java.util.List;

@Constrained
public class PersonForm {
    @NotNull
    @NotBlank
    private final String firstName;
    @NotBlank
    @NotNull
    private final String lastName;
    @InRange(min = 0, max = 200)
    private int age;
    @NotEmpty
    private List<Pet> pets;

    public PersonForm(String firstName, String lastName, int age, List<Pet> pets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.pets = pets;
    }
}