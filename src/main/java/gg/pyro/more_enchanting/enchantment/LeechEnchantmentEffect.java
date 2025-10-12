package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyro.more_enchanting.MoreEnchantingConfig;
import gg.pyro.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public record LeechEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<LeechEnchantmentEffect> CODEC = MapCodec.unit(LeechEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (context.owner() == null || !(context.owner() instanceof PlayerEntity player) || world.isClient()) return;
        if (target instanceof LivingEntity) {
            if (MoreEnchantingConfig.CONFIG.leechAlwaysHeal || new Random().nextBoolean()
                    && player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).wasCrit) {
                player.heal(MoreEnchantingConfig.CONFIG.leechBaseHeal + MoreEnchantingConfig.CONFIG.leechHealPerLevel * level);
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}