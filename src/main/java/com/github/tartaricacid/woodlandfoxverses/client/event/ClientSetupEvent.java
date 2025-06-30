package com.github.tartaricacid.woodlandfoxverses.client.event;

import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.client.cloth.ClothConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = WoodlandFoxVerses.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvent {
    public static final String CLOTH_CONFIG = "cloth_config";

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (!ModList.get().isLoaded(CLOTH_CONFIG)) {
                return;
            }
            MinecraftForge.EVENT_BUS.register(new ClothConfigScreen());
        });
    }
}
