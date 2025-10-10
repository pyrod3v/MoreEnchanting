package gg.pyro.more_enchanting.client.datagen;

import gg.pyro.more_enchanting.MoreEnchanting;
import gg.pyro.more_enchanting.enchantment.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends FabricDynamicRegistryProvider {
    public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static final TagKey<Item> MELEE_WEAPON = TagKey.of(RegistryKeys.ITEM, MoreEnchanting.id("melee_weapon"));

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        register(entries, MoreEnchantingEnchantments.DOUBLE_JUMP, Enchantment.builder(
                Enchantment.definition(
                        registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.FOOT_ARMOR),
                        4,
                        1,
                        Enchantment.leveledCost(1, 20),
                        Enchantment.leveledCost(1, 20),
                        10,
                        AttributeModifierSlot.ARMOR
                )
        ));

        register(entries, MoreEnchantingEnchantments.ROOTED, Enchantment.builder(
                Enchantment.definition(
                        registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.LEG_ARMOR),
                        4,
                        2,
                        Enchantment.leveledCost(1, 20),
                        Enchantment.leveledCost(1, 25),
                        8,
                        AttributeModifierSlot.ARMOR
                )
        ).addEffect(
                EnchantmentEffectComponentTypes.TICK,
                new RootedEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.SOUL_ANCHOR, Enchantment.builder(
                        Enchantment.definition(
                                registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.CHEST_ARMOR),
                                5,
                                1,
                                Enchantment.leveledCost(1, 25),
                                Enchantment.leveledCost(1, 25),
                                5,
                                AttributeModifierSlot.ARMOR
                        )
                ).addEffect(
                        EnchantmentEffectComponentTypes.TICK,
                        new SoulAnchorEnchantmentEffect()
                )
        );

        register(entries, MoreEnchantingEnchantments.LEECH, Enchantment.builder(
                Enchantment.definition(
                        registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(MELEE_WEAPON),
                        2,
                        3,
                        Enchantment.leveledCost(1, 20),
                        Enchantment.leveledCost(1, 30),
                        8,
                        AttributeModifierSlot.ANY
                )
        ).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new LeechEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.MOMENTUM, Enchantment.builder(
                Enchantment.definition(
                        registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(MELEE_WEAPON),
                        6,
                        2,
                        Enchantment.leveledCost(1, 15),
                        Enchantment.leveledCost(1, 20),
                        6,
                        AttributeModifierSlot.ANY
                )
        ).addEffect(
                EnchantmentEffectComponentTypes.TICK,
                new MomentumEnchantmentEffect()
            )
        );
    }

    private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "MoreEnchantingEnchantmentGenerator";
    }
}