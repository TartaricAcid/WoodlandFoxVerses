package com.github.tartaricacid.woodlandfoxverses.chatbubble;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.client.chatbubble.VersesChatBubbleRenderer;
import com.github.tartaricacid.woodlandfoxverses.resource.poetry.Poetry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("all")
public class VersesChatBubbleData implements IChatBubbleData {
    public static final ResourceLocation ID = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "verses");
    public static final ResourceLocation FONT = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "chunqiu");
    public static final ResourceLocation BG = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "textures/verses.png");
    public static final int EXIST_TICK = 25 * 30;

    private final Component title;
    private final Component author;
    private final Component paragraphs;

    @OnlyIn(Dist.CLIENT)
    private IChatBubbleRenderer renderer;

    public VersesChatBubbleData(Component title, Component author, Component paragraphs) {
        this.title = title;
        this.author = author;
        this.paragraphs = paragraphs;
    }

    public static VersesChatBubbleData create(Poetry poetry) {
        Component title = Component.literal(poetry.getTitle()).setStyle(Style.EMPTY.withFont(FONT));
        Component author = Component.literal(poetry.getAuthor()).setStyle(Style.EMPTY.withFont(FONT).withColor(ChatFormatting.DARK_RED));
        Component paragraphs = Component.literal(String.join("\n", poetry.getParagraphs())).setStyle(Style.EMPTY.withFont(FONT)
                .withColor(0x444444));
        return new VersesChatBubbleData(title, author, paragraphs);
    }

    @Override
    public int existTick() {
        return EXIST_TICK;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public Component getTitle() {
        return title;
    }

    public Component getAuthor() {
        return author;
    }

    public Component getParagraphs() {
        return paragraphs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IChatBubbleRenderer getRenderer(IChatBubbleRenderer.Position position) {
        if (renderer == null) {
            renderer = new VersesChatBubbleRenderer(this, BG, position);
        }
        return renderer;
    }

    public static class VersesChatSerializer implements IChatBubbleData.ChatSerializer {
        @Override
        public IChatBubbleData readFromBuff(FriendlyByteBuf buf) {
            return new VersesChatBubbleData(buf.readComponent(), buf.readComponent(), buf.readComponent());
        }

        @Override
        public void writeToBuff(FriendlyByteBuf buf, IChatBubbleData data) {
            VersesChatBubbleData bubbleData = (VersesChatBubbleData) data;
            buf.writeComponent(bubbleData.title);
            buf.writeComponent(bubbleData.author);
            buf.writeComponent(bubbleData.paragraphs);
        }
    }
}
