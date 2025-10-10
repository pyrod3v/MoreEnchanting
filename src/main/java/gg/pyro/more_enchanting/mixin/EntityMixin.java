package gg.pyro.more_enchanting.mixin;

import gg.pyro.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "fall", at = @At("TAIL"))
    private void onFall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci) {
        if ((Object) this instanceof PlayerEntity player && onGround) {
            player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).canDoubleJump = true;
        }
    }
}
