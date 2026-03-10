package dev.imb11.armorful.loot;

import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ArmorfulLootTables {
    public static final Identifier ILLAGER_HELMET = ArmorfulUtil.id("entities/illager_helmet");
    public static final Identifier ILLAGER_CHEST = ArmorfulUtil.id("entities/illager_chestplate");
    public static final Identifier ILLAGER_LEGGINGS = ArmorfulUtil.id("entities/illager_legs");
    public static final Identifier ILLAGER_FEET = ArmorfulUtil.id("entities/illager_feet");
    public static final Identifier NATURAL_SPAWN_HELMET = ArmorfulUtil.id("entities/natural_spawn/helmet");
    public static final Identifier NATURAL_SPAWN_CHEST = ArmorfulUtil.id("entities/natural_spawn/chestplate");
    public static final Identifier NATURAL_SPAWN_LEGGINGS = ArmorfulUtil.id("entities/natural_spawn/legs");
    public static final Identifier NATURAL_SPAWN_FEET = ArmorfulUtil.id("entities/natural_spawn/feet");

    public static final ContextKeySet SLOT = new ContextKeySet.Builder()
            .required(LootContextParams.THIS_ENTITY)
            .required(LootContextParams.ORIGIN)
            .build();

    public static void init() {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, ArmorfulUtil.id("wave"), RaidWaveCondition.WAVE);
    }
}
