package com.github.tartaricacid.woodlandfoxverses;


import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleRegister;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.LaTexChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.VersesChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.WordsChatBubbleData;

@LittleMaidExtension
public class LittleMaidCompat implements ILittleMaid {
    @Override
    public void registerChatBubble(ChatBubbleRegister register) {
        register.register(VersesChatBubbleData.ID, new VersesChatBubbleData.VersesChatSerializer());
        register.register(WordsChatBubbleData.ID, new WordsChatBubbleData.WordsChatSerializer());
        register.register(LaTexChatBubbleData.ID, new LaTexChatBubbleData.LaTexChatSerializer());
    }
}
