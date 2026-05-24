package gg.pyrod3v.more_enchanting.components;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.ComponentV3;

public class MarkingComponent implements ComponentV3 {

    public String markedBy = null;
    public int level = 0;

    @Override
    public void readData(ValueInput readView) {
        markedBy = readView.getStringOr("markedBy", "");
        level = readView.getIntOr("level", 0);
    }

    @Override
    public void writeData(ValueOutput writeView) {
        if (markedBy != null) {
            writeView.putString("markedBy", markedBy);
        }
        if (level > 0) {
            writeView.putInt("level", level);
        }
    }
}
