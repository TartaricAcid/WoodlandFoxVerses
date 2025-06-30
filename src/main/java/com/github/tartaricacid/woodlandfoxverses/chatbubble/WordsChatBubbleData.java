package com.github.tartaricacid.woodlandfoxverses.chatbubble;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.client.chatbubble.WordsChatBubbleRenderer;
import com.github.tartaricacid.woodlandfoxverses.config.ModGeneralConfig;
import com.github.tartaricacid.woodlandfoxverses.resource.words.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WordsChatBubbleData implements IChatBubbleData {
    public static final ResourceLocation ID = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "words");
    public static final ResourceLocation FONT = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "libertinus_sans");
    public static final ResourceLocation FONT_BOLD = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "libertinus_sans_bold");
    public static final ResourceLocation FONT_ITALIC = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "libertinus_sans_italic");
    public static final ResourceLocation BG = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "textures/words.png");

    private final Component word;
    private final Component pronunciation;
    private final Component meanings;

    @OnlyIn(Dist.CLIENT)
    private IChatBubbleRenderer renderer;

    public WordsChatBubbleData(Component word, Component pronunciation, Component meanings) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.meanings = meanings;
    }

    public static WordsChatBubbleData create(Words words) {
        Component word = Component.literal(words.getWord()).setStyle(Style.EMPTY.withFont(FONT_BOLD));
        Component pronunciation = Component.literal(words.getPronunciation()).setStyle(Style.EMPTY.withFont(FONT).withColor(ChatFormatting.DARK_GRAY));
        Component meanings = Component.literal(String.join("\n", words.getMeanings())).setStyle(Style.EMPTY.withFont(FONT_ITALIC));
        return new WordsChatBubbleData(word, pronunciation, meanings);
    }

    @Override
    public int existTick() {
        return ModGeneralConfig.BUBBLE_SHOW_DURATION.get();
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public Component getWord() {
        return word;
    }

    public Component getPronunciation() {
        return pronunciation;
    }

    public Component getMeanings() {
        return meanings;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IChatBubbleRenderer getRenderer(IChatBubbleRenderer.Position position) {
        if (renderer == null) {
            renderer = new WordsChatBubbleRenderer(this, BG, position);
        }
        return renderer;
    }

    public static class WordsChatSerializer implements IChatBubbleData.ChatSerializer {
        @Override
        public IChatBubbleData readFromBuff(FriendlyByteBuf buf) {
            return new WordsChatBubbleData(buf.readComponent(), buf.readComponent(), buf.readComponent());
        }

        @Override
        public void writeToBuff(FriendlyByteBuf buf, IChatBubbleData data) {
            WordsChatBubbleData bubbleData = (WordsChatBubbleData) data;
            buf.writeComponent(bubbleData.word);
            buf.writeComponent(bubbleData.pronunciation);
            buf.writeComponent(bubbleData.meanings);
        }
    }
}
