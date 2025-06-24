package com.github.tartaricacid.woodlandfoxverses.poetry;

import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class PoetryManager {
    private static final List<Poetry> POETRIES = Lists.newArrayList();
    private static final Gson GSON = new Gson();

    public static void initialize() {
        readFile("tang_poetry");
        readFile("song_ci");
    }

    public static Poetry getRandomPoetry() {
        int index = (int) (Math.random() * POETRIES.size());
        Poetry poetry = POETRIES.get(index);
        if (poetry.getParagraphs().length > 6) {
            // 如果诗句过多，随机选取一部分
            int start = (int) (Math.random() * (poetry.getParagraphs().length - 6));
            String[] paragraphs = new String[6];
            System.arraycopy(poetry.getParagraphs(), start, paragraphs, 0, 6);
            return new Poetry(poetry.getAuthor(), paragraphs, poetry.getTitle());
        }
        return poetry;
    }

    private static void readFile(String fileName) {
        try (InputStream stream = readJarFile("/assets/woodlandfoxverses/poetry/%s.json".formatted(fileName))) {
            List<Poetry> poetries = GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), new TypeToken<>() {
            });
            POETRIES.addAll(poetries);
        } catch (IOException e) {
            WoodlandFoxVerses.LOGGER.error(e.getMessage());
        }
    }

    private static InputStream readJarFile(String filePath) throws IOException {
        URL url = WoodlandFoxVerses.class.getResource(filePath);
        if (url != null) {
            return url.openStream();
        }
        throw new IOException("File not found: " + filePath);
    }
}
