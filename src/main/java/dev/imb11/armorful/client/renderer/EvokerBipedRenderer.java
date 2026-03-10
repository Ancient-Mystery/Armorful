package dev.imb11.armorful.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.imb11.armorful.client.model.IllagerBipedModel;
import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.EvokerRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;

public class EvokerBipedRenderer<T extends SpellcasterIllager> extends IllagerBipedRenderer<T, EvokerRenderState> {
    private static final Identifier TEXTURE = ArmorfulUtil.defaultID("textures/entity/illager/evoker.png");

    public EvokerBipedRenderer(Context context) {
        super(context);
        this.addLayer(new ItemInHandLayer<EvokerRenderState, IllagerBipedModel<EvokerRenderState>>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light,
                               EvokerRenderState state, float partialTick, float bob) {
                if (state.isAggressive || state.isCastingSpell) {
                    super.submit(poseStack, collector, light, state, partialTick, bob);
                }
            }
        });
    }

    @Override
    public Identifier getTextureLocation(EvokerRenderState state) {
        return TEXTURE;
    }

    @Override
    public EvokerRenderState createRenderState() {
        return new EvokerRenderState();
    }

    @Override
    public void extractRenderState(T entity, EvokerRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isCastingSpell = entity.isCastingSpell();
    }
}
