package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.registries.blocks.entities.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashMap;

public class
TideBlockEntities {
    public static final HashMap<String, BlockEntityType<?>> BLOCK_ENTITIES = new HashMap<>();

    public static final BlockEntityType<? extends LootCrateBlockEntity> WOODEN_CRATE =
            register("wooden_crate", BlockEntityType.Builder.of(WoodenCrateBlockEntity::new,
                    TideBlocks.WOODEN_CRATE).build(null));
    public static final BlockEntityType<? extends LootCrateBlockEntity> OBSIDIAN_CRATE =
            register("obsidian_crate", BlockEntityType.Builder.of(ObsidianCrateBlockEntity::new,
                    TideBlocks.OBSIDIAN_CRATE).build(null));
    public static final BlockEntityType<? extends LootCrateBlockEntity> PURPUR_CRATE =
            register("purpur_crate", BlockEntityType.Builder.of(PurpurCrateBlockEntity::new,
                    TideBlocks.PURPUR_CRATE).build(null));

    public static final BlockEntityType<FishDisplayBlockEntity> FISH_DISPLAY =
            register("fish_display", BlockEntityType.Builder.of(FishDisplayBlockEntity::new,
                    TideBlocks.FISH_DISPLAY).build(null));

    public static <T extends BlockEntity> BlockEntityType<T> register(String key, BlockEntityType<T> block) {
        BLOCK_ENTITIES.put(key, block);
        return Tide.PLATFORM.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Tide.resource(key), block);
    }

    public static void init() {}
}
