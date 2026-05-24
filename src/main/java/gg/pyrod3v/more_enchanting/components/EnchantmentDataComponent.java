package gg.pyrod3v.more_enchanting.components;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class EnchantmentDataComponent implements AutoSyncedComponent {
    private final Player self;

    public boolean canDoubleJump = true;
    public int hits = 0; // not saved as it is used temporarily by the momentum effect
    public boolean wasCrit = false; // not saved as it is used temporarily by the leech effect

    public EnchantmentDataComponent(Player self) {
        this.self = self;
    }

    @Override
    public boolean shouldSyncWith(@NonNull ServerPlayer player) {
        return player == self;
    }

    @Override
    public void readData(ValueInput readView) {
        canDoubleJump = readView.getBooleanOr("canDoubleJump", true);
    }

    @Override
    public void writeData(ValueOutput writeView) {
        writeView.putBoolean("canDoubleJump", canDoubleJump);
    }
}
