package com.github.tartaricacid.woodlandfoxverses.resource;

import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormula;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormulaManager;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.Poetry;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.PoetryManager;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.PoetryType;
import com.github.tartaricacid.woodlandfoxverses.resource.words.Words;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsManager;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ModDataPackListener extends SimpleJsonResourceReloadListener {
    private static final String FOLDER = "database";
    private static final Gson GSON = new Gson();

    public ModDataPackListener() {
        super(GSON, FOLDER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement value = entry.getValue();
            String path = key.getPath();
            if (!key.getNamespace().equals(WoodlandFoxVerses.MOD_ID)) {
                return;
            }

            // 加载诗歌
            if (path.startsWith(PoetryManager.FOLDER_NAME)) {
                String poetryName = path.substring(PoetryManager.FOLDER_NAME.length());
                try {
                    PoetryType type = PoetryType.valueOf(poetryName.toUpperCase(Locale.ENGLISH));
                    List<Poetry> poetryList = GSON.fromJson(value, new TypeToken<List<Poetry>>() {
                    }.getType());
                    PoetryManager.POETRIES.put(type, poetryList);
                } catch (IllegalArgumentException e) {
                    WoodlandFoxVerses.LOGGER.error("Invalid poetry type: {}", poetryName);
                }
            }

            // 加载单词表
            if (path.startsWith(WordsManager.FOLDER_NAME)) {
                String wordsName = path.substring(WordsManager.FOLDER_NAME.length());
                try {
                    WordsType type = WordsType.valueOf(wordsName.toUpperCase(Locale.ENGLISH));
                    List<Words> wordsList = GSON.fromJson(value, new TypeToken<List<Words>>() {
                    }.getType());
                    WordsManager.WORDS.put(type, wordsList);
                } catch (IllegalArgumentException e) {
                    WoodlandFoxVerses.LOGGER.error("Invalid words type: {}", wordsName);
                }
            }

            // 数学公式
            if (path.startsWith(MathFormulaManager.FOLDER_NAME)) {
                List<MathFormula> formulas = GSON.fromJson(value, new TypeToken<List<MathFormula>>() {
                }.getType());
                MathFormulaManager.FORMULAS.addAll(formulas);
            }
        }
    }
}
