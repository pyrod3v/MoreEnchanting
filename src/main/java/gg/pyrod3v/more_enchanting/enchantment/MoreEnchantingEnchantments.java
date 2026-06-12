package gg.pyrod3v.more_enchanting.enchantment;

import com.mojang.serialization.MapCodec;
import gg.pyrod3v.more_enchanting.MoreEnchanting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class MoreEnchantingEnchantments {

    public static final ResourceKey<Enchantment> DOUBLE_JUMP = register("double_jump");
    public static final ResourceKey<Enchantment> ROOTED = register("rooted");
    public static final ResourceKey<Enchantment> SOUL_ANCHOR = register("soul_anchor");
    public static final ResourceKey<Enchantment> DESPERATION = register("desperation");
    public static final ResourceKey<Enchantment> LEECH = register("leech");
    public static final ResourceKey<Enchantment> MOMENTUM = register("momentum");
    public static final ResourceKey<Enchantment> MARKING = register("marking");
    public static final ResourceKey<Enchantment> FROST_ASPECT = register("frost_aspect");

    public static final ResourceKey<Enchantment> CURSE_OF_THE_UNDEAD = register("curse_of_the_undead");

    public static final MapCodec<RootedEnchantmentEffect> ROOTED_EFFECT = register("rooted", RootedEnchantmentEffect.CODEC);
    public static final MapCodec<SoulAnchorEnchantmentEffect> SOUL_ANCHOR_EFFECT = register("soul_anchor", SoulAnchorEnchantmentEffect.CODEC);
    public static final MapCodec<DesperationEnchantmentEffect> DESPERATION_EFFECT = register("desperation", DesperationEnchantmentEffect.CODEC);
    public static final MapCodec<LeechEnchantmentEffect> LEECH_EFFECT = register("leech", LeechEnchantmentEffect.CODEC);
    public static final MapCodec<MomentumEnchantmentEffect> MOMENTUM_EFFECT = register("momentum", MomentumEnchantmentEffect.CODEC);
    public static final MapCodec<MarkingEnchantmentEffect> MARKING_EFFECT = register("marking", MarkingEnchantmentEffect.CODEC);
    public static final MapCodec<FrostAspectEnchantmentEffect> FROST_ASPECT_EFFECT = register("frost_aspect", FrostAspectEnchantmentEffect.CODEC);

    public static final MapCodec<CurseOfTheUndeadEffect> CURSE_OF_THE_UNDEAD_EFFECT = register("curse_of_the_undead", CurseOfTheUndeadEffect.CODEC);

    private static ResourceKey<Enchantment> register(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, MoreEnchanting.id(id));
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, MoreEnchanting.id(id), codec);
    }

    public static void register() {
    }
}
