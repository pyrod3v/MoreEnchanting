package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record RootedEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<RootedEnchantmentEffect> CODEC = MapCodec.unit(RootedEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (!(user instanceof Player player) || player.isCreative() || world.isClientSide()) return;
        if (player.isShiftKeyDown()) {
            MobEffectInstance regeneration = new MobEffectInstance(MobEffects.REGENERATION, 10, --level, false, false, false);
            MobEffectInstance resistance = new MobEffectInstance(MobEffects.RESISTANCE, 10, --level, false, false, false);
            player.addEffect(regeneration);
            player.addEffect(resistance);
        }
    }

    @Override
    public @NonNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
