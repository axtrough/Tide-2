package com.li64.tide.data.fishing.modifiers;

import com.li64.tide.Tide;
import com.li64.tide.data.fishing.modifiers.types.ConditionalModifier;
import com.li64.tide.data.fishing.modifiers.types.TemperatureModifier;
import com.li64.tide.registries.TideRegistries;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public interface FishingModifierType<T extends FishingModifier> {
    FishingModifierType<ConditionalModifier> CONDITIONAL = register("conditional", ConditionalModifier.CODEC);
    FishingModifierType<TemperatureModifier> TEMPERATURE = register("temperature", TemperatureModifier.CODEC);

    static void register() {
        Tide.LOG.info("Registering fishing modifiers");
    }

    MapCodec<T> codec();

    static <T extends FishingModifier> FishingModifierType<T> register(String name, MapCodec<T> codec) {
        return register(Tide.resource(name), codec);
    }

    static <T extends FishingModifier> FishingModifierType<T> register(ResourceLocation name, MapCodec<T> codec) {
        /*? if !forge {*/return Registry.register(TideRegistries.FISHING_MODIFIERS, name, () -> codec);
        //?} else {
        /*FishingModifierType<T> type = () -> codec;
        TideRegistries.FISHING_MODIFIERS.register(name, type);
        return type;
        *///?}
    }
}