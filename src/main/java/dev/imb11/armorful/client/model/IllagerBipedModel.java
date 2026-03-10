package dev.imb11.armorful.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
public class IllagerBipedModel<S extends IllagerRenderState> extends HumanoidModel<S> {
    public ModelPart nose;
    public ModelPart jacket;
    public ModelPart arms;

    public IllagerBipedModel(ModelPart part) {
        super(part);
        this.jacket = this.body.getChild("jacket");
        this.nose = this.head.getChild("nose");
        this.arms = part.getChild("arms");
        this.hat.visible = false;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition model = modelData.getRoot();

        PartDefinition headModel = model.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        headModel.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F,
                8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);

        headModel.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition bodyModel = model.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bodyModel.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F,
                6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armsModel = model.addOrReplaceChild("arms",
                CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38)
                        .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));

        armsModel.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                PartPose.ZERO);

        model.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-2.0F, 12.0F, 0.0F));

        model.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(2.0F, 12.0F, 0.0F));

        model.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        model.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(S state) {
        super.setupAnim(state);

        AbstractIllager.IllagerArmPose currentArmPose = state.armPose;
        boolean isArmsCrossed = currentArmPose == AbstractIllager.IllagerArmPose.CROSSED;

        this.arms.visible = isArmsCrossed;
        this.leftArm.visible = !isArmsCrossed;
        this.rightArm.visible = !isArmsCrossed;

        if (isArmsCrossed) {
            this.leftArm.y = 3.0F;
            this.leftArm.z = -1.0F;
            this.leftArm.xRot = -0.75F;
            this.rightArm.y = 3.0F;
            this.rightArm.z = -1.0F;
            this.rightArm.xRot = -0.75F;
        }

        this.jacket.loadPose(this.body.storePose());

        boolean isWearingChestplateOrLeggings =
                (state.chestEquipment != null && !state.chestEquipment.isEmpty())
                || (state.legsEquipment != null && !state.legsEquipment.isEmpty());

        this.jacket.visible = !isWearingChestplateOrLeggings;
        this.arms.y = 3.0F;
        this.arms.z = -1.0F;
        this.arms.xRot = -0.75F;

        switch (currentArmPose) {
            case ATTACKING:
                if (state.getMainHandItemStack() == null || state.getMainHandItemStack().isEmpty()) {
                    AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, state);
                } else {
                    AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, state.mainArm, state.attackAnim, 0);
                }
                break;
            case SPELLCASTING:
                this.rightArm.z = 0.0F;
                this.rightArm.x = -5.0F;
                this.leftArm.z = 0.0F;
                this.leftArm.x = 5.0F;
                this.rightArm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 0.25F;
                this.leftArm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 0.25F;
                this.rightArm.zRot = 2.3561945F;
                this.leftArm.zRot = -2.3561945F;
                this.rightArm.yRot = 0.0F;
                this.leftArm.yRot = 0.0F;
                break;
            case CELEBRATING:
                this.rightArm.z = 0.0F;
                this.rightArm.x = -5.0F;
                this.rightArm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 0.05F;
                this.rightArm.zRot = 2.670354F;
                this.rightArm.yRot = 0.0F;
                this.leftArm.z = 0.0F;
                this.leftArm.x = 5.0F;
                this.leftArm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 0.05F;
                this.leftArm.zRot = -2.3561945F;
                this.leftArm.yRot = 0.0F;
                break;
            default:
                break;
        }
    }
}
