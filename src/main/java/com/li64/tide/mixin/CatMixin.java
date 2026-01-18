package com.li64.tide.mixin;

import com.li64.tide.data.TideTags;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cat.class)
public abstract class CatMixin {
    //? if <1.21 {
    /*@Shadow @Final @Mutable private static Ingredient TEMPT_INGREDIENT;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyIngredient(CallbackInfo ci) {
        TEMPT_INGREDIENT = Ingredient.of(TideTags.Items.CAT_FOOD);
    }
    *///?}
}
