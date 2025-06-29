package com.github.tartaricacid.woodlandfoxverses;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.Random;


@Mod(WoodlandFoxVerses.MOD_ID)
public class WoodlandFoxVerses {
    public static final String MOD_ID = "woodlandfoxverses";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Random RANDOM = new Random();

    public WoodlandFoxVerses() {
    }
}
