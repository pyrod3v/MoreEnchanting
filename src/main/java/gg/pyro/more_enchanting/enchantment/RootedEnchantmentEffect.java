package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record RootedEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<RootedEnchantmentEffect> CODEC = MapCodec.unit(RootedEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user instanceof PlayerEntity player) {
            ItemStack stack = player.getEquippedStack(EquipmentSlot.LEGS);
            if (player.isSneaking() && stack.hasEnchantments() &&
                    stack.getEnchantments().getEnchantmentEntries().stream()
                            .anyMatch(entry -> entry.getKey().getKey().isPresent() &&
                                    entry.getKey().getKey().get().equals(MoreEnchantingEnchantments.ROOTED))) {
                StatusEffectInstance regeneration = new StatusEffectInstance(StatusEffects.REGENERATION, 1, level, false, false, false);
                StatusEffectInstance resistance = new StatusEffectInstance(StatusEffects.RESISTANCE, 1, level, false, false, false);
                player.addStatusEffect(regeneration);
                player.addStatusEffect(resistance);
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
