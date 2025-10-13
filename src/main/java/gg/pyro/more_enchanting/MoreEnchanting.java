package gg.pyro.more_enchanting;

import gg.pyro.more_enchanting.enchantment.MoreEnchantingEnchantments;
import gg.pyro.more_enchanting.network.DoubleJumpPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class MoreEnchanting implements ModInitializer {

    public static final String MOD_ID = "more_enchanting";
    public static MinecraftServer server = null;

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    @Override
    public void onInitialize() {
        MoreEnchantingConfig.init();
        MoreEnchantingEnchantments.register();
        DoubleJumpPacket.register();
        DoubleJumpPacket.registerServer();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> MoreEnchanting.server = server);
    }
}
