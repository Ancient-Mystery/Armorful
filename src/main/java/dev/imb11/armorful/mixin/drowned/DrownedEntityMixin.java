package dev.imb11.armorful.mixin.drowned;

import dev.imb11.armorful.util.ArmorfulUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Drowned.class)
public abstract class DrownedEntityMixin extends Zombie {
    protected DrownedEntityMixin(EntityType<? extends Zombie> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = false)
    public void initializeArmor(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData entityData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if(world instanceof ServerLevel) {
            if (spawnReason == EntitySpawnReason.STRUCTURE) return;
            ArmorfulUtil.giveArmorNaturally(this.random, this, difficulty);
        }
    }
}
