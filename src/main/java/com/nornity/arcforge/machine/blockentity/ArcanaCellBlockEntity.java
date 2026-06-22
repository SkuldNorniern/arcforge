package com.nornity.arcforge.machine.blockentity;

import com.nornity.arcforge.arcana.ArcanaContainer;
import com.nornity.arcforge.arcana.ArcanaStorage;
import com.nornity.arcforge.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ArcanaCellBlockEntity extends BlockEntity implements ArcanaContainer {
    public static final long CAPACITY = 10_000;
    public static final long MAX_TRANSFER_RATE = 100;

    private final ArcanaStorage arcanaStorage = new ArcanaStorage(CAPACITY, MAX_TRANSFER_RATE);

    public ArcanaCellBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARCANA_CELL.get(), pos, state);
    }

    @Override
    public ArcanaStorage getArcanaStorage() {
        return arcanaStorage;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putLong("arcana", arcanaStorage.getAmount());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        arcanaStorage.setAmount(input.getLongOr("arcana", 0));
    }
}
