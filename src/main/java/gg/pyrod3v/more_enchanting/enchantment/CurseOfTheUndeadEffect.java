package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.effect.MoreEnchantingEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record CurseOfTheUndeadEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<CurseOfTheUndeadEffect> CODEC = MapCodec.unit(CurseOfTheUndeadEffect::new);

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (!(user instanceof Player player) || player.isCreative() || player.isSpectator() || world.isClientSide() || player.isUnderWater()) return;
        if (world.canSeeSky(player.getOnPos().above())) {
            if (world.isBrightOutside()) {
                ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                if (head.isEmpty() || (double) head.getDamageValue() / head.getMaxDamage() > 0.66) {
                    player.addEffect(new MobEffectInstance(MoreEnchantingEffects.DECOMPOSITION, 20, 0, false, true, false));
                }
                player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 20, 0, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0, false, false, false));
            } else {
                player.addEffect(new MobEffectInstance(MobEffects.SPEED, 20, 0, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 20, 0, false, false, false));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
