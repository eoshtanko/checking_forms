package usageExample;

import anyOf.AnyOf;
import constrained.Constrained;
import inRange.InRange;
import negative.Negative;
import notBlank.NotBlank;
import notEmpty.NotEmpty;
import notNull.NotNull;
import positive.Positive;
import size.Size;

import java.util.List;

@Constrained
public class Pet {
    @NotNull
    @NotEmpty
    @NotBlank
    private String petName;
    @InRange(min = 0, max = 30)
    private int petAge;
    @AnyOf({"s", "mid", "l"})
    private String petSizeCode;
    @Negative
    private int minComfortTemperature;
    @Positive
    private int maxComfortTemperature;
    @Size(min = 0, max = 100)
    private List<@NotNull PetsAwards> awards;
    @Size(min = 7, max = 7)
    private List<@NotEmpty List<@NotNull Food>> diet;


    public Pet(String petName, int petAge, String petSizeCode, int minComfortTemperature, int maxComfortTemperature,
               List<PetsAwards> awards, List<List<Food>> diet) {
        this.petName = petName;
        this.petAge = petAge;
        this.petSizeCode = petSizeCode;
        this.minComfortTemperature = minComfortTemperature;
        this.maxComfortTemperature = maxComfortTemperature;
        this.awards = awards;
        this.diet = diet;
    }
}

