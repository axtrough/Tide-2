package com.li64.tide.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.li64.tide.registries.TideRegistries;

@Mixin(BuiltInRegistries.class)
public class BuiltInRegistriesMixin {
    //? if fabric {
    @Inject(at = @At(value = "HEAD"), method = "bootStrap")
    private static void bootstrap(CallbackInfo ci) {
        //TideRegistries.init();
    }
    //?}
}