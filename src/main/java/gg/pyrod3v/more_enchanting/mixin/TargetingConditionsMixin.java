package gg.pyrod3v.more_enchanting.mixin;

import gg.pyrod3v.more_enchanting.enchantment.MoreEnchantingEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetingConditions.class)
public abstract class TargetingConditionsMixin {

    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    private void curseOfTheUndeadSkip(ServerLevel level, LivingEntity targeter, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (targeter.tags().anyMatch(tag -> tag == EntityTypeTags.UNDEAD)) {
            if (target instanceof Player player) {
                Holder<Enchantment> holder = level.registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(MoreEnchantingEnchantments.CURSE_OF_THE_UNDEAD);
                if (EnchantmentHelper.getEnchantmentLevel(holder, player) > 0) {
                    if (targeter.getLastAttacker() == null) {
                        cir.setReturnValue(false);
                    } else if (!targeter.getLastAttacker().is(player)) {
                        cir.setReturnValue(false);
                    }
                }
            }

        }
    }
}
