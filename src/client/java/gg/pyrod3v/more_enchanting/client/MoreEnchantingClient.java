package gg.pyrod3v.more_enchanting.client;

import net.fabricmc.api.ClientModInitializer;

public class MoreEnchantingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DoubleJumpHandler.register();
    }
}
