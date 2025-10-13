package gg.pyro.more_enchanting.components;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.Component;

public class MarkingComponent implements Component {

    public String markedBy = null;
    public int level = 0;

    @Override
    public void readData(ReadView readView) {
        markedBy = readView.getString("markedBy", null);
        level = readView.getInt("level", 0);
    }

    @Override
    public void writeData(WriteView writeView) {
        if (markedBy != null) {
            writeView.putString("markedBy", markedBy);
        }
        if (level > 0) {
            writeView.putInt("level", level);
        }
    }
}
