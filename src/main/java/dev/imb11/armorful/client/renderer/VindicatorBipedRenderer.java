package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.Vindicator;
import org.jetbrains.annotations.NotNull;

public class VindicatorBipedRenderer extends IllagerBipedRenderer<Vindicator, IllagerRenderState> {
    private static final Identifier TEXTURE = ArmorfulUtil.defaultID("textures/entity/illager/vindicator.png");

    public VindicatorBipedRenderer(Context context) {
        super(context);
        this.addLayer(new ItemInHandLayer<IllagerRenderState, IllagerBipedModel<IllagerRenderState>>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light,
                               IllagerRenderState state, float partialTick, float bob) {
                if (state.isAggressive) {
                    super.submit(poseStack, collector, light, state, partialTick, bob);
                }
            }
        });
    }

    @Override
    public @NotNull Identifier getTextureLocation(IllagerRenderState state) {
        return TEXTURE;
    }

    @Override
    public IllagerRenderState createRenderState() {
        return new IllagerRenderState();
    }
}
