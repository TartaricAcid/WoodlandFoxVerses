package com.github.tartaricacid.woodlandfoxverses.event;

import com.github.tartaricacid.woodlandfoxverses.resource.ModDataPackListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber
public class ReadDatabaseEvent {
    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new ModDataPackListener());
    }
}
