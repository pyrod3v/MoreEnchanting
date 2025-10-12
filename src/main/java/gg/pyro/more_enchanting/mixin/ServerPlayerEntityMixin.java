package gg.pyro.more_enchanting.mixin;

import gg.pyro.more_enchanting.components.MoreEnchantingComponents;
import gg.pyro.more_enchanting.enchantment.DesperationEnchantmentEffect;
import gg.pyro.more_enchanting.enchantment.MomentumEnchantmentEffect;
import gg.pyro.more_enchanting.enchantment.MoreEnchantingEnchantments;
import gg.pyro.more_enchanting.enchantment.SoulAnchorEnchantmentEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Unique
    private final ServerPlayerEntity self = (ServerPlayerEntity) (Object) this;

    @Inject(method = "attack", at = @At("HEAD"))
    private void countHits(Entity target, CallbackInfo ci) {
        self.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).hits++;
    }

    @Inject(method = "attack", at = @At("RETURN"))
    private void resetCritFlag(Entity target, CallbackInfo ci) {
        self.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).wasCrit = false;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void cleanupAttributes(CallbackInfo ci) {
        if (self.getInventory().isEmpty()) return;

        for (ItemStack stack : self.getInventory()) {
            if (isEquipped(stack) || !stack.hasEnchantments()) continue;
            for (var entry : stack.getEnchantments().getEnchantmentEntries()) {
                entry.getKey().getKey().ifPresent(key -> {
                    if (key.equals(MoreEnchantingEnchantments.SOUL_ANCHOR)) removeModifierIfPresent(self, EntityAttributes.JUMP_STRENGTH, SoulAnchorEnchantmentEffect.JUMP_STRENGTH_MODIFIER);
                    if (key.equals(MoreEnchantingEnchantments.DESPERATION)) removeModifierIfPresent(self, EntityAttributes.ATTACK_DAMAGE, DesperationEnchantmentEffect.DAMAGE_BOOST);
                    if (key.equals(MoreEnchantingEnchantments.MOMENTUM)) removeModifierIfPresent(self, EntityAttributes.ATTACK_SPEED, MomentumEnchantmentEffect.MOMENTUM_BOOST);
                });
            }
        }
    }

    @Unique
    private boolean isEquipped(ItemStack stack) {
        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            ItemStack equipped = self.getEquippedStack(slot);
            if (!equipped.isEmpty() && ItemStack.areEqual(stack, equipped)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private static void removeModifierIfPresent(PlayerEntity player, RegistryEntry<EntityAttribute> attribute, Identifier id) {
        var instance = player.getAttributeInstance(attribute);
        if (instance != null && instance.hasModifier(id)) {
            instance.removeModifier(id);
        }
    }
}
