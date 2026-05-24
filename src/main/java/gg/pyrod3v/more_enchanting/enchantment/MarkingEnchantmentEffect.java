package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.components.MoreEnchantingComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record MarkingEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<MarkingEnchantmentEffect> CODEC = MapCodec.unit(MarkingEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity target, Vec3 pos) {
        if (context.owner() == null || !(context.owner() instanceof Player player) || world.isClientSide()) return;
        if (target instanceof LivingEntity livingEntity) {
            livingEntity.getComponent(MoreEnchantingComponents.MARKING_COMPONENT).markedBy = player.getStringUUID().toLowerCase();
            livingEntity.getComponent(MoreEnchantingComponents.MARKING_COMPONENT).level = level;
        }
    }

    @Override
    public @NonNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
