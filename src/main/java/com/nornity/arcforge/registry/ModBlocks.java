package com.nornity.arcforge.registry;

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
}
