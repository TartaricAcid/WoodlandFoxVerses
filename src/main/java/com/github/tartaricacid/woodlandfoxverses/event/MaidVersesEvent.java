package com.github.tartaricacid.woodlandfoxverses.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidTickEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.VersesChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.poetry.Poetry;
import com.github.tartaricacid.woodlandfoxverses.poetry.PoetryManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MaidVersesEvent {
    @SubscribeEvent
    public static void onMaidTick(MaidTickEvent event) {
        EntityMaid maid = event.getMaid();
        long randomTime = maid.tickCount + maid.getUUID().getLeastSignificantBits();
        int checkTime = VersesChatBubbleData.EXIST_TICK + 20;
        if (!maid.level().isClientSide && randomTime % checkTime == 0) {
            Poetry randomPoetry = PoetryManager.getRandomPoetry();
            VersesChatBubbleData data = VersesChatBubbleData.create(randomPoetry);
            maid.getChatBubbleManager().addChatBubble(data);
        }
    }
}
