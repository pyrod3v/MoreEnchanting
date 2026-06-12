package gg.pyrod3v.more_enchanting.effect;

import gg.pyrod3v.more_enchanting.MoreEnchantingConfig;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DecompositionEffect extends MobEffect {

    protected DecompositionEffect() {
        int color = 0x911f0f;
        super(MobEffectCategory.HARMFUL, color, ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, ARGB.color(120, color)));
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (level.getGameTime() % 20 != 0 || MoreEnchantingConfig.CONFIG.decompositionDamageChance == 0) {
            return super.applyEffectTick(level, entity, amplifier);
        }

        float chance = Math.min(MoreEnchantingConfig.CONFIG.decompositionDamageChance + amplifier * 4, 100);
        if (entity.getRandom().nextFloat() * 100f <= chance) {
            entity.hurtServer(level, entity.damageSources().dryOut(), MoreEnchantingConfig.CONFIG.decompositionDamage);
        }

        if (entity.getRandom().nextFloat() * 80f <= chance) {
            entity.igniteForTicks((entity.getRandom().nextInt(75, 125) / 100) * MoreEnchantingConfig.CONFIG.decompositionFireTicks);
        }

        return super.applyEffectTick(level, entity, amplifier);
    }
}
