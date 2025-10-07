package gg.pyro.more_enchanting.components;

import gg.pyro.more_enchanting.MoreEnchanting;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class MoreEnchantingComponents implements EntityComponentInitializer {

    public static final ComponentKey<DoubleJumpDataComponent> DOUBLE_JUMP_DATA_COMPONENT =
            ComponentRegistry.getOrCreate(MoreEnchanting.id("double_jump_data"), DoubleJumpDataComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(DOUBLE_JUMP_DATA_COMPONENT, DoubleJumpDataComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
