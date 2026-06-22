package com.nornity.arcforge.item;

import com.nornity.arcforge.registry.ModDataComponents;
import com.nornity.arcforge.spellcore.SpellBlueprint;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class SpellCoreItem extends Item {
    public SpellCoreItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        SpellBlueprint blueprint = itemStack.getOrDefault(ModDataComponents.SPELL_BLUEPRINT.get(), SpellBlueprint.EMPTY);
        if (blueprint.isEmpty()) {
            builder.accept(Component.translatable("item.arcforge.spell_core.no_components"));
            return;
        }
        appendPart(builder, blueprint.delivery());
        appendPart(builder, blueprint.effect());
        appendPart(builder, blueprint.target());
        for (Identifier modifierId : blueprint.modifiers()) {
            appendPart(builder, modifierId);
        }
    }

    private static void appendPart(Consumer<Component> builder, Identifier partId) {
        if (partId != null) {
            builder.accept(Component.translatable("item.arcforge." + partId.getPath()));
        }
    }
}
