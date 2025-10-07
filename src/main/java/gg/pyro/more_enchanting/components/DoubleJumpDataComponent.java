package gg.pyro.more_enchanting.components;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class DoubleJumpDataComponent implements AutoSyncedComponent {
    private final PlayerEntity self;
    public boolean canDoubleJump = true;

    public DoubleJumpDataComponent(PlayerEntity self) {
        this.self = self;
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player == self;
    }

    @Override
    public void readData(ReadView readView) {
        canDoubleJump = readView.getBoolean("canDoubleJump", true);
    }

    @Override
    public void writeData(WriteView writeView) {
        writeView.putBoolean("canDoubleJump", canDoubleJump);
    }
}
