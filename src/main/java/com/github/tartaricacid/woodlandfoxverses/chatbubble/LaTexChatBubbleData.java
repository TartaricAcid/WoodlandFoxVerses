package com.github.tartaricacid.woodlandfoxverses.chatbubble;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.client.chatbubble.LaTexChatBubbleRenderer;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormula;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LaTexChatBubbleData implements IChatBubbleData {
    public static final ResourceLocation ID = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "latex");
    public static final ResourceLocation EN_FONT = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "libertinus_sans");
    public static final ResourceLocation ZH_FONT = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "chunqiu");
    public static final ResourceLocation BG = new ResourceLocation(WoodlandFoxVerses.MOD_ID, "textures/words.png");
    public static final int EXIST_TICK = 15 * 30;

    private final String formula;
    private final Component enName;
    private final Component zhName;

    @OnlyIn(Dist.CLIENT)
    private IChatBubbleRenderer renderer;

    public LaTexChatBubbleData(String formula, Component enName, Component zhName) {
        this.formula = formula;
        this.enName = enName;
        this.zhName = zhName;
    }

    public static LaTexChatBubbleData create(MathFormula formula) {
        return new LaTexChatBubbleData(formula.getFormula(),
                Component.literal(formula.getEnName()).setStyle(Style.EMPTY.withFont(EN_FONT).withColor(ChatFormatting.DARK_GRAY)),
                Component.literal(formula.getZhName()).setStyle(Style.EMPTY.withFont(ZH_FONT).withColor(ChatFormatting.DARK_GRAY)));
    }

    @Override
    public int existTick() {
        return EXIST_TICK;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public String getFormula() {
        return formula;
    }

    public Component getEnName() {
        return enName;
    }

    public Component getZhName() {
        return zhName;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IChatBubbleRenderer getRenderer(IChatBubbleRenderer.Position position) {
        if (renderer == null) {
            renderer = new LaTexChatBubbleRenderer(this, BG, position);
        }
        return renderer;
    }

    public static class LaTexChatSerializer implements IChatBubbleData.ChatSerializer {
        @Override
        public IChatBubbleData readFromBuff(FriendlyByteBuf buf) {
            return new LaTexChatBubbleData(buf.readUtf(), buf.readComponent(), buf.readComponent());
        }

        @Override
        public void writeToBuff(FriendlyByteBuf buf, IChatBubbleData data) {
            LaTexChatBubbleData bubbleData = (LaTexChatBubbleData) data;
            buf.writeUtf(bubbleData.formula);
            buf.writeComponent(bubbleData.enName);
            buf.writeComponent(bubbleData.zhName);
        }
    }
}
