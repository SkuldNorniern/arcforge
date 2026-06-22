package com.nornity.arcforge.client;

import com.nornity.arcforge.Arcforge;
import com.nornity.arcforge.client.screen.ArcaneFurnaceScreen;
import com.nornity.arcforge.client.screen.SpellAssemblerScreen;
import com.nornity.arcforge.registry.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Arcforge.MODID, value = Dist.CLIENT)
public class ArcforgeClient {
    @SubscribeEvent
    static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SPELL_ASSEMBLER.get(), SpellAssemblerScreen::new);
        event.register(ModMenuTypes.ARCANE_FURNACE.get(), ArcaneFurnaceScreen::new);
    }
}
