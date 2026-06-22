package com.nornity.arcforge.machine.block;

import com.mojang.serialization.MapCodec;
import com.nornity.arcforge.machine.blockentity.RuneLampBlockEntity;
import com.nornity.arcforge.registry.ModBlockEntities;
import com.nornity.arcforge.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

public class RuneLampBlock extends BaseEntityBlock {
    public static final MapCodec<RuneLampBlock> CODEC = simpleCodec(RuneLampBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public RuneLampBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected MapCodec<RuneLampBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RuneLampBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level instanceof ServerLevel serverLevel
            ? createTickerHelper(blockEntityType, ModBlockEntities.RUNE_LAMP.get(),
                (innerLevel, pos, blockState, entity) -> RuneLampBlockEntity.serverTick(serverLevel, pos, blockState, entity))
            : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide() || !(level.getBlockEntity(pos) instanceof RuneLampBlockEntity lamp)) {
            return InteractionResult.SUCCESS;
        }

        ItemStack held = player.getMainHandItem();
        ItemStack installed = lamp.getInstalledCore();

        if (installed.isEmpty() && held.is(ModItems.BASIC_SPELL_CORE.get())) {
            lamp.setInstalledCore(held.copyWithCount(1));
            held.shrink(1);
        } else if (!installed.isEmpty() && held.isEmpty()) {
            ItemStack removed = installed.copy();
            if (!player.getInventory().add(removed)) {
                player.drop(removed, false);
            }
            lamp.setInstalledCore(ItemStack.EMPTY);
        }

        return InteractionResult.SUCCESS;
    }
}
