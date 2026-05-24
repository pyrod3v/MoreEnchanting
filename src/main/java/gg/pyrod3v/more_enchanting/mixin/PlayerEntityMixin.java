package gg.pyrod3v.more_enchanting.mixin;

import gg.pyrod3v.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

    @Shadow
    protected abstract boolean canCriticalAttack(Entity entity);

    @Unique
    private final Player self = (Player) (Object) this;

    @Inject(method = "attack", at = @At("HEAD"))
    private void updateHitData(Entity entity, CallbackInfo ci) {
        var data = self.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT);
        data.hits++;
        data.wasCrit = self.getAttackStrengthScale(0.5F) > 0.9F && canCriticalAttack(entity);
    }

    @Inject(method = "attack", at = @At("RETURN"))
    private void resetCritFlag(Entity entity, CallbackInfo ci) {
        self.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).wasCrit = false;
    }
}
