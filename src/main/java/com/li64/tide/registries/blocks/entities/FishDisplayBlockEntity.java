package com.li64.tide.registries.blocks.entities;

import com.li64.tide.data.fishing.DisplayData;
import com.li64.tide.data.fishing.FishData;
import com.li64.tide.data.item.TideItemData;
import com.li64.tide.registries.TideBlockEntities;
import com.li64.tide.registries.blocks.FishDisplayBlock;
import com.li64.tide.registries.blocks.FishDisplayShape;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/*? if >=1.21*//*import net.minecraft.core.HolderLookup;*/

public class FishDisplayBlockEntity extends BlockEntity {
    private ItemStack fish;
    private DisplayData displayData;
    private Entity renderedEntity;

    public FishDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(TideBlockEntities.FISH_DISPLAY, pos, state);
    }

    public boolean isEmpty() {
        return this.fish == null || displayData == null;
    }

    public DisplayData getDisplayData() {
        return this.displayData;
    }

    public ItemStack getDisplayStack() {
        return this.fish;
    }

    public boolean setDisplayStack(ItemStack stack) {
        if (!isEmpty()) return false;
        var dataOp = FishData.getExact(stack).flatMap(FishData::display);
        if (dataOp.isEmpty()) return false;
        this.fish = stack;
        DisplayData displayData = dataOp.get();
        double lengthCm = TideItemData.FISH_LENGTH.get(stack);

        this.displayData = new DisplayData(displayData.entityKey(), displayData.nbt(), displayData.shape(),
                displayData.x(), displayData.y(), displayData.z(),
                displayData.roll(), displayData.pitch(), displayData.yaw(),
                (float) lengthCm
        );

        this.updateShape(displayData.shape());
        this.markUpdated();
        return true;
    }

    public ItemStack takeDisplayStack() {
        ItemStack stack = this.fish;
        this.fish = null;
        this.displayData = null;
        this.renderedEntity = null;
        this.updateShape(FishDisplayShape.SHAPE_1x1);
        this.markUpdated();
        return stack;
    }

    private void updateShape(FishDisplayShape shape) {
        if (this.level == null) return;
        this.level.setBlock(getBlockPos(), getBlockState().setValue(FishDisplayBlock.SHAPE, shape), 1);
    }

    //? if >=1.21 {
    /*@Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        if (this.fish != null) tag.put("fish", this.fish.save(registries));
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        if (tag.contains("fish")) {
            ItemStack.parse(registries, tag.get("fish")).ifPresent(this::setDisplayStack);
    } else {
            this.fish = null;
            this.displayData = null;
            this.renderedEntity = null; //should work but not tested
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    *///?} else {

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        if (this.fish != null) tag.put("fish", this.fish.save(new CompoundTag()));
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        if (tag.contains("fish")) {
            ItemStack stack = ItemStack.of(tag.getCompound("fish"));
            if (!stack.isEmpty()) this.setDisplayStack(stack);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }
    //?}

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected void markUpdated() {
        this.setChanged();
        if (this.getLevel() != null) {
            this.getLevel().sendBlockUpdated(
                    this.getBlockPos(),
                    this.getBlockState(),
                    this.getBlockState(), 3);
        }
    }

    public Entity getRenderedEntity() {
        return this.renderedEntity;
    }

    public void setRenderedEntity(Entity entity) {
        this.renderedEntity = entity;
    }
}
