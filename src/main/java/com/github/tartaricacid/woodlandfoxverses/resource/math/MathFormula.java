package com.github.tartaricacid.woodlandfoxverses.resource.math;

import com.google.gson.annotations.SerializedName;

public class MathFormula {
    @SerializedName("formula")
    private String formula;

    @SerializedName("english")
    private String enName;

    @SerializedName("chinese")
    private String zhName;

    public String getFormula() {
        return formula;
    }

    public String getEnName() {
        return enName;
    }

    public String getZhName() {
        return zhName;
    }
}
