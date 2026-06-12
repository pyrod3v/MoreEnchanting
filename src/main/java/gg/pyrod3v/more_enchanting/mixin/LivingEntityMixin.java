package gg.pyrod3v.more_enchanting.mixin;

import gg.pyrod3v.more_enchanting.components.MoreEnchantingComponents;
import gg.pyrod3v.more_enchanting.enchantment.MoreEnchantingEnchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private final LivingEntity self = (LivingEntity) (Object) this;

    @Inject(method = "hurtServer", at = @At("TAIL"))
    private void detonate(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() || !(source.getEntity() instanceof Player player)) return;

        if (source.getWeaponItem() != null &&
                source.getWeaponItem().isEnchanted()) {
            boolean armorCrush = false, sharp = false;
            for (var entry : source.getWeaponItem().getEnchantments().entrySet()) {
                if (entry.getKey().is(MoreEnchantingEnchantments.ARMOR_CRUSH)) armorCrush = true;
                if (entry.getKey().is(Enchantments.SHARPNESS)) sharp = true;
            }

            if (armorCrush) {
                if (sharp) {
                    self.hurtArmor(source, damage / 3);
                } else if (source.getWeaponItem().tags().anyMatch(tag -> tag == ItemTags.MACE_ENCHANTABLE)) {
                    self.hurtArmor(source, damage);
                } else {
                    self.hurtArmor(source, damage / 2);
                }
            }
        }

        if (source.is(DamageTypes.ARROW)) {
            var comp = self.getComponent(MoreEnchantingComponents.MARKING_COMPONENT);

            if (comp.markedBy != null &&
                    comp.level != 0 &&
                    comp.markedBy.equalsIgnoreCase(player.getStringUUID())) {
                level.explode(
                        self, self.getX(), self.getY(), self.getZ(),
                        comp.level,
                        Level.ExplosionInteraction.MOB
                );
                comp.markedBy = null;
                comp.level = 0;
            }
        }
    }
}
