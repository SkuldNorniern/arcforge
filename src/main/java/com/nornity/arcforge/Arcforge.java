package com.nornity.arcforge;

import com.mojang.logging.LogUtils;
import com.nornity.arcforge.config.Config;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(Arcforge.MODID)
public class Arcforge {
    public static final String MODID = "arcforge";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Arcforge(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    }
}
