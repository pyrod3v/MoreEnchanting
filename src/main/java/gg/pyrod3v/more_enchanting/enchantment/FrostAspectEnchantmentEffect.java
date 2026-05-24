package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.MoreEnchantingConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record FrostAspectEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<FrostAspectEnchantmentEffect> CODEC = MapCodec.unit(FrostAspectEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity target, Vec3 pos) {
        target.setTicksFrozen(MoreEnchantingConfig.CONFIG.frostAspectBaseFrozenTicks + (MoreEnchantingConfig.CONFIG.frostAspectFrozenTicksPerLevel * level));
    }

    @Override
    public @NonNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
