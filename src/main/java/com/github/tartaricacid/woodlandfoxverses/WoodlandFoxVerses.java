package com.github.tartaricacid.woodlandfoxverses;

import com.github.tartaricacid.woodlandfoxverses.config.ModGeneralConfig;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.Random;


@Mod(WoodlandFoxVerses.MOD_ID)
public class WoodlandFoxVerses {
    public static final String MOD_ID = "woodlandfoxverses";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Random RANDOM = new Random();

    public WoodlandFoxVerses(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ModGeneralConfig.init());
    }
}
