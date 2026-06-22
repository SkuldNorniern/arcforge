package com.nornity.arcforge;

import com.mojang.logging.LogUtils;
import com.nornity.arcforge.config.Config;
import com.nornity.arcforge.registry.ModBlockEntities;
import com.nornity.arcforge.registry.ModBlocks;
import com.nornity.arcforge.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(Arcforge.MODID)
public class Arcforge {
    public static final String MODID = "arcforge";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB =
        CREATIVE_MODE_TABS.register("arcforge_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.arcforge"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.BASIC_SPELL_CORE.get().getDefaultInstance())
            .displayItems((params, output) -> {
                output.accept(ModItems.RAW_MANA_CRYSTAL.get());
                output.accept(ModItems.REFINED_MANA_CRYSTAL.get());
                output.accept(ModItems.RUNIC_DUST.get());
                output.accept(ModItems.BRASS_FRAME.get());
                output.accept(ModItems.ARCANA_COIL.get());
                output.accept(ModItems.RUNE_PLATE.get());
                output.accept(ModItems.HEAT_RUNE.get());
                output.accept(ModItems.LIGHT_RUNE.get());
                output.accept(ModItems.BASIC_SPELL_CORE.get());
                output.accept(ModItems.RAW_MANA_CRYSTAL_ORE.get());
                output.accept(ModItems.ARCANA_CELL.get());
            })
            .build());

    public Arcforge(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    }
}
