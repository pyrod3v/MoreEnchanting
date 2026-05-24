package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.MoreEnchantingConfig;
import gg.pyrod3v.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public record LeechEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<LeechEnchantmentEffect> CODEC = MapCodec.unit(LeechEnchantmentEffect::new);
    private static final Random RANDOM = new Random();

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity target, Vec3 pos) {
        if (context.owner() == null || !(context.owner() instanceof Player player) || world.isClientSide()) return;
        if (target instanceof LivingEntity) {
            if (RANDOM.nextFloat() <= MoreEnchantingConfig.CONFIG.leechHealChance
                    && player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).wasCrit) {
                player.heal(MoreEnchantingConfig.CONFIG.leechBaseHeal + MoreEnchantingConfig.CONFIG.leechHealPerLevel * level);
            }
        }
    }

    @Override
    public @NonNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}