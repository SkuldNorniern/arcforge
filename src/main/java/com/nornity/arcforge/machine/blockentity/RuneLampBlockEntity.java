package com.nornity.arcforge.machine.blockentity;

import com.nornity.arcforge.machine.block.RuneLampBlock;
import com.nornity.arcforge.registry.ModBlockEntities;
import com.nornity.arcforge.registry.ModDataComponents;
import com.nornity.arcforge.registry.ModItems;
import com.nornity.arcforge.spellcore.SpellBlueprint;
import com.nornity.arcforge.spellcore.SpellComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class RuneLampBlockEntity extends BlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public RuneLampBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RUNE_LAMP.get(), pos, state);
    }

    public ItemStack getInstalledCore() {
        return items.get(0);
    }

    public void setInstalledCore(ItemStack stack) {
        items.set(0, stack);
        this.setChanged();
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, RuneLampBlockEntity entity) {
        boolean lit = isLightCore(entity.getInstalledCore());
        if (state.getValue(RuneLampBlock.LIT) != lit) {
            level.setBlock(pos, state.setValue(RuneLampBlock.LIT, lit), Block.UPDATE_ALL);
        }
    }

    private static boolean isLightCore(ItemStack core) {
        if (core.isEmpty() || !core.is(ModItems.BASIC_SPELL_CORE.get())) {
            return false;
        }
        SpellBlueprint blueprint = core.getOrDefault(ModDataComponents.SPELL_BLUEPRINT.get(), SpellBlueprint.EMPTY);
        Identifier effect = blueprint.effect();
        return effect != null && effect.equals(SpellComponents.idOf(ModItems.LIGHT_RUNE.get()));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
    }
}
