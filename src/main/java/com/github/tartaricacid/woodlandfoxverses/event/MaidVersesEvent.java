package com.github.tartaricacid.woodlandfoxverses.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTickEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.LaTexChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.VersesChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.WordsChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormula;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormulaManager;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.Poetry;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.PoetryManager;
import com.github.tartaricacid.woodlandfoxverses.resource.words.Words;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsManager;
import com.github.tartaricacid.woodlandfoxverses.resource.words.WordsType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MaidVersesEvent {
    @SubscribeEvent
    public static void onMaidTick(MaidTickEvent event) {
        EntityMaid maid = event.getMaid();
        long randomTime = maid.tickCount + maid.getUUID().getLeastSignificantBits();
        int checkTime = 50 + 20;
        if (!maid.level().isClientSide && randomTime % checkTime == 0) {
            //showPoetry(maid);
            //showWords(maid);
            showMathFormula(maid);
        }
    }

    private static void showPoetry(EntityMaid maid) {
        Poetry randomPoetry = PoetryManager.getRandomPoetry();
        if (randomPoetry == null) {
            // 没有可用的诗歌
            return;
        }
        VersesChatBubbleData data = VersesChatBubbleData.create(randomPoetry);
        maid.getChatBubbleManager().addChatBubble(data);
    }

    private static void showWords(EntityMaid maid) {
        Words randomWord = WordsManager.getRandomWord(WordsType.CET4);
        if (randomWord == null) {
            // 没有可用的单词
            return;
        }
        WordsChatBubbleData data = WordsChatBubbleData.create(randomWord);
        maid.getChatBubbleManager().addChatBubble(data);
    }

    private static void showMathFormula(EntityMaid maid) {
        MathFormula randomFormula = MathFormulaManager.getRandomFormula();
        if (randomFormula == null) {
            // 没有可用的数学公式
            return;
        }
        LaTexChatBubbleData data = LaTexChatBubbleData.create(randomFormula);
        maid.getChatBubbleManager().addChatBubble(data);
    }
}
