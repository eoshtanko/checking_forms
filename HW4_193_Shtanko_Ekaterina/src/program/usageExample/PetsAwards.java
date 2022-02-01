package usageExample;

import constrained.Constrained;
import inRange.InRange;
import notBlank.NotBlank;
import notEmpty.NotEmpty;
import notNull.NotNull;
import size.Size;

import java.util.List;

@Constrained
public class PetsAwards {
    @InRange(min = 1, max = 5)
    private int degree;
    @NotEmpty
    @NotNull
    @Size(min = 0, max = 30)
    private List<@NotEmpty @NotBlank String> diplomas;

    public PetsAwards(int degree, List<String> diplomas) {
        this.degree = degree;
        this.diplomas = diplomas;
    }
}
