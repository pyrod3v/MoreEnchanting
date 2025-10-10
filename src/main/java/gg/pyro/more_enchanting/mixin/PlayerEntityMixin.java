package gg.pyro.more_enchanting.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gg.pyro.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Unique
    private final PlayerEntity self = (PlayerEntity) (Object) this;

    @WrapOperation(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/Entity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"
            )
    )
    private void onPlaySound(World world, Entity entity, double x, double y, double z,
                             SoundEvent sound, SoundCategory category, float volume, float pitch,
                             Operation<Void> original, Entity target) {
        if (sound == SoundEvents.ENTITY_PLAYER_ATTACK_CRIT) {
            self.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).wasCrit = true;
        }

        original.call(world, entity, x, y, z, sound, category, volume, pitch);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void countHits(Entity target, CallbackInfo ci) {
        self.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).hits++;
    }

    @Inject(method = "attack", at = @At("RETURN"))
    private void resetCritFlag(Entity target, CallbackInfo ci) {
        self.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).wasCrit = false;
    }
}
