package gg.pyrod3v.more_enchanting.client.datagen;

import gg.pyrod3v.more_enchanting.enchantment.*;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends FabricDynamicRegistryProvider {

    public EnchantmentGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        HolderGetter<Item> itemLookup = entries.getLookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantmentLookup = entries.getLookup(Registries.ENCHANTMENT);

        register(entries, MoreEnchantingEnchantments.DOUBLE_JUMP, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.FOOT_ARMOR),
                        3,
                        1,
                        Enchantment.dynamicCost(1, 20),
                        Enchantment.dynamicCost(1, 20),
                        10,
                        EquipmentSlotGroup.ARMOR
                )
            ).exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(Enchantments.FEATHER_FALLING)))
        );

        register(entries, MoreEnchantingEnchantments.ROOTED, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.LEG_ARMOR),
                        5,
                        2,
                        Enchantment.dynamicCost(1, 20),
                        Enchantment.dynamicCost(1, 25),
                        8,
                        EquipmentSlotGroup.ARMOR
                )
            ).withEffect(
                EnchantmentEffectComponents.TICK,
                new RootedEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.SOUL_ANCHOR, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.CHEST_ARMOR),
                        4,
                        1,
                        Enchantment.dynamicCost(1, 25),
                        Enchantment.dynamicCost(1, 25),
                        5,
                        EquipmentSlotGroup.ARMOR
                )
            ).withEffect(
                EnchantmentEffectComponents.TICK,
                new SoulAnchorEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.DESPERATION, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        4,
                        3,
                        Enchantment.dynamicCost(1, 20),
                        Enchantment.dynamicCost(1, 30),
                        8,
                        EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                EnchantmentEffectComponents.TICK,
                new DesperationEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.LEECH, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        2,
                        3,
                        Enchantment.dynamicCost(1, 20),
                        Enchantment.dynamicCost(1, 30),
                        8,
                        EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                EnchantmentEffectComponents.POST_ATTACK,
                EnchantmentTarget.ATTACKER,
                EnchantmentTarget.VICTIM,
                new LeechEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.MOMENTUM, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                        6,
                        2,
                        Enchantment.dynamicCost(1, 15),
                        Enchantment.dynamicCost(1, 20),
                        6,
                        EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                EnchantmentEffectComponents.TICK,
                new MomentumEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.MARKING, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        3,
                        3,
                        Enchantment.dynamicCost(1, 25),
                        Enchantment.dynamicCost(1, 35),
                        10,
                        EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                EnchantmentEffectComponents.POST_ATTACK,
                EnchantmentTarget.ATTACKER,
                EnchantmentTarget.VICTIM,
                new MarkingEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.FROST_ASPECT, new Enchantment.Builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.SWORDS),
                        2,
                        3,
                        Enchantment.dynamicCost(1, 15),
                        Enchantment.dynamicCost(1, 25),
                        5,
                        EquipmentSlotGroup.MAINHAND
                )
            ).withEffect(
                EnchantmentEffectComponents.POST_ATTACK,
                EnchantmentTarget.ATTACKER,
                EnchantmentTarget.VICTIM,
                new FrostAspectEnchantmentEffect()
            )
        );

        register(entries, MoreEnchantingEnchantments.CURSE_OF_THE_UNDEAD, new Enchantment.Builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                1,
                                1,
                                Enchantment.dynamicCost(1, 31),
                                Enchantment.dynamicCost(1, 31),
                                5,
                                EquipmentSlotGroup.ARMOR
                        )
                ).withEffect(
                        EnchantmentEffectComponents.TICK,
                        new CurseOfTheUndeadEffect()
                )
        );
    }

    private void register(Entries entries, ResourceKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.identifier()), resourceConditions);
    }

    @Override
    public @NonNull String getName() {
        return "MoreEnchantingEnchantmentGenerator";
    }
}