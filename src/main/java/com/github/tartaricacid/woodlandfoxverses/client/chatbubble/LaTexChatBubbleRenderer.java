package com.github.tartaricacid.woodlandfoxverses.client.chatbubble;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.EntityGraphics;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.chatbubble.LaTexChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.client.renderer.texture.LaTexTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.util.List;

public class LaTexChatBubbleRenderer implements IChatBubbleRenderer {
    private static final int MIN_WIDTH = 180;

    private final ResourceLocation formulaTexture;
    private final List<FormattedCharSequence> enName;
    private final List<FormattedCharSequence> zhName;
    private final Font font;
    private final Position position;
    private final ResourceLocation bg;

    private int width;
    private int height;
    private int formulaWidth;
    private int formulaHeight;

    public LaTexChatBubbleRenderer(LaTexChatBubbleData data, ResourceLocation bg, Position position) {
        this.font = Minecraft.getInstance().font;
        this.formulaTexture = getOrRegisterFormulaTexture(data.getFormula());
        this.position = position;
        this.bg = bg;

        this.formulaHeight = this.formulaHeight / 4;
        this.formulaWidth = this.formulaWidth / 4;

        // 考虑文本长度，拓宽高度
        this.width = Math.max(this.formulaWidth, MIN_WIDTH);
        this.enName = this.font.split(data.getEnName(), this.width);
        this.zhName = this.font.split(data.getZhName(), this.width);
        this.height = this.formulaHeight + (this.enName.size() + this.zhName.size()) * this.font.lineHeight;
    }

    private ResourceLocation getOrRegisterFormulaTexture(String formula) {
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        ResourceLocation id = new ResourceLocation("woodlandfoxverses", "builtin/formulas/" + formula.hashCode());
        if (!manager.byPath.containsKey(id)) {
            try (LaTexTexture texture = new LaTexTexture(new TeXFormula(formula))) {
                manager.register(id, texture);
                this.formulaWidth = texture.getWidth();
                this.formulaHeight = texture.getHeight();
            } catch (Exception e) {
                WoodlandFoxVerses.LOGGER.error("Failed to register formula texture", e);
            }
        } else if (manager.byPath.get(id) instanceof LaTexTexture texture) {
            this.formulaWidth = texture.getWidth();
            this.formulaHeight = texture.getHeight();
        }
        return id;
    }

    @Override
    public int getHeight() {
        return this.height + 10;
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
        graphics.blit(this.formulaTexture, (this.getWidth() - this.formulaWidth) / 2,
                y, 0, 0, this.formulaWidth, this.formulaHeight, this.formulaWidth, this.formulaHeight);

        y += this.formulaHeight + 4;
        for (FormattedCharSequence sequence : this.enName) {
            int x = (this.getWidth() - font.width(sequence)) / 2;
            graphics.drawString(font, sequence, x, y, 0x000000, false);
            y += font.lineHeight;
        }

        y += 4;
        for (FormattedCharSequence sequence : this.zhName) {
            int x = (this.getWidth() - font.width(sequence)) / 2;
            graphics.drawString(font, sequence, x, y, 0x000000, false);
            y += font.lineHeight;
        }
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return this.bg;
    }
}
