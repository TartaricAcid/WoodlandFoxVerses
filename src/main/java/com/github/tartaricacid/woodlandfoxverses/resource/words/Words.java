package com.github.tartaricacid.woodlandfoxverses.resource.words;

import com.google.gson.annotations.SerializedName;

public class Words {
    @SerializedName("word")
    private String word;

    @SerializedName("pronunciation")
    private String pronunciation;

    @SerializedName("meanings")
    private String[] meanings;

    public String getWord() {
        return word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String[] getMeanings() {
        return meanings;
    }
}
