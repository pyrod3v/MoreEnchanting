package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.MoreEnchanting;
import gg.pyrod3v.more_enchanting.MoreEnchantingConfig;
import gg.pyrod3v.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record MomentumEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<MomentumEnchantmentEffect> CODEC = MapCodec.unit(MomentumEnchantmentEffect::new);

    public static final Identifier MOMENTUM_BOOST = MoreEnchanting.id("momentum_enchantment_attack_speed_boost");

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (!(user instanceof Player player) || world.isClientSide()) return;

        var attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
        double time = MoreEnchantingConfig.CONFIG.momentumBaseTimer
                + (MoreEnchantingConfig.CONFIG.momentumSecondaryTimer
                / player.getAttributeValue(Attributes.ATTACK_SPEED)
                + MoreEnchantingConfig.CONFIG.momentumPerLevelTimer * level);
        int attackTime = player.tickCount - player.getLastHurtMobTimestamp();
        final int hits = player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).hits;

        if (!attackSpeed.hasModifier(MOMENTUM_BOOST) &&
                attackTime < time &&
                hits > 2) {
            double boost =  Math.min(1, hits / (7.5 - level));
            attackSpeed.addTransientModifier(new AttributeModifier(MOMENTUM_BOOST, boost, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
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
    public @NonNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
