package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.MoreEnchanting;
import gg.pyrod3v.more_enchanting.MoreEnchantingConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record DesperationEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<DesperationEnchantmentEffect> CODEC = MapCodec.unit(DesperationEnchantmentEffect::new);

    public static final Identifier DAMAGE_BOOST = MoreEnchanting.id("desperation_damage_boost");

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (!(user instanceof Player player) || player.isCreative() || player.isSpectator() || world.isClientSide()) return;
        var attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (player.getHealth() <= MoreEnchantingConfig.CONFIG.desperationHealthThreshold && !attackDamage.hasModifier(DAMAGE_BOOST)) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),  SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 0.5F, 1.0F);
            attackDamage.addTransientModifier(new AttributeModifier(DAMAGE_BOOST, 0.08 * level, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else if (player.getHealth() > MoreEnchantingConfig.CONFIG.desperationHealthThreshold &&attackDamage.hasModifier(DAMAGE_BOOST)) {
            attackDamage.removeModifier(DAMAGE_BOOST);
        }
    }

    @Override
    public @NonNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}