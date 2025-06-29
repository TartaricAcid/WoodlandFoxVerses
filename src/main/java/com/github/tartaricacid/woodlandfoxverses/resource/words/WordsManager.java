package com.github.tartaricacid.woodlandfoxverses.resource.words;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;

import static com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses.RANDOM;

public class WordsManager {
    public static final String FOLDER_NAME = "words/";
    public static final EnumMap<WordsType, List<Words>> WORDS = new EnumMap<>(WordsType.class);

    @Nullable
    public static Words getRandomWord(WordsType type) {
        List<Words> wordsList = WORDS.get(type);
        if (wordsList == null || wordsList.isEmpty()) {
            return null;
        }
        int randomIndex = RANDOM.nextInt(wordsList.size());
        return wordsList.get(randomIndex);
    }
}
