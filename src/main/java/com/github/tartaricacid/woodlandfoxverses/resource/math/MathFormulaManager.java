package com.github.tartaricacid.woodlandfoxverses.resource.math;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.List;

import static com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses.RANDOM;

public class MathFormulaManager {
    public static final String FOLDER_NAME = "math/";
    public static final List<MathFormula> FORMULAS = Lists.newArrayList();

    @Nullable
    public static MathFormula getRandomFormula() {
        if (FORMULAS.isEmpty()) {
            return null;
        }
        int formulaIndex = RANDOM.nextInt(FORMULAS.size());
        return FORMULAS.get(formulaIndex);
    }
}
