package com.github.tartaricacid.woodlandfoxverses.client.chatbubble;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.EntityGraphics;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.WordsChatBubbleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class WordsChatBubbleRenderer implements IChatBubbleRenderer {
    private static final int MAX_WIDTH = 240;
    private static final int MAX_CENTER_WIDTH = 480;

    private final Component word;
    private final Component pronunciation;
    private final List<FormattedCharSequence> split;
    private final Font font;
    private final int width;
    private final int height;
    private final ResourceLocation bg;
    private final Position position;

    public WordsChatBubbleRenderer(WordsChatBubbleData data, ResourceLocation bg, Position position) {
        this.font = Minecraft.getInstance().font;
        this.word = data.getWord();
        this.pronunciation = data.getPronunciation();
        this.position = position;
        if (position == Position.CENTER) {
            this.split = font.split(data.getMeanings(), MAX_CENTER_WIDTH);
        } else {
            this.split = font.split(data.getMeanings(), MAX_WIDTH);
        }
        int tmpWidth = getMaxWidth(split);
        tmpWidth = Math.max(tmpWidth, font.width(word));
        this.width = Math.max(tmpWidth, font.width(pronunciation));
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
        return this.height + 6;
    }

    @Override
    public int getWidth() {
        return this.width + 8;
    }

    @Override
    public void render(EntityMaidRenderer renderer, EntityGraphics graphics) {
        if (this.position == Position.LEFT) {
            graphics.blit(this.bg, 1, this.getHeight() + 3, 49, 7, 3, 5);
        } else {
            graphics.blit(this.bg, this.getWidth() - 3, this.getHeight() + 3, 49, 7, 3, 5);
        }
        graphics.blit(this.bg, this.getWidth() - 1, this.getHeight() / 2 - 3, 49, 0, 7, 6);

        int y = 2;
        graphics.drawString(font, this.word, 4, y, 0x000000, false);
        y += font.lineHeight + 4;
        graphics.drawString(font, this.pronunciation, 4, y, 0x000000, false);
        y += font.lineHeight + 4;
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
