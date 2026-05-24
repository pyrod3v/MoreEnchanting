package gg.pyrod3v.more_enchanting.network;

import gg.pyrod3v.more_enchanting.MoreEnchanting;
import gg.pyrod3v.more_enchanting.MoreEnchantingConfig;
import gg.pyrod3v.more_enchanting.components.MoreEnchantingComponents;
import gg.pyrod3v.more_enchanting.enchantment.MoreEnchantingEnchantments;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public record DoubleJumpPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DoubleJumpPacket> TYPE =
            new CustomPacketPayload.Type<>(MoreEnchanting.id("double_jump"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DoubleJumpPacket> CODEC =
            StreamCodec.unit(new DoubleJumpPacket());


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(TYPE, CODEC);
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            context.server().execute(() -> {
                Player player = context.player();

                if (player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).canDoubleJump &&
                        !player.onGround() &&
                        !player.isInWater() &&
                        player.getDeltaMovement().y < 0.2 &&
                        player.getItemBySlot(EquipmentSlot.FEET).isEnchanted() &&
                        player.getItemBySlot(EquipmentSlot.FEET).getEnchantments().entrySet().stream()
                        .anyMatch(entry -> entry.getKey().unwrapKey().isPresent() &&
                                entry.getKey().unwrapKey().get().equals(MoreEnchantingEnchantments.DOUBLE_JUMP))
                ) {
                    double fallDistance = player.fallDistance;
                    Vec3 velocity = player.getDeltaMovement();
                    player.setDeltaMovement(
                            velocity.x != 0 ? velocity.x + velocity.x / 3 : 0,
                            MoreEnchantingConfig.CONFIG.doubleJumpVelocity,
                            velocity.z != 0 ? velocity.z + velocity.z / 3 : 0);
                    player.hurtMarked = true;
                    player.fallDistance = fallDistance;

                    if (MoreEnchantingConfig.CONFIG.showDoubleJumpEffect) {
                        ServerLevel serverLevel = (ServerLevel) player.level();

                        serverLevel.playSound(
                                null,
                                player.getX(), player.getY(), player.getZ(),
                                SoundEvents.PLAYER_ATTACK_SWEEP,
                                SoundSource.PLAYERS,
                                1.0F, 1.0F
                        );

                        serverLevel.sendParticles(
                                ParticleTypes.CLOUD,
                                player.getX(), player.getY(), player.getZ(),
                                10,
                                0.2, 0.1, 0.2,
                                0.1
                        );
                    }

                    player.getComponent(MoreEnchantingComponents.ENCHANTMENT_DATA_COMPONENT).canDoubleJump = false;
                }
            });
        });
    }
}