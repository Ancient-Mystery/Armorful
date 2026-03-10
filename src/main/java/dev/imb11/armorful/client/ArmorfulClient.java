package dev.imb11.armorful.client;

import dev.imb11.armorful.client.model.IllagerArmorModel;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import dev.imb11.armorful.client.renderer.*;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.EntityType;

public class ArmorfulClient implements ClientModInitializer {
    public static final ModelLayerLocation ILLAGER_BIPED = ArmorfulUtil.registerEntityModelLayer("illager_biped", IllagerBipedModel::createBodyLayer);
    public static final ModelLayerLocation ILLAGER_ARMOR_HEAD = ArmorfulUtil.registerEntityModelLayer("illager_armor_head", IllagerArmorModel::createHeadArmorLayer);
    public static final ModelLayerLocation ILLAGER_ARMOR_CHEST = ArmorfulUtil.registerEntityModelLayer("illager_armor_chest", IllagerArmorModel::createChestArmorLayer);
    public static final ModelLayerLocation ILLAGER_ARMOR_LEGS = ArmorfulUtil.registerEntityModelLayer("illager_armor_legs", IllagerArmorModel::createLegsArmorLayer);
    public static final ModelLayerLocation ILLAGER_ARMOR_FEET = ArmorfulUtil.registerEntityModelLayer("illager_armor_feet", IllagerArmorModel::createFeetArmorLayer);

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.PILLAGER, PillagerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.EVOKER, EvokerBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.VINDICATOR, VindicatorBipedRenderer::new);
        EntityRendererRegistry.register(EntityType.ILLUSIONER, IllusionerBipedRenderer::new);
    }
}
