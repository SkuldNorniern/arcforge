package com.nornity.arcforge.machine.block;

import com.mojang.serialization.MapCodec;
import com.nornity.arcforge.machine.blockentity.ArcaneFurnaceBlockEntity;
import com.nornity.arcforge.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class ArcaneFurnaceBlock extends BaseEntityBlock {
    public static final MapCodec<ArcaneFurnaceBlock> CODEC = simpleCodec(ArcaneFurnaceBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ArcaneFurnaceBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected MapCodec<ArcaneFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArcaneFurnaceBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level instanceof ServerLevel serverLevel
            ? createTickerHelper(blockEntityType, ModBlockEntities.ARCANE_FURNACE.get(),
                (innerLevel, pos, blockState, entity) -> ArcaneFurnaceBlockEntity.serverTick(serverLevel, pos, blockState, entity))
            : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof ArcaneFurnaceBlockEntity furnace && !level.isClientSide()) {
            player.openMenu(furnace);
        }
        return InteractionResult.SUCCESS;
    }
}
