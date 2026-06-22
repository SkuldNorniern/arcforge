package com.nornity.arcforge.spellcore;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record SpellBlueprint(@Nullable Identifier delivery, @Nullable Identifier effect, @Nullable Identifier target, List<Identifier> modifiers) {
    public static final SpellBlueprint EMPTY = new SpellBlueprint(null, null, null, List.of());

    public static final Codec<SpellBlueprint> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.optionalFieldOf("delivery").forGetter(SpellBlueprint::deliveryOpt),
        Identifier.CODEC.optionalFieldOf("effect").forGetter(SpellBlueprint::effectOpt),
        Identifier.CODEC.optionalFieldOf("target").forGetter(SpellBlueprint::targetOpt),
        Identifier.CODEC.listOf().optionalFieldOf("modifiers", List.of()).forGetter(SpellBlueprint::modifiers)
    ).apply(instance, (delivery, effect, target, modifiers) ->
        new SpellBlueprint(delivery.orElse(null), effect.orElse(null), target.orElse(null), modifiers)));

    public static final StreamCodec<RegistryFriendlyByteBuf, SpellBlueprint> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.optional(Identifier.STREAM_CODEC), SpellBlueprint::deliveryOpt,
        ByteBufCodecs.optional(Identifier.STREAM_CODEC), SpellBlueprint::effectOpt,
        ByteBufCodecs.optional(Identifier.STREAM_CODEC), SpellBlueprint::targetOpt,
        ByteBufCodecs.<RegistryFriendlyByteBuf, Identifier, List<Identifier>>collection(ArrayList::new, Identifier.STREAM_CODEC), SpellBlueprint::modifiers,
        (delivery, effect, target, modifiers) -> new SpellBlueprint(delivery.orElse(null), effect.orElse(null), target.orElse(null), modifiers)
    );

    public Optional<Identifier> deliveryOpt() {
        return Optional.ofNullable(delivery);
    }

    public Optional<Identifier> effectOpt() {
        return Optional.ofNullable(effect);
    }

    public Optional<Identifier> targetOpt() {
        return Optional.ofNullable(target);
    }

    public boolean isEmpty() {
        return delivery == null && effect == null && target == null && modifiers.isEmpty();
    }
}
