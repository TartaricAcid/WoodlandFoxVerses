package com.github.tartaricacid.woodlandfoxverses.client.cloth;

import com.github.tartaricacid.touhoulittlemaid.api.event.client.AddClothConfigEvent;
import com.github.tartaricacid.woodlandfoxverses.config.ModGeneralConfig;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsType;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClothConfigScreen {
    @SubscribeEvent
    public void onAddClothConfigEvent(AddClothConfigEvent event) {
        addConfigScreen(event.getRoot(), event.getEntryBuilder());
    }

    public static void addConfigScreen(ConfigBuilder root, ConfigEntryBuilder entryBuilder) {
        ConfigCategory general = root.getOrCreateCategory(Component.translatable("config.woodlandfoxverses.general"));

        MutableComponent globalEnableName = Component.translatable("config.woodlandfoxverses.general.global_enable");
        MutableComponent globalEnableDesc = Component.translatable("config.woodlandfoxverses.general.global_enable.tooltip");
        general.addEntry(entryBuilder.startBooleanToggle(globalEnableName, ModGeneralConfig.GLOBAL_ENABLED.get())
                .setTooltip(globalEnableDesc)
                .setDefaultValue(ModGeneralConfig.GLOBAL_ENABLED.getDefault())
                .setSaveConsumer(ModGeneralConfig.GLOBAL_ENABLED::set).build());

        MutableComponent bubbleAddInterval = Component.translatable("config.woodlandfoxverses.general.bubble_add_interval");
        MutableComponent bubbleAddIntervalDesc = Component.translatable("config.woodlandfoxverses.general.bubble_add_interval.tooltip");
        general.addEntry(entryBuilder.startIntField(bubbleAddInterval, ModGeneralConfig.BUBBLE_ADD_INTERVAL.get())
                .setTooltip(bubbleAddIntervalDesc)
                .setDefaultValue(ModGeneralConfig.BUBBLE_ADD_INTERVAL.getDefault())
                .setMin(10)
                .setSaveConsumer(ModGeneralConfig.BUBBLE_ADD_INTERVAL::set).build());

        MutableComponent bubbleShowDuration = Component.translatable("config.woodlandfoxverses.general.bubble_show_duration");
        MutableComponent bubbleShowDurationDesc = Component.translatable("config.woodlandfoxverses.general.bubble_show_duration.tooltip");
        general.addEntry(entryBuilder.startIntField(bubbleShowDuration, ModGeneralConfig.BUBBLE_SHOW_DURATION.get())
                .setTooltip(bubbleShowDurationDesc)
                .setDefaultValue(ModGeneralConfig.BUBBLE_SHOW_DURATION.getDefault())
                .setMin(10)
                .setSaveConsumer(ModGeneralConfig.BUBBLE_SHOW_DURATION::set).build());

        MutableComponent showMathFormula = Component.translatable("config.woodlandfoxverses.general.show_math_formula");
        MutableComponent showMathFormulaDesc = Component.translatable("config.woodlandfoxverses.general.show_math_formula.tooltip");
        general.addEntry(entryBuilder.startBooleanToggle(showMathFormula, ModGeneralConfig.SHOW_MATH_FORMULA.get())
                .setTooltip(showMathFormulaDesc)
                .setDefaultValue(ModGeneralConfig.SHOW_MATH_FORMULA.getDefault())
                .setSaveConsumer(ModGeneralConfig.SHOW_MATH_FORMULA::set).build());

        MutableComponent showPoetry = Component.translatable("config.woodlandfoxverses.general.show_poetry");
        MutableComponent showPoetryDesc = Component.translatable("config.woodlandfoxverses.general.show_poetry.tooltip");
        general.addEntry(entryBuilder.startBooleanToggle(showPoetry, ModGeneralConfig.SHOW_POETRY.get())
                .setTooltip(showPoetryDesc)
                .setDefaultValue(ModGeneralConfig.SHOW_POETRY.getDefault())
                .setSaveConsumer(ModGeneralConfig.SHOW_POETRY::set).build());

        MutableComponent showWords = Component.translatable("config.woodlandfoxverses.general.show_words");
        MutableComponent showWordsDesc = Component.translatable("config.woodlandfoxverses.general.show_words.tooltip");
        general.addEntry(entryBuilder.startBooleanToggle(showWords, ModGeneralConfig.SHOW_WORDS.get())
                .setTooltip(showWordsDesc)
                .setDefaultValue(ModGeneralConfig.SHOW_WORDS.getDefault())
                .setSaveConsumer(ModGeneralConfig.SHOW_WORDS::set).build());

        MutableComponent wordsType = Component.translatable("config.woodlandfoxverses.general.words_type");
        MutableComponent wordsTypeDesc = Component.translatable("config.woodlandfoxverses.general.words_type.tooltip");
        general.addEntry(entryBuilder.startEnumSelector(wordsType, WordsType.class, ModGeneralConfig.WORDS_TYPE.get())
                .setTooltip(wordsTypeDesc)
                .setDefaultValue(ModGeneralConfig.WORDS_TYPE.getDefault())
                .setSaveConsumer(ModGeneralConfig.WORDS_TYPE::set).build());
    }
}
