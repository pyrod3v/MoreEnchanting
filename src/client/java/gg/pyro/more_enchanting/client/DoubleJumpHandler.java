package gg.pyro.more_enchanting.client;

import gg.pyro.more_enchanting.network.DoubleJumpPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class DoubleJumpHandler {
    private static boolean wasJumpPressed = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isJumpPressed = client.options.jumpKey.isPressed();
            boolean isOnGround = client.player.isOnGround();

            if (isJumpPressed && !wasJumpPressed && !isOnGround && !client.player.isSubmergedInWater()) {
                ClientPlayNetworking.send(new DoubleJumpPacket());
            }

            wasJumpPressed = isJumpPressed;
        });
    }
}