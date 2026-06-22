package com.nornity.arcforge.spellcore;

import com.nornity.arcforge.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpellComponents {
    public static final int MAX_MODIFIER_SLOTS = 4;
    private static final Map<Identifier, SpellComponentCategory> CATEGORY_BY_ID = new LinkedHashMap<>();

    static {
        register(ModItems.TOUCH_GLYPH.get(), SpellComponentCategory.DELIVERY_SHAPE);
        register(ModItems.SELF_GLYPH.get(), SpellComponentCategory.DELIVERY_SHAPE);
        register(ModItems.AREA_GLYPH.get(), SpellComponentCategory.DELIVERY_SHAPE);

        register(ModItems.HEAT_RUNE.get(), SpellComponentCategory.EFFECT_CORE);
        register(ModItems.LIGHT_RUNE.get(), SpellComponentCategory.EFFECT_CORE);
        register(ModItems.PULL_RUNE.get(), SpellComponentCategory.EFFECT_CORE);
        register(ModItems.PUSH_RUNE.get(), SpellComponentCategory.EFFECT_CORE);

        register(ModItems.BLOCK_SIGIL.get(), SpellComponentCategory.TARGET_MEDIUM);
        register(ModItems.ITEM_SIGIL.get(), SpellComponentCategory.TARGET_MEDIUM);
        register(ModItems.MACHINE_SIGIL.get(), SpellComponentCategory.TARGET_MEDIUM);
    }

    private SpellComponents() {}

    private static void register(Item item, SpellComponentCategory category) {
        CATEGORY_BY_ID.put(BuiltInRegistries.ITEM.getKey(item), category);
    }

    @Nullable
    public static SpellComponentCategory categoryOf(Item item) {
        return CATEGORY_BY_ID.get(BuiltInRegistries.ITEM.getKey(item));
    }

    public static boolean isCategory(Item item, SpellComponentCategory category) {
        return categoryOf(item) == category;
    }

    public static Identifier idOf(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
