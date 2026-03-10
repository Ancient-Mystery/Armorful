package dev.imb11.armorful.client.renderer;

import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.Pillager;
import org.jetbrains.annotations.NotNull;

public class PillagerBipedRenderer extends IllagerBipedRenderer<Pillager, IllagerRenderState> {
    private static final Identifier TEXTURE = ArmorfulUtil.defaultID("textures/entity/illager/pillager.png");

    public PillagerBipedRenderer(Context context) {
        super(context);
        this.addLayer(new ItemInHandLayer<>(this));
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
