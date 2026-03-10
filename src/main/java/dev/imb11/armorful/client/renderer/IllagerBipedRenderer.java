package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.imb11.armorful.client.ArmorfulClient;
import dev.imb11.armorful.client.model.IllagerArmorModel;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.item.CrossbowItem;

public abstract class IllagerBipedRenderer<T extends AbstractIllager, S extends IllagerRenderState>
        extends MobRenderer<T, S, IllagerBipedModel<S>> {

    public IllagerBipedRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerBipedModel<>(context.bakeLayer(ArmorfulClient.ILLAGER_BIPED)), 0.5F);

        ArmorModelSet<IllagerArmorModel<S>> armorModels = ArmorModelSet.bake(
                new ArmorModelSet<>(
                        ArmorfulClient.ILLAGER_ARMOR_HEAD,
                        ArmorfulClient.ILLAGER_ARMOR_CHEST,
                        ArmorfulClient.ILLAGER_ARMOR_LEGS,
                        ArmorfulClient.ILLAGER_ARMOR_FEET
                ),
                context.getModelSet(),
                IllagerArmorModel::new
        );
        this.addLayer(new HumanoidArmorLayer<>(this, armorModels, context.getEquipmentRenderer()));
    }

    @Override
    protected void scale(S state, PoseStack poseStack) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public void extractRenderState(T entity, S state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        HumanoidMobRenderer.extractHumanoidRenderState(entity, state, partialTick, this.itemModelResolver);

        state.isRiding = entity.isPassenger();
        state.mainArm = entity.getMainArm();
        state.armPose = entity.getArmPose();
        state.maxCrossbowChargeDuration = (state.armPose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE)
                ? CrossbowItem.getChargeDuration(entity.getUseItem(), entity) : 0;
        state.ticksUsingItem = entity.getTicksUsingItem(partialTick);
        state.attackAnim = entity.getAttackAnim(partialTick);
        state.isAggressive = entity.isAggressive();
    }
}
