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

    public static final RegistryKey<Enchantment> DOUBLE_JUMP = register("double_jump");
    public static final RegistryKey<Enchantment> ROOTED = register("rooted");
    public static final RegistryKey<Enchantment> SOUL_ANCHOR = register("soul_anchor");
    public static final RegistryKey<Enchantment> DESPERATION = register("desperation");
    public static final RegistryKey<Enchantment> LEECH = register("leech");
    public static final RegistryKey<Enchantment> MOMENTUM = register("momentum");
    public static final RegistryKey<Enchantment> MARKING = register("marking");
    public static final RegistryKey<Enchantment> FROST_ASPECT = register("frost_aspect");

    public static final MapCodec<RootedEnchantmentEffect> ROOTED_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "rooted"), RootedEnchantmentEffect.CODEC);
    public static final MapCodec<SoulAnchorEnchantmentEffect> SOUL_ANCHOR_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "soul_anchor"), SoulAnchorEnchantmentEffect.CODEC);
    public static final MapCodec<DesperationEnchantmentEffect> DESPERATION_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "desperation"), DesperationEnchantmentEffect.CODEC);
    public static final MapCodec<LeechEnchantmentEffect> LEECH_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "leech"), LeechEnchantmentEffect.CODEC);
    public static final MapCodec<MomentumEnchantmentEffect> MOMENTUM_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "momentum"), MomentumEnchantmentEffect.CODEC);
    public static final MapCodec<MarkingEnchantmentEffect> MARKING_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "marking"), MarkingEnchantmentEffect.CODEC);
    public static final MapCodec<FrostAspectEnchantmentEffect> FROST_ASPECT_EFFECT =
            Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MoreEnchanting.MOD_ID, "frost_aspect"), FrostAspectEnchantmentEffect.CODEC);

    private static RegistryKey<Enchantment> register(String path) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, MoreEnchanting.id(path));
    }

    public static void register() {
    }
}
