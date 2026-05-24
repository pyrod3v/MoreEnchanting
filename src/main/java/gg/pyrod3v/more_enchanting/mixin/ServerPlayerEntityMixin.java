package gg.pyrod3v.more_enchanting.mixin;

import gg.pyrod3v.more_enchanting.enchantment.DesperationEnchantmentEffect;
import gg.pyrod3v.more_enchanting.enchantment.MomentumEnchantmentEffect;
import gg.pyrod3v.more_enchanting.enchantment.MoreEnchantingEnchantments;
import gg.pyrod3v.more_enchanting.enchantment.SoulAnchorEnchantmentEffect;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin {

    @Unique
    private final ServerPlayer self = (ServerPlayer) (Object) this;

    @Inject(method = "tick", at = @At("HEAD"))
    private void cleanupAttributes(CallbackInfo ci) {
        if (self.getInventory().isEmpty()) return;

        for (ItemStack stack : self.getInventory()) {
            if (isEquipped(stack) || !stack.isEnchanted()) continue;
            for (var entry : stack.getEnchantments().entrySet()) {
                entry.getKey().unwrapKey().ifPresent(key -> {
                    if (key.equals(MoreEnchantingEnchantments.SOUL_ANCHOR)) removeModifierIfPresent(self, Attributes.JUMP_STRENGTH, SoulAnchorEnchantmentEffect.JUMP_STRENGTH_MODIFIER);
                    if (key.equals(MoreEnchantingEnchantments.DESPERATION)) removeModifierIfPresent(self, Attributes.ATTACK_DAMAGE, DesperationEnchantmentEffect.DAMAGE_BOOST);
                    if (key.equals(MoreEnchantingEnchantments.MOMENTUM)) removeModifierIfPresent(self, Attributes.ATTACK_SPEED, MomentumEnchantmentEffect.MOMENTUM_BOOST);
                });
            }
        }
    }

    @Unique
    private boolean isEquipped(ItemStack stack) {
        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            ItemStack equipped = self.getItemBySlot(slot);
            if (!equipped.isEmpty() && ItemStack.isSameItem(stack, equipped)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private static void removeModifierIfPresent(Player player, Holder<Attribute> attribute, Identifier id) {
        var instance = player.getAttribute(attribute);
        if (instance != null && instance.hasModifier(id)) {
            instance.removeModifier(id);
        }
    }
}
