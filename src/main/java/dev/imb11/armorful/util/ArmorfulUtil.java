package dev.imb11.armorful.util;

import dev.imb11.armorful.Armorful;
import dev.imb11.armorful.loot.ArmorfulLootTables;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;
import java.util.List;

public class ArmorfulUtil {
    public static Identifier id(String id) {
        return Identifier.tryBuild(Armorful.MOD_ID, id);
    }

    public static Identifier defaultID(String id) {
        return Identifier.tryBuild("minecraft", id);
    }

    public static ModelLayerLocation registerEntityModelLayer(String id, EntityModelLayerRegistry.TexturedModelDataProvider texturedModelDataProvider) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(id(id), id);
        EntityModelLayerRegistry.registerModelLayer(modelLayer, texturedModelDataProvider);
        return modelLayer;
    }

    private static Identifier getNaturalSpawnTable(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD  -> id("entities/natural_spawn/helmet");
            case CHEST -> id("entities/natural_spawn/chestplate");
            case LEGS  -> id("entities/natural_spawn/legs");
            case FEET  -> id("entities/natural_spawn/feet");
            default    -> null;
        };
    }

    public static List<ItemStack> getNaturalSpawnItemsFromLootTable(LivingEntity entity, EquipmentSlot slot) {
        Identifier tableId = getNaturalSpawnTable(slot);
        if (tableId == null) return List.of();
        var key = ResourceKey.create(Registries.LOOT_TABLE, tableId);
        LootTable loot = entity.level().getServer().reloadableRegistries().getLootTable(key);
        LootParams lootParams = new LootParams.Builder((ServerLevel) entity.level())
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .create(ArmorfulLootTables.SLOT);
        List<ItemStack> items = new ArrayList<>();
        loot.getRandomItems(lootParams, items::add);
        return items;
    }

    public static void giveArmorNaturally(RandomSource random, LivingEntity entity, DifficultyInstance difficulty) {
        if (random.nextFloat() < 0.24F) {
            float difficultyChance = entity.level().getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            boolean flag = true;

            for (EquipmentSlot slotType : EquipmentSlot.values()) {
                if (slotType.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                    if (!flag && random.nextFloat() < difficultyChance) {
                        break;
                    }

                    flag = false;
                    for (ItemStack stack : getNaturalSpawnItemsFromLootTable(entity, slotType)) {
                        entity.setItemSlot(slotType, stack);
                    }
                }
            }
        }
    }
}
