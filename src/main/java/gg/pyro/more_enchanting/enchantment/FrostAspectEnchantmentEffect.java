package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyro.more_enchanting.MoreEnchantingConfig;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record FrostAspectEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<FrostAspectEnchantmentEffect> CODEC = MapCodec.unit(FrostAspectEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        target.setFrozenTicks(MoreEnchantingConfig.CONFIG.frostAspectBaseFrozenTicks + (MoreEnchantingConfig.CONFIG.frostAspectFrozenTicksPerLevel * level));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
