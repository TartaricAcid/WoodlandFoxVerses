package com.github.tartaricacid.woodlandfoxverses.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTickEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.LaTexChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.VersesChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.WordsChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.config.ModGeneralConfig;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormula;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormulaManager;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.Poetry;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.PoetryManager;
import com.github.tartaricacid.woodlandfoxverses.resource.words.Words;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsManager;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsType;
import com.google.common.collect.Lists;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.List;

@EventBusSubscriber
public class MaidVersesEvent {
    @SubscribeEvent
    public static void onMaidTick(MaidTickEvent event) {
        if (event.getMaid().level().isClientSide) {
            // 只在服务器端处理
            return;
        }
        if (!ModGeneralConfig.GLOBAL_ENABLED.get()) {
            return;
        }

        EntityMaid maid = event.getMaid();
        long randomTime = maid.tickCount + maid.getUUID().getLeastSignificantBits();
        int bubbleAddInterval = ModGeneralConfig.BUBBLE_ADD_INTERVAL.get();

        if (randomTime % bubbleAddInterval == 0) {
            List<Runnable> actions = Lists.newArrayList();

            // 根据配置决定是否显示不同类型的内容
            if (ModGeneralConfig.SHOW_POETRY.get()) {
                actions.add(() -> showPoetry(maid));
            }
            if (ModGeneralConfig.SHOW_WORDS.get()) {
                actions.add(() -> showWords(maid));
            }
            if (ModGeneralConfig.SHOW_MATH_FORMULA.get()) {
                actions.add(() -> showMathFormula(maid));
            }

            // 随机选择一个动作执行
            if (!actions.isEmpty()) {
                int randomIndex = WoodlandFoxVerses.RANDOM.nextInt(actions.size());
                actions.get(randomIndex).run();
            }
        }
    }

    private static void showPoetry(EntityMaid maid) {
        Poetry randomPoetry = PoetryManager.getRandomPoetry();
        if (randomPoetry == null) {
            return;
        }
        VersesChatBubbleData data = VersesChatBubbleData.create(randomPoetry);
        maid.getChatBubbleManager().addChatBubble(data);
    }

    private static void showWords(EntityMaid maid) {
        WordsType wordsType = ModGeneralConfig.WORDS_TYPE.get();
        if (wordsType == null) {
            // 默认使用 CET4
            wordsType = WordsType.CET4;
        }
        Words randomWord = WordsManager.getRandomWord(wordsType);
        if (randomWord == null) {
            return;
        }
        WordsChatBubbleData data = WordsChatBubbleData.create(randomWord);
        maid.getChatBubbleManager().addChatBubble(data);
    }

    private static void showMathFormula(EntityMaid maid) {
        MathFormula randomFormula = MathFormulaManager.getRandomFormula();
        if (randomFormula == null) {
            return;
        }
        LaTexChatBubbleData data = LaTexChatBubbleData.create(randomFormula);
        maid.getChatBubbleManager().addChatBubble(data);
    }
}
