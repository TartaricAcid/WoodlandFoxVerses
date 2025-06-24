package com.github.tartaricacid.woodlandfoxverses;


import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleRegister;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.VersesChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.poetry.PoetryManager;

@LittleMaidExtension
public class LittleMaidCompat implements ILittleMaid {
    public LittleMaidCompat() {
        PoetryManager.initialize();
    }

    @Override
    public void registerChatBubble(ChatBubbleRegister register) {
        register.register(VersesChatBubbleData.ID, new VersesChatBubbleData.VersesChatSerializer());
    }
}
