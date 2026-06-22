package com.nornity.arcforge.machine.block;

import com.nornity.arcforge.machine.blockentity.SpellAssemblerBlockEntity;
import com.nornity.arcforge.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class SpellAssemblerBlock extends BaseEntityBlock {
    public static final com.mojang.serialization.MapCodec<SpellAssemblerBlock> CODEC = simpleCodec(SpellAssemblerBlock::new);
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 13, 16);

    public SpellAssemblerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected com.mojang.serialization.MapCodec<SpellAssemblerBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpellAssemblerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level instanceof ServerLevel serverLevel
            ? createTickerHelper(blockEntityType, ModBlockEntities.SPELL_ASSEMBLER.get(),
                (innerLevel, pos, blockState, entity) -> SpellAssemblerBlockEntity.serverTick(serverLevel, pos, blockState, entity))
            : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof SpellAssemblerBlockEntity assembler && !level.isClientSide()) {
            player.openMenu(assembler);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
