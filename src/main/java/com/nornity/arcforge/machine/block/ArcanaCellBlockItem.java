package com.nornity.arcforge.machine.block;

import com.nornity.arcforge.machine.blockentity.ArcanaCellBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class ArcanaCellBlockItem extends BlockItem {
    public ArcanaCellBlockItem(ArcanaCellBlock block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        builder.accept(Component.translatable("item.arcforge.arcana_cell.capacity", ArcanaCellBlockEntity.CAPACITY));
    }
}
