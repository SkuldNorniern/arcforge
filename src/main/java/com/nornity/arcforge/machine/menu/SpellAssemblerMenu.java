package com.nornity.arcforge.machine.menu;

import com.nornity.arcforge.registry.ModMenuTypes;
import com.nornity.arcforge.spellcore.SpellComponentCategory;
import com.nornity.arcforge.spellcore.SpellComponents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SpellAssemblerMenu extends AbstractContainerMenu {
    public static final int SLOT_CORE = 0;
    public static final int SLOT_DELIVERY = 1;
    public static final int SLOT_EFFECT = 2;
    public static final int SLOT_TARGET = 3;
    public static final int SLOT_MODIFIER_START = 4;
    public static final int MODIFIER_SLOT_COUNT = SpellComponents.MAX_MODIFIER_SLOTS;
    public static final int SLOT_OUTPUT = SLOT_MODIFIER_START + MODIFIER_SLOT_COUNT;
    public static final int CONTAINER_SIZE = SLOT_OUTPUT + 1;

    private final Container assembler;

    public SpellAssemblerMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(CONTAINER_SIZE));
    }

    public SpellAssemblerMenu(int containerId, Inventory inventory, Container assembler) {
        super(ModMenuTypes.SPELL_ASSEMBLER.get(), containerId);
        this.assembler = assembler;
        checkContainerSize(assembler, CONTAINER_SIZE);
        assembler.startOpen(inventory.player);

        this.addSlot(new Slot(assembler, SLOT_CORE, 8, 53));
        this.addSlot(categorySlot(assembler, SLOT_DELIVERY, 26, 17, SpellComponentCategory.DELIVERY_SHAPE));
        this.addSlot(categorySlot(assembler, SLOT_EFFECT, 44, 17, SpellComponentCategory.EFFECT_CORE));
        this.addSlot(categorySlot(assembler, SLOT_TARGET, 62, 17, SpellComponentCategory.TARGET_MEDIUM));
        for (int i = 0; i < MODIFIER_SLOT_COUNT; i++) {
            this.addSlot(categorySlot(assembler, SLOT_MODIFIER_START + i, 80 + i * 18, 17, SpellComponentCategory.MODIFIER));
        }
        this.addSlot(new Slot(assembler, SLOT_OUTPUT, 152, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        this.addStandardInventorySlots(inventory, 8, 84);
    }

    private static Slot categorySlot(Container container, int index, int x, int y, SpellComponentCategory category) {
        return new Slot(container, index, x, y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return SpellComponents.isCategory(stack.getItem(), category);
            }
        };
    }

    @Override
    public boolean stillValid(Player player) {
        return this.assembler.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack clicked = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            clicked = stack.copy();
            if (slotIndex < CONTAINER_SIZE) {
                if (!this.moveItemStackTo(stack, CONTAINER_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, CONTAINER_SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return clicked;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.assembler.stopOpen(player);
    }
}
