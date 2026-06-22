package com.nornity.arcforge.registry;

import com.nornity.arcforge.machine.menu.SpellAssemblerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
        DeferredRegister.create(Registries.MENU, "arcforge");

    public static final DeferredHolder<MenuType<?>, MenuType<SpellAssemblerMenu>> SPELL_ASSEMBLER =
        MENU_TYPES.register("spell_assembler",
            () -> IMenuTypeExtension.create((windowId, inventory, buf) -> new SpellAssemblerMenu(windowId, inventory)));
}
