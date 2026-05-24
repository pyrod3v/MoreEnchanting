package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.MoreEnchanting;
import gg.pyrod3v.more_enchanting.MoreEnchantingConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record SoulAnchorEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<SoulAnchorEnchantmentEffect> CODEC = MapCodec.unit(SoulAnchorEnchantmentEffect::new);

    public static final Identifier JUMP_STRENGTH_MODIFIER = MoreEnchanting.id("jump_strength_modifier");

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (!(user instanceof Player player) || player.isCreative() || player.isSpectator() || world.isClientSide()) return;
        var jumpStrength = player.getAttribute(Attributes.JUMP_STRENGTH);
        if (player.getHealth() <= MoreEnchantingConfig.CONFIG.soulAnchorHealthThreshold) {
            MobEffectInstance resistance = new MobEffectInstance(MobEffects.RESISTANCE, 10, 2, false, false, false);
            MobEffectInstance slowness = new MobEffectInstance(MobEffects.SLOWNESS, 10, 1, false, false, false);
            if (!jumpStrength.hasModifier(JUMP_STRENGTH_MODIFIER)) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 0.5F, 1.0F);
                jumpStrength.addTransientModifier(new AttributeModifier(JUMP_STRENGTH_MODIFIER, -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            }
            player.addEffect(resistance);
            player.addEffect(slowness);
        } else if (jumpStrength.hasModifier(JUMP_STRENGTH_MODIFIER)) {
            jumpStrength.removeModifier(JUMP_STRENGTH_MODIFIER);
        }
    }

    @Override
    public @NonNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
