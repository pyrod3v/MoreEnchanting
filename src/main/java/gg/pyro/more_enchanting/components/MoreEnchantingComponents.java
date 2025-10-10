package gg.pyro.more_enchanting.components;

import gg.pyro.more_enchanting.MoreEnchanting;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class MoreEnchantingComponents implements EntityComponentInitializer {

    public static final ComponentKey<EnchantmentDataComponent> ENCHANTMENT_DATA_COMPONENT =
            ComponentRegistry.getOrCreate(MoreEnchanting.id("enchantment_data"), EnchantmentDataComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ENCHANTMENT_DATA_COMPONENT, EnchantmentDataComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
