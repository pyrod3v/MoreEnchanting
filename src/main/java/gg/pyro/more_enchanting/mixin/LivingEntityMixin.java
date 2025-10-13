package gg.pyro.more_enchanting.mixin;

import gg.pyro.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private final LivingEntity self = (LivingEntity) (Object) this;

    @Inject(method = "damage", at = @At("TAIL"))
    private void detonate(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(DamageTypes.ARROW) &&
               source.getAttacker() != null &&
               source.getAttacker() instanceof PlayerEntity player) {
            var comp = self.getComponent(MoreEnchantingComponents.MARKING_COMPONENT);

            if (comp.markedBy != null &&
                    self.getComponent(MoreEnchantingComponents.MARKING_COMPONENT).level != 0 &&
                    comp.markedBy.equalsIgnoreCase(player.getUuidAsString())) {
                world.createExplosion(
                        self, self.getX(), self.getY(), self.getZ(),
                        comp.level,
                        World.ExplosionSourceType.MOB
                );
                comp.markedBy = null;
                comp.level = 0;
            }
        }
    }
}
