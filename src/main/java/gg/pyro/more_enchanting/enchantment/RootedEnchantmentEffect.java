package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public record RootedEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<RootedEnchantmentEffect> CODEC = MapCodec.unit(RootedEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!(user instanceof PlayerEntity player) || world.isClient()) return;
        player.sendMessage(Text.of(String.valueOf(level)), true);
        if (player.isSneaking()) {
            StatusEffectInstance regeneration = new StatusEffectInstance(StatusEffects.REGENERATION, 10, --level, false, false, false);
            StatusEffectInstance resistance = new StatusEffectInstance(StatusEffects.RESISTANCE, 10, --level, false, false, false);
            player.addStatusEffect(regeneration);
            player.addStatusEffect(resistance);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
