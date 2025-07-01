package com.github.tartaricacid.woodlandfoxverses.resource.poetry;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;

import static com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses.RANDOM;

public final class PoetryManager {
    public static final String FOLDER_NAME = "poetry/";
    public static final EnumMap<PoetryType, List<Poetry>> POETRIES = new EnumMap<>(PoetryType.class);

    @Nullable
    public static Poetry getRandomPoetry() {
        PoetryType[] types = PoetryType.values();
        if (types.length == 0) {
            return null;
        }
        int typeIndex = RANDOM.nextInt(types.length);
        PoetryType type = types[typeIndex];
        List<Poetry> poetryList = POETRIES.get(type);
        if (poetryList == null || poetryList.isEmpty()) {
            return null;
        }
        int poetryIndex = RANDOM.nextInt(poetryList.size());
        Poetry poetry = poetryList.get(poetryIndex);
        String[] paragraphs = poetry.getParagraphs();
        if (paragraphs.length > 6) {
            int maxStart = paragraphs.length - 6;
            int start = RANDOM.nextInt(maxStart + 1);
            String[] selected = new String[6];
            System.arraycopy(paragraphs, start, selected, 0, 6);
            return new Poetry(poetry.getAuthor(), selected, poetry.getTitle());
        }
        return poetry;
    }
}
