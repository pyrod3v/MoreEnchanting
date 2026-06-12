package gg.pyrod3v.more_enchanting.effect;

import gg.pyrod3v.more_enchanting.MoreEnchanting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class MoreEnchantingEffects {

    public static final Holder<MobEffect> DECOMPOSITION =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, MoreEnchanting.id("decomposition"), new DecompositionEffect());

    public static void register() {
    }
}
