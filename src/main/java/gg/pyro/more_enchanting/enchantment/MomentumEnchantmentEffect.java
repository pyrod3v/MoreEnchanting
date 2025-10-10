package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyro.more_enchanting.MoreEnchanting;
import gg.pyro.more_enchanting.MoreEnchantingConfig;
import gg.pyro.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record MomentumEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<MomentumEnchantmentEffect> CODEC = MapCodec.unit(MomentumEnchantmentEffect::new);

    private static final Identifier MOMENTUM_BOOST = MoreEnchanting.id("momentum_enchantment_attack_speed_boost");

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!(user instanceof PlayerEntity player)) return;

        var attackSpeed = player.getAttributeInstance(EntityAttributes.ATTACK_SPEED);
        double time = MoreEnchantingConfig.CONFIG.momentumBaseTimer
                + (MoreEnchantingConfig.CONFIG.momentumSecondaryTimer
                / player.getAttributeValue(EntityAttributes.ATTACK_SPEED)
                + MoreEnchantingConfig.CONFIG.momentumPerLevelTimer * level);
        int attackTime = player.age - player.getLastAttackTime();
        final int hits = player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).hits;

        if (!attackSpeed.hasModifier(MOMENTUM_BOOST) &&
                attackTime < time &&
                hits > 2) {
            double boost =  Math.min(1, hits / (7.5 - level));
            attackSpeed.addPersistentModifier(new EntityAttributeModifier(MOMENTUM_BOOST, boost, EntityAttributeModifier.Operation.ADD_VALUE));
            player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).lastAttackSpeedBoost = boost;
        } else if (attackTime >= time) {
            if (attackSpeed.hasModifier(MOMENTUM_BOOST)) {
                attackSpeed.removeModifier(MOMENTUM_BOOST);
            }
            player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).hits = 0;
        } else if (attackTime >= time / 2 && hits == 0 && attackSpeed.hasModifier(MOMENTUM_BOOST)) {
            attackSpeed.removeModifier(MOMENTUM_BOOST);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
