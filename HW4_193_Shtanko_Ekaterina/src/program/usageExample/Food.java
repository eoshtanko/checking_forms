package usageExample;

import constrained.Constrained;
import notBlank.NotBlank;
import notEmpty.NotEmpty;
import notNull.NotNull;
import positive.Positive;

@Constrained
public class Food {
    @NotBlank
    @NotNull
    @NotEmpty
    private String name;
    @Positive
    private int calories;

    public Food(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }
}
