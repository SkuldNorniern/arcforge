package com.nornity.arcforge.registry;

import com.nornity.arcforge.machine.block.ArcanaCellBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("arcforge");

    public static final DeferredItem<Item> RAW_MANA_CRYSTAL =
        ITEMS.registerSimpleItem("raw_mana_crystal");
    public static final DeferredItem<Item> REFINED_MANA_CRYSTAL =
        ITEMS.registerSimpleItem("refined_mana_crystal");
    public static final DeferredItem<Item> RUNIC_DUST =
        ITEMS.registerSimpleItem("runic_dust");
    public static final DeferredItem<Item> BRASS_FRAME =
        ITEMS.registerSimpleItem("brass_frame");
    public static final DeferredItem<Item> ARCANA_COIL =
        ITEMS.registerSimpleItem("arcana_coil");
    public static final DeferredItem<Item> RUNE_PLATE =
        ITEMS.registerSimpleItem("rune_plate");
    public static final DeferredItem<Item> HEAT_RUNE =
        ITEMS.registerSimpleItem("heat_rune");
    public static final DeferredItem<Item> LIGHT_RUNE =
        ITEMS.registerSimpleItem("light_rune");
    public static final DeferredItem<Item> BASIC_SPELL_CORE =
        ITEMS.registerSimpleItem("basic_spell_core");

    public static final DeferredItem<BlockItem> RAW_MANA_CRYSTAL_ORE =
        ITEMS.registerSimpleBlockItem(ModBlocks.RAW_MANA_CRYSTAL_ORE);

    public static final DeferredItem<ArcanaCellBlockItem> ARCANA_CELL =
        ITEMS.registerItem(
            "arcana_cell",
            props -> new ArcanaCellBlockItem(ModBlocks.ARCANA_CELL.get(), props),
            Item.Properties::new
        );
}
