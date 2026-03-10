package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.IllusionerRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IllusionerBipedRenderer extends IllagerBipedRenderer<Illusioner, IllusionerRenderState> {
    private static final Identifier TEXTURE = ArmorfulUtil.defaultID("textures/entity/illager/illusioner.png");

    public IllusionerBipedRenderer(Context context) {
        super(context);
        this.addLayer(new ItemInHandLayer<IllusionerRenderState, IllagerBipedModel<IllusionerRenderState>>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light,
                               IllusionerRenderState state, float partialTick, float bob) {
                if (state.isCastingSpell || state.isAggressive) {
                    super.submit(poseStack, collector, light, state, partialTick, bob);
                }
            }
        });
    }

    @Override
    public void submit(IllusionerRenderState state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        if (state.isInvisible) {
            Vec3[] offsets = state.illusionOffsets;
            for (int i = 0; i < offsets.length; i++) {
                poseStack.pushPose();
                poseStack.translate(
                        offsets[i].x + Mth.cos((float) i + state.ageInTicks * 0.5F) * 0.025,
                        offsets[i].y + Mth.cos((float) i + state.ageInTicks * 0.75F) * 0.0125,
                        offsets[i].z + Mth.cos((float) i + state.ageInTicks * 0.7F) * 0.025
                );
                super.submit(state, poseStack, collector, cameraRenderState);
                poseStack.popPose();
            }
        } else {
            super.submit(state, poseStack, collector, cameraRenderState);
        }
    }

    @Override
    protected boolean isBodyVisible(IllusionerRenderState state) {
        return true;
    }

    @Override
    protected AABB getBoundingBoxForCulling(Illusioner entity) {
        return super.getBoundingBoxForCulling(entity).inflate(3.0, 0.0, 3.0);
    }

    @Override
    public IllusionerRenderState createRenderState() {
        return new IllusionerRenderState();
    }

    @Override
    public void extractRenderState(Illusioner entity, IllusionerRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        Vec3[] offsets = entity.getIllusionOffsets(partialTick);
        state.illusionOffsets = Arrays.copyOf(offsets, offsets.length);
        state.isCastingSpell = entity.isCastingSpell();
    }

    @Override
    public @NotNull Identifier getTextureLocation(IllusionerRenderState state) {
        return TEXTURE;
    }
}
