package com.github.tartaricacid.woodlandfoxverses.poetry;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;

public class Poetry {
    @SerializedName("author")
    private String author = StringUtils.EMPTY;

    @SerializedName("paragraphs")
    private String[] paragraphs = new String[0];

    @SerializedName(value = "title", alternate = "rhythmic")
    private String title = StringUtils.EMPTY;

    public Poetry(String author, String[] paragraphs, String title) {
        this.author = author;
        this.paragraphs = paragraphs;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getParagraphs() {
        return paragraphs;
    }

    public String getTitle() {
        return title;
    }
}
