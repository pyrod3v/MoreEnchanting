package gg.pyro.more_enchanting.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gg.pyro.more_enchanting.access.CriticalHitTracker;
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
public class PlayerEntityMixin implements CriticalHitTracker {

    @Unique
    private boolean moreEnchanting$wasCrit = false;

    @Override
    public boolean moreEnchanting$wasCrit() {
        return moreEnchanting$wasCrit;
    }

    @Override
    public void moreEnchanting$setCrit(boolean value) {
        moreEnchanting$wasCrit = value;
    }

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
            System.out.println("crit detected!");
            this.moreEnchanting$setCrit(true);
        }

        original.call(world, entity, x, y, z, sound, category, volume, pitch);
    }

    @Inject(method = "attack", at = @At("RETURN"))
    private void resetCritFlag(Entity target, CallbackInfo ci) {
        this.moreEnchanting$setCrit(false);
    }
}
