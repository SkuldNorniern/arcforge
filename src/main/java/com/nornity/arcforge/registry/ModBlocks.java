package com.nornity.arcforge.registry;

import com.nornity.arcforge.machine.block.ArcanaCellBlock;
import com.nornity.arcforge.machine.block.ArcaneFurnaceBlock;
import com.nornity.arcforge.machine.block.RuneLampBlock;
import com.nornity.arcforge.machine.block.RuneScriberBlock;
import com.nornity.arcforge.machine.block.SpellAssemblerBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("arcforge");

    public static final DeferredBlock<Block> RAW_MANA_CRYSTAL_ORE = BLOCKS.register(
        "raw_mana_crystal_ore",
        id -> new Block(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, id))
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(3.0f, 3.0f)
            .requiresCorrectToolForDrops())
    );

    public static final DeferredBlock<ArcanaCellBlock> ARCANA_CELL = BLOCKS.register(
        "arcana_cell",
        id -> new ArcanaCellBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, id))
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .sound(SoundType.AMETHYST)
            .strength(3.0f, 6.0f))
    );

    public static final DeferredBlock<RuneScriberBlock> RUNE_SCRIBER = BLOCKS.register(
        "rune_scriber",
        id -> new RuneScriberBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, id))
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .strength(2.5f, 3.0f)
            .noOcclusion())
    );

    public static final DeferredBlock<SpellAssemblerBlock> SPELL_ASSEMBLER = BLOCKS.register(
        "spell_assembler",
        id -> new SpellAssemblerBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, id))
            .mapColor(MapColor.COLOR_PURPLE)
            .sound(SoundType.AMETHYST)
            .strength(3.0f, 6.0f)
            .noOcclusion())
    );

    public static final DeferredBlock<ArcaneFurnaceBlock> ARCANE_FURNACE = BLOCKS.register(
        "arcane_furnace",
        id -> new ArcaneFurnaceBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, id))
            .mapColor(MapColor.COLOR_GRAY)
            .sound(SoundType.STONE)
            .strength(3.5f, 6.0f)
            .lightLevel(state -> state.getValue(ArcaneFurnaceBlock.LIT) ? 13 : 0)
            .requiresCorrectToolForDrops())
    );

    public static final DeferredBlock<RuneLampBlock> RUNE_LAMP = BLOCKS.register(
        "rune_lamp",
        id -> new RuneLampBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, id))
            .mapColor(MapColor.COLOR_YELLOW)
            .sound(SoundType.AMETHYST)
            .strength(2.0f, 4.0f)
            .lightLevel(state -> state.getValue(RuneLampBlock.LIT) ? 15 : 0)
            .noOcclusion())
    );
}
