package com.github.tartaricacid.woodlandfoxverses.client.chatbubble;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.EntityGraphics;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.VersesChatBubbleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class VersesChatBubbleRenderer implements IChatBubbleRenderer {
    private static final int MAX_WIDTH = 240;
    private static final int MAX_CENTER_WIDTH = 480;

    private final Component title;
    private final Component author;
    private final List<FormattedCharSequence> split;
    private final Font font;
    private final int width;
    private final int height;
    private final ResourceLocation bg;

    public VersesChatBubbleRenderer(VersesChatBubbleData data, ResourceLocation bg, IChatBubbleRenderer.Position position) {
        this.font = Minecraft.getInstance().font;
        this.title = data.getTitle();
        this.author = data.getAuthor();
        if (position == Position.CENTER) {
            this.split = font.split(data.getParagraphs(), MAX_CENTER_WIDTH);
        } else {
            this.split = font.split(data.getParagraphs(), MAX_WIDTH);
        }
        int tmpWidth = getMaxWidth(split);
        tmpWidth = Math.max(tmpWidth, font.width(title));
        this.width = Math.max(tmpWidth, font.width(author));
        this.height = (split.size() + 2) * (font.lineHeight + 2);
        this.bg = bg;
    }

    private int getMaxWidth(List<FormattedCharSequence> split) {
        int width = 0;
        for (FormattedCharSequence sequence : split) {
            int lineWidth = font.width(sequence);
            if (lineWidth > width) {
                width = lineWidth;
            }
        }
        return width;
    }

    @Override
    public int getHeight() {
        return this.height + 8;
    }

    @Override
    public int getWidth() {
        return this.width + 8;
    }

    @Override
    public void render(EntityMaidRenderer renderer, EntityGraphics graphics) {
        int y = 2;
        graphics.drawString(font, this.title, 4, y, 0x000000, false);
        y += font.lineHeight + 2;
        graphics.drawString(font, this.author, 4, y, 0x000000, false);
        y += font.lineHeight + 6;
        for (FormattedCharSequence sequence : this.split) {
            graphics.drawString(font, sequence, 4, y, 0x000000, false);
            y += font.lineHeight + 2;
        }
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return this.bg;
    }
}
