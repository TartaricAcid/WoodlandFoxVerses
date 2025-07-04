package com.github.tartaricacid.woodlandfoxverses.config;

import com.electronwill.nightconfig.core.EnumGetMethod;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsType;
import net.minecraftforge.common.ForgeConfigSpec;

public class ModGeneralConfig {
    public static ForgeConfigSpec.BooleanValue GLOBAL_ENABLED;

    public static ForgeConfigSpec.IntValue BUBBLE_ADD_INTERVAL;
    public static ForgeConfigSpec.IntValue BUBBLE_SHOW_DURATION;

    public static ForgeConfigSpec.BooleanValue SHOW_MATH_FORMULA;
    public static ForgeConfigSpec.BooleanValue SHOW_POETRY;
    public static ForgeConfigSpec.BooleanValue SHOW_WORDS;

    public static ForgeConfigSpec.EnumValue<WordsType> WORDS_TYPE;

    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");

        builder.comment("Enable or disable all Woodland Fox Verses features globally");
        GLOBAL_ENABLED = builder.define("GlobalEnabled", true);

        builder.comment("The interval (in ticks) at which bubbles are added");
        BUBBLE_ADD_INTERVAL = builder.defineInRange("BubbleAddInterval", 320, 10, Integer.MAX_VALUE);

        builder.comment("The duration (in ticks) for which bubbles are shown");
        BUBBLE_SHOW_DURATION = builder.defineInRange("BubbleShowDuration", 300, 10, Integer.MAX_VALUE);

        builder.comment("Whether to show math formulas in bubbles");
        SHOW_MATH_FORMULA = builder.define("ShowMathFormula", true);

        builder.comment("Whether to show poetry in bubbles");
        SHOW_POETRY = builder.define("ShowPoetry", true);

        builder.comment("Whether to show words in bubbles");
        SHOW_WORDS = builder.define("ShowWords", true);

        builder.comment("The type of words to show in bubbles");
        WORDS_TYPE = builder.defineEnum("WordsType", WordsType.CET4, EnumGetMethod.NAME_IGNORECASE);

        builder.pop();

        return builder.build();
    }
}
