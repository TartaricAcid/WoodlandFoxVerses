package com.github.tartaricacid.woodlandfoxverses.chatbubble;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.client.chatbubble.LaTexChatBubbleRenderer;
import com.github.tartaricacid.woodlandfoxverses.config.ModGeneralConfig;
import com.github.tartaricacid.woodlandfoxverses.resource.math.MathFormula;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LaTexChatBubbleData implements IChatBubbleData {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(WoodlandFoxVerses.MOD_ID, "latex");
    public static final ResourceLocation EN_FONT = ResourceLocation.fromNamespaceAndPath(WoodlandFoxVerses.MOD_ID, "libertinus_sans");
    public static final ResourceLocation ZH_FONT = ResourceLocation.fromNamespaceAndPath(WoodlandFoxVerses.MOD_ID, "chunqiu");
    public static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(WoodlandFoxVerses.MOD_ID, "textures/words.png");

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
        return ModGeneralConfig.BUBBLE_SHOW_DURATION.get();
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
            return new LaTexChatBubbleData(buf.readUtf(),
                    buf.readJsonWithCodec(ComponentSerialization.CODEC),
                    buf.readJsonWithCodec(ComponentSerialization.CODEC));
        }

        @Override
        public void writeToBuff(FriendlyByteBuf buf, IChatBubbleData data) {
            LaTexChatBubbleData bubbleData = (LaTexChatBubbleData) data;
            buf.writeUtf(bubbleData.formula);
            buf.writeJsonWithCodec(ComponentSerialization.CODEC, bubbleData.enName);
            buf.writeJsonWithCodec(ComponentSerialization.CODEC, bubbleData.zhName);
        }
    }
}
