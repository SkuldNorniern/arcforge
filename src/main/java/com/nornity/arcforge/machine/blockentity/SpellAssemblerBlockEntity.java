package com.nornity.arcforge.machine.blockentity;

import com.nornity.arcforge.machine.menu.SpellAssemblerMenu;
import com.nornity.arcforge.registry.ModBlockEntities;
import com.nornity.arcforge.registry.ModDataComponents;
import com.nornity.arcforge.registry.ModItems;
import com.nornity.arcforge.spellcore.SpellBlueprint;
import com.nornity.arcforge.spellcore.SpellComponentCategory;
import com.nornity.arcforge.spellcore.SpellComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.List;

public class SpellAssemblerBlockEntity extends BaseContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(SpellAssemblerMenu.CONTAINER_SIZE, ItemStack.EMPTY);

    public SpellAssemblerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPELL_ASSEMBLER.get(), pos, state);
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, SpellAssemblerBlockEntity entity) {
        entity.tryAssemble();
    }

    private void tryAssemble() {
        ItemStack core = this.items.get(SpellAssemblerMenu.SLOT_CORE);
        if (core.isEmpty() || !core.is(ModItems.BASIC_SPELL_CORE.get())) {
            return;
        }

        ItemStack delivery = this.items.get(SpellAssemblerMenu.SLOT_DELIVERY);
        ItemStack effect = this.items.get(SpellAssemblerMenu.SLOT_EFFECT);
        ItemStack target = this.items.get(SpellAssemblerMenu.SLOT_TARGET);
        if (!isValid(delivery, SpellComponentCategory.DELIVERY_SHAPE)
            || !isValid(effect, SpellComponentCategory.EFFECT_CORE)
            || !isValid(target, SpellComponentCategory.TARGET_MEDIUM)) {
            return;
        }

        ItemStack output = this.items.get(SpellAssemblerMenu.SLOT_OUTPUT);
        if (!output.isEmpty() && (!output.is(ModItems.BASIC_SPELL_CORE.get()) || output.getCount() >= output.getMaxStackSize())) {
            return;
        }

        List<Identifier> modifiers = new ArrayList<>();
        List<Integer> modifierSlots = new ArrayList<>();
        for (int i = 0; i < SpellAssemblerMenu.MODIFIER_SLOT_COUNT; i++) {
            int slotIndex = SpellAssemblerMenu.SLOT_MODIFIER_START + i;
            ItemStack stack = this.items.get(slotIndex);
            if (!stack.isEmpty() && SpellComponents.isCategory(stack.getItem(), SpellComponentCategory.MODIFIER)) {
                modifiers.add(SpellComponents.idOf(stack.getItem()));
                modifierSlots.add(slotIndex);
            }
        }

        SpellBlueprint blueprint = new SpellBlueprint(
            SpellComponents.idOf(delivery.getItem()),
            SpellComponents.idOf(effect.getItem()),
            SpellComponents.idOf(target.getItem()),
            List.copyOf(modifiers)
        );

        ItemStack result = core.copyWithCount(1);
        result.set(ModDataComponents.SPELL_BLUEPRINT.get(), blueprint);

        core.shrink(1);
        delivery.shrink(1);
        effect.shrink(1);
        target.shrink(1);
        for (int slot : modifierSlots) {
            this.items.get(slot).shrink(1);
        }

        if (output.isEmpty()) {
            this.items.set(SpellAssemblerMenu.SLOT_OUTPUT, result);
        } else {
            output.grow(1);
        }

        this.setChanged();
    }

    private static boolean isValid(ItemStack stack, SpellComponentCategory category) {
        return !stack.isEmpty() && SpellComponents.isCategory(stack.getItem(), category);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.arcforge.spell_assembler");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new SpellAssemblerMenu(containerId, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (slot == SpellAssemblerMenu.SLOT_OUTPUT) {
            return false;
        }
        if (slot == SpellAssemblerMenu.SLOT_DELIVERY) {
            return SpellComponents.isCategory(itemStack.getItem(), SpellComponentCategory.DELIVERY_SHAPE);
        }
        if (slot == SpellAssemblerMenu.SLOT_EFFECT) {
            return SpellComponents.isCategory(itemStack.getItem(), SpellComponentCategory.EFFECT_CORE);
        }
        if (slot == SpellAssemblerMenu.SLOT_TARGET) {
            return SpellComponents.isCategory(itemStack.getItem(), SpellComponentCategory.TARGET_MEDIUM);
        }
        if (slot >= SpellAssemblerMenu.SLOT_MODIFIER_START && slot < SpellAssemblerMenu.SLOT_OUTPUT) {
            return SpellComponents.isCategory(itemStack.getItem(), SpellComponentCategory.MODIFIER);
        }
        return true;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
    }
}
