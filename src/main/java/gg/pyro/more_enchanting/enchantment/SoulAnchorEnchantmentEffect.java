package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.Keyable;
import com.mojang.serialization.MapCodec;
import gg.pyro.more_enchanting.MoreEnchanting;
import gg.pyro.more_enchanting.MoreEnchantingConfig;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record SoulAnchorEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<SoulAnchorEnchantmentEffect> CODEC = MapCodec.unit(SoulAnchorEnchantmentEffect::new);

    private static final Identifier JUMP_STRENGTH_MODIFIER = MoreEnchanting.id("jump_strength_modifier");

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!(user instanceof PlayerEntity player) || world.isClient()) return;
        var jumpStrength = player.getAttributeInstance(EntityAttributes.JUMP_STRENGTH);
        if (player.getHealth() < MoreEnchantingConfig.CONFIG.soulAnchorHealthThreshold) {
            StatusEffectInstance resistance = new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 2, false, false, false);
            StatusEffectInstance slowness = new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 2, false, false, false);
            if (!jumpStrength.hasModifier(JUMP_STRENGTH_MODIFIER)) {
                jumpStrength.addPersistentModifier(new EntityAttributeModifier(JUMP_STRENGTH_MODIFIER, -0.7, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
            player.addStatusEffect(resistance);
            player.addStatusEffect(slowness);
        } else if (jumpStrength.hasModifier(JUMP_STRENGTH_MODIFIER)) {
            jumpStrength.removeModifier(JUMP_STRENGTH_MODIFIER);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
