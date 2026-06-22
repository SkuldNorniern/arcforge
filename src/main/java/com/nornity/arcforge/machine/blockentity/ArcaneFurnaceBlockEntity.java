package com.nornity.arcforge.machine.blockentity;

import com.nornity.arcforge.arcana.ArcanaContainer;
import com.nornity.arcforge.arcana.ArcanaStorage;
import com.nornity.arcforge.machine.block.ArcaneFurnaceBlock;
import com.nornity.arcforge.machine.menu.ArcaneFurnaceMenu;
import com.nornity.arcforge.registry.ModBlockEntities;
import com.nornity.arcforge.registry.ModDataComponents;
import com.nornity.arcforge.registry.ModItems;
import com.nornity.arcforge.spellcore.SpellBlueprint;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ArcaneFurnaceBlockEntity extends BaseContainerBlockEntity implements ArcanaContainer {
    public static final long CAPACITY = 4_000;
    public static final long MAX_TRANSFER_RATE = 200;
    public static final long ARCANA_COST_PER_TICK = 1;
    public static final long CRYSTAL_CHARGE_AMOUNT = 500;

    private final ArcanaStorage arcanaStorage = new ArcanaStorage(CAPACITY, MAX_TRANSFER_RATE);
    private NonNullList<ItemStack> items = NonNullList.withSize(ArcaneFurnaceMenu.CONTAINER_SIZE, ItemStack.EMPTY);
    private int cookingProgress;
    private int cookingTotalTime = 100;

    public ArcaneFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARCANE_FURNACE.get(), pos, state);
    }

    @Override
    public ArcanaStorage getArcanaStorage() {
        return arcanaStorage;
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, ArcaneFurnaceBlockEntity entity) {
        entity.tick(level, pos, state);
    }

    private void tick(ServerLevel level, BlockPos pos, BlockState state) {
        chargeFromCrystal();

        ItemStack core = this.items.get(ArcaneFurnaceMenu.SLOT_CORE);
        ItemStack inputStack = this.items.get(ArcaneFurnaceMenu.SLOT_INPUT);

        RecipeHolder<SmeltingRecipe> matched = null;
        if (isHeatCore(core) && !inputStack.isEmpty()) {
            SingleRecipeInput input = new SingleRecipeInput(inputStack);
            matched = level.getServer().getRecipeManager().getRecipeFor(RecipeType.SMELTING, input, level).orElse(null);
        }

        boolean canCook = matched != null && arcanaStorage.getAmount() >= ARCANA_COST_PER_TICK && canAcceptOutput(matched);

        if (canCook) {
            cookingTotalTime = Math.max(1, matched.value().cookingTime() / 2);
            cookingProgress++;
            arcanaStorage.extract(ARCANA_COST_PER_TICK, false);
            if (cookingProgress >= cookingTotalTime) {
                craftItem(matched);
                cookingProgress = 0;
            }
            this.setChanged();
        } else if (cookingProgress > 0) {
            cookingProgress = Math.max(0, cookingProgress - 2);
            this.setChanged();
        }

        if (state.getValue(ArcaneFurnaceBlock.LIT) != canCook) {
            level.setBlock(pos, state.setValue(ArcaneFurnaceBlock.LIT, canCook), Block.UPDATE_ALL);
        }
    }

    private void chargeFromCrystal() {
        ItemStack charge = this.items.get(ArcaneFurnaceMenu.SLOT_CHARGE);
        if (!charge.isEmpty() && charge.is(ModItems.REFINED_MANA_CRYSTAL.get())
            && arcanaStorage.getAmount() < arcanaStorage.getCapacity()) {
            arcanaStorage.receive(CRYSTAL_CHARGE_AMOUNT, false);
            charge.shrink(1);
            this.setChanged();
        }
    }

    private boolean canAcceptOutput(RecipeHolder<SmeltingRecipe> matched) {
        ItemStack output = this.items.get(ArcaneFurnaceMenu.SLOT_OUTPUT);
        if (output.isEmpty()) {
            return true;
        }
        ItemStack result = matched.value().assemble(new SingleRecipeInput(this.items.get(ArcaneFurnaceMenu.SLOT_INPUT)));
        return ItemStack.isSameItemSameComponents(output, result)
            && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void craftItem(RecipeHolder<SmeltingRecipe> matched) {
        ItemStack inputStack = this.items.get(ArcaneFurnaceMenu.SLOT_INPUT);
        ItemStack result = matched.value().assemble(new SingleRecipeInput(inputStack));
        ItemStack output = this.items.get(ArcaneFurnaceMenu.SLOT_OUTPUT);
        if (output.isEmpty()) {
            this.items.set(ArcaneFurnaceMenu.SLOT_OUTPUT, result);
        } else {
            output.grow(result.getCount());
        }
        inputStack.shrink(1);
    }

    private static boolean isHeatCore(ItemStack core) {
        if (core.isEmpty() || !core.is(ModItems.BASIC_SPELL_CORE.get())) {
            return false;
        }
        SpellBlueprint blueprint = core.getOrDefault(ModDataComponents.SPELL_BLUEPRINT.get(), SpellBlueprint.EMPTY);
        Identifier effect = blueprint.effect();
        return effect != null && effect.equals(SpellComponents.idOf(ModItems.HEAT_RUNE.get()));
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.arcforge.arcane_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ArcaneFurnaceMenu(containerId, inventory, this);
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
        if (slot == ArcaneFurnaceMenu.SLOT_OUTPUT) {
            return false;
        }
        if (slot == ArcaneFurnaceMenu.SLOT_CHARGE) {
            return itemStack.is(ModItems.REFINED_MANA_CRYSTAL.get());
        }
        if (slot == ArcaneFurnaceMenu.SLOT_CORE) {
            return itemStack.is(ModItems.BASIC_SPELL_CORE.get());
        }
        return true;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putLong("arcana", arcanaStorage.getAmount());
        output.putInt("cooking_progress", cookingProgress);
        output.putInt("cooking_total_time", cookingTotalTime);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        arcanaStorage.setAmount(input.getLongOr("arcana", 0));
        cookingProgress = input.getIntOr("cooking_progress", 0);
        cookingTotalTime = input.getIntOr("cooking_total_time", 100);
    }
}
