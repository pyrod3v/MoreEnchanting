package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyro.more_enchanting.MoreEnchanting;
import gg.pyro.more_enchanting.MoreEnchantingConfig;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record DesperationEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<DesperationEnchantmentEffect> CODEC = MapCodec.unit(DesperationEnchantmentEffect::new);

    public static final Identifier DAMAGE_BOOST = MoreEnchanting.id("desperation_damage_boost");

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!(user instanceof PlayerEntity player) || player.isInCreativeMode() || player.isSpectator() || world.isClient()) return;
        var attackDamage = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (player.getHealth() <= MoreEnchantingConfig.CONFIG.desperationHealthThreshold && !attackDamage.hasModifier(DAMAGE_BOOST)) {
            player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.5F, 1.0F);
            attackDamage.addTemporaryModifier(new EntityAttributeModifier(DAMAGE_BOOST, 0.08 * level, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else if (player.getHealth() > MoreEnchantingConfig.CONFIG.desperationHealthThreshold &&attackDamage.hasModifier(DAMAGE_BOOST)) {
            attackDamage.removeModifier(DAMAGE_BOOST);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}