package com.nornity.arcforge.machine.menu;

import com.nornity.arcforge.registry.ModItems;
import com.nornity.arcforge.registry.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ArcaneFurnaceMenu extends AbstractContainerMenu {
    public static final int SLOT_CORE = 0;
    public static final int SLOT_INPUT = 1;
    public static final int SLOT_CHARGE = 2;
    public static final int SLOT_OUTPUT = 3;
    public static final int CONTAINER_SIZE = 4;

    private final Container furnace;

    public ArcaneFurnaceMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(CONTAINER_SIZE));
    }

    public ArcaneFurnaceMenu(int containerId, Inventory inventory, Container furnace) {
        super(ModMenuTypes.ARCANE_FURNACE.get(), containerId);
        this.furnace = furnace;
        checkContainerSize(furnace, CONTAINER_SIZE);
        furnace.startOpen(inventory.player);

        this.addSlot(new Slot(furnace, SLOT_CORE, 8, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.BASIC_SPELL_CORE.get());
            }
        });
        this.addSlot(new Slot(furnace, SLOT_INPUT, 56, 17));
        this.addSlot(new Slot(furnace, SLOT_CHARGE, 56, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.REFINED_MANA_CRYSTAL.get());
            }
        });
        this.addSlot(new Slot(furnace, SLOT_OUTPUT, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        this.addStandardInventorySlots(inventory, 8, 84);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.furnace.stillValid(player);
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
        this.furnace.stopOpen(player);
    }
}
