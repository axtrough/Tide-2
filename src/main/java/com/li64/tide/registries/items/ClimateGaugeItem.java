package com.li64.tide.registries.items;

import com.li64.tide.Tide;
import com.li64.tide.util.TideUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ClimateGaugeItem extends SimpleTooltipItem {
    public ClimateGaugeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            float temp = TideUtils.getTemperatureAt(player.blockPosition(), serverLevel);
            float degrees = Math.round(TideUtils.mcTempToRealTemp(temp) * 10) / 10.0f;
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                    Component.literal(degrees + (Tide.CONFIG.journal.useFahrenheit ? "°F" : "°C"))));
            serverPlayer.level().playSound(null, serverPlayer.blockPosition(),
                    SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.PLAYERS, 1.0f, 2.0f);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    @Override
    public void addTooltip(ItemStack stack, Consumer<Component> tooltip) {
        Style gray = Component.empty().getStyle().withColor(ChatFormatting.GRAY);
        tooltip.accept(Component.translatable("item.tide.climate_gauge.desc_0").setStyle(gray));
        tooltip.accept(Component.translatable("item.tide.climate_gauge.desc_1").setStyle(gray));
    }
}
