package gg.pyrod3v.more_enchanting.client;

import gg.pyrod3v.more_enchanting.network.DoubleJumpPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class DoubleJumpHandler {
    private static boolean wasJumpPressed = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isJumpPressed = client.options.keyJump.isDown();
            boolean isOnGround = client.player.onGround();

            if (isJumpPressed && !wasJumpPressed && !isOnGround && !client.player.isInWater()) {
                ClientPlayNetworking.send(new DoubleJumpPacket());
            }

            wasJumpPressed = isJumpPressed;
        });
    }
}