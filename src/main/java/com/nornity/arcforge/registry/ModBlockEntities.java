package com.nornity.arcforge.registry;

import com.nornity.arcforge.machine.blockentity.ArcanaCellBlockEntity;
import com.nornity.arcforge.machine.blockentity.ArcaneFurnaceBlockEntity;
import com.nornity.arcforge.machine.blockentity.SpellAssemblerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "arcforge");

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArcanaCellBlockEntity>> ARCANA_CELL =
        BLOCK_ENTITY_TYPES.register(
            "arcana_cell",
            () -> new BlockEntityType<>(ArcanaCellBlockEntity::new, ModBlocks.ARCANA_CELL.get())
        );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpellAssemblerBlockEntity>> SPELL_ASSEMBLER =
        BLOCK_ENTITY_TYPES.register(
            "spell_assembler",
            () -> new BlockEntityType<>(SpellAssemblerBlockEntity::new, ModBlocks.SPELL_ASSEMBLER.get())
        );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArcaneFurnaceBlockEntity>> ARCANE_FURNACE =
        BLOCK_ENTITY_TYPES.register(
            "arcane_furnace",
            () -> new BlockEntityType<>(ArcaneFurnaceBlockEntity::new, ModBlocks.ARCANE_FURNACE.get())
        );
}
