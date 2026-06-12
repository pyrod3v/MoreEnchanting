package gg.pyrod3v.more_enchanting.client.datagen;

import gg.pyrod3v.more_enchanting.MoreEnchantingTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(MoreEnchantingTags.HEAVY_WEAPONS)
                .forceAddTag(ItemTags.AXES)
                .forceAddTag(ItemTags.MACE_ENCHANTABLE)
                .setReplace(false);
    }
}