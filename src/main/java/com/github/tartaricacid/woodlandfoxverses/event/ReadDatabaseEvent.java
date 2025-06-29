package com.github.tartaricacid.woodlandfoxverses.event;

import com.github.tartaricacid.woodlandfoxverses.resource.ModDataPackListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ReadDatabaseEvent {
    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new ModDataPackListener());
    }
}
