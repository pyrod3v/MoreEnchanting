package gg.pyrod3v.more_enchanting;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MoreEnchantingTags {

    public static final TagKey<Item> HEAVY_WEAPONS = TagKey.create(Registries.ITEM, MoreEnchanting.id("heavy_weapons"));

    public static void register() {
    }
}
