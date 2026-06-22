package com.nornity.arcforge.registry;

import com.nornity.arcforge.spellcore.SpellBlueprint;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "arcforge");

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SpellBlueprint>> SPELL_BLUEPRINT =
        DATA_COMPONENT_TYPES.register(
            "spell_blueprint",
            () -> DataComponentType.<SpellBlueprint>builder()
                .persistent(SpellBlueprint.CODEC)
                .networkSynchronized(SpellBlueprint.STREAM_CODEC)
                .build()
        );
}
