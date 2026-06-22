package com.nornity.arcforge.registry;

import com.nornity.arcforge.item.SpellCoreItem;
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
    public static final DeferredItem<Item> PULL_RUNE =
        ITEMS.registerSimpleItem("pull_rune");
    public static final DeferredItem<Item> PUSH_RUNE =
        ITEMS.registerSimpleItem("push_rune");
    public static final DeferredItem<Item> TOUCH_GLYPH =
        ITEMS.registerSimpleItem("touch_glyph");
    public static final DeferredItem<Item> SELF_GLYPH =
        ITEMS.registerSimpleItem("self_glyph");
    public static final DeferredItem<Item> AREA_GLYPH =
        ITEMS.registerSimpleItem("area_glyph");
    public static final DeferredItem<Item> BLOCK_SIGIL =
        ITEMS.registerSimpleItem("block_sigil");
    public static final DeferredItem<Item> ITEM_SIGIL =
        ITEMS.registerSimpleItem("item_sigil");
    public static final DeferredItem<Item> MACHINE_SIGIL =
        ITEMS.registerSimpleItem("machine_sigil");

    public static final DeferredItem<SpellCoreItem> BASIC_SPELL_CORE =
        ITEMS.registerItem("basic_spell_core", SpellCoreItem::new, Item.Properties::new);

    public static final DeferredItem<BlockItem> RAW_MANA_CRYSTAL_ORE =
        ITEMS.registerSimpleBlockItem(ModBlocks.RAW_MANA_CRYSTAL_ORE);

    public static final DeferredItem<ArcanaCellBlockItem> ARCANA_CELL =
        ITEMS.registerItem(
            "arcana_cell",
            props -> new ArcanaCellBlockItem(ModBlocks.ARCANA_CELL.get(), props),
            Item.Properties::new
        );

    public static final DeferredItem<BlockItem> RUNE_SCRIBER =
        ITEMS.registerSimpleBlockItem(ModBlocks.RUNE_SCRIBER);

    public static final DeferredItem<BlockItem> SPELL_ASSEMBLER =
        ITEMS.registerSimpleBlockItem(ModBlocks.SPELL_ASSEMBLER);

    public static final DeferredItem<BlockItem> ARCANE_FURNACE =
        ITEMS.registerSimpleBlockItem(ModBlocks.ARCANE_FURNACE);

    public static final DeferredItem<BlockItem> RUNE_LAMP =
        ITEMS.registerSimpleBlockItem(ModBlocks.RUNE_LAMP);
}
