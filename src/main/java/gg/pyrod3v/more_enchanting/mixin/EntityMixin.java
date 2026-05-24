package gg.pyrod3v.more_enchanting.mixin;

import gg.pyrod3v.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "checkFallDamage", at = @At("TAIL"))
    private void onFall(double ya, boolean onGround, BlockState onState, BlockPos pos, CallbackInfo ci) {
        if ((Object) this instanceof Player player && onGround) {
            player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).canDoubleJump = true;
        }
    }
}
