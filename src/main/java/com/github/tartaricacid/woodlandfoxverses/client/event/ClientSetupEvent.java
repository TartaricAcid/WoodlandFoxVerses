package com.github.tartaricacid.woodlandfoxverses.client.event;

import com.github.tartaricacid.woodlandfoxverses.WoodlandFoxVerses;
import com.github.tartaricacid.woodlandfoxverses.client.cloth.ClothConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = WoodlandFoxVerses.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvent {
    public static final String CLOTH_CONFIG = "cloth_config";

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (!ModList.get().isLoaded(CLOTH_CONFIG)) {
                return;
            }
            NeoForge.EVENT_BUS.register(new ClothConfigScreen());
        });
    }
}
