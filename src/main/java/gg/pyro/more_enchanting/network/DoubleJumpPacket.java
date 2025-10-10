package gg.pyro.more_enchanting.network;

import gg.pyro.more_enchanting.MoreEnchanting;
import gg.pyro.more_enchanting.MoreEnchantingConfig;
import gg.pyro.more_enchanting.components.MoreEnchantingComponents;
import gg.pyro.more_enchanting.enchantment.MoreEnchantingEnchantments;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public record DoubleJumpPacket() implements CustomPayload {
    public static final CustomPayload.Id<DoubleJumpPacket> ID =
            new CustomPayload.Id<>(MoreEnchanting.id("double_jump"));

    public static final PacketCodec<RegistryByteBuf, DoubleJumpPacket> CODEC =
            PacketCodec.unit(new DoubleJumpPacket());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void register() {
        PayloadTypeRegistry.playC2S().register(ID, CODEC);
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();

                if (player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).canDoubleJump &&
                        !player.isOnGround() &&
                        !player.isSubmergedInWater() &&
                        player.getVelocity().y < 0.2 &&
                        player.getEquippedStack(EquipmentSlot.FEET).hasEnchantments() &&
                        player.getEquippedStack(EquipmentSlot.FEET).getEnchantments().getEnchantmentEntries().stream()
                        .anyMatch(entry -> entry.getKey().getKey().isPresent() &&
                                entry.getKey().getKey().get().equals(MoreEnchantingEnchantments.DOUBLE_JUMP))
                ) {
                    double fallDistance = player.fallDistance;
                    Vec3d velocity = player.getVelocity();
                    System.out.println(velocity);
                    player.setVelocity(velocity.x > 0 ? velocity.x + velocity.x / 4 : 0, MoreEnchantingConfig.CONFIG.doubleJumpVelocity, velocity.z);
                    player.velocityModified = true;
                    player.fallDistance = fallDistance;

                    if (MoreEnchantingConfig.CONFIG.showDoubleJumpEffect) {
                        player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 0.5F, 1.0F);
                        (player.getWorld()).spawnParticles(
                                ParticleTypes.CLOUD,
                                player.getX(),
                                player.getY(),
                                player.getZ(),
                                10,
                                0.2, 0.1, 0.2, 0.02
                        );
                    }

                    player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).canDoubleJump = false;
                }
            });
        });
    }
}