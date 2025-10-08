package gg.pyro.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyro.more_enchanting.MoreEnchanting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class MoreEnchantingEnchantments {

    public static final RegistryKey<Enchantment> DOUBLE_JUMP = of("double_jump");
    public static final RegistryKey<Enchantment> ROOTED = of("rooted");
    public static final RegistryKey<Enchantment> LEECH = of("leech");

    public static final MapCodec<LeechEnchantmentEffect> ROOTED_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "rooted"), RootedEnchantmentEffect.CODEC);
    public static final MapCodec<LeechEnchantmentEffect> LEECH_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "leech"), LeechEnchantmentEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = MoreEnchanting.id(path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    public static void register() {
    }
}
