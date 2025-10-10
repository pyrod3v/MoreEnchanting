package gg.pyro.more_enchanting;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

public class MoreEnchantingConfig extends Config {

    public static MoreEnchantingConfig CONFIG;

    public MoreEnchantingConfig() {
        super(MoreEnchanting.id("config"));
    }

    public static void init() {
        CONFIG = ConfigApiJava.registerAndLoadConfig(MoreEnchantingConfig::new);
    }

    // double jump
    ConfigGroup doubleJumpSettings = new ConfigGroup();
    public boolean showDoubleJumpEffect = true;

    @ConfigGroup.Pop
    @ValidatedDouble.Restrict(min = 0.1, max = 5)
    public double doubleJumpVelocity = 0.42;

    ConfigGroup soulAnchorSettings = new ConfigGroup();

    @ConfigGroup.Pop
    @ValidatedDouble.Restrict(min = 0.5, max = 19)
    public double soulAnchorHealthThreshold = 4;

    // leech
    ConfigGroup leechSettings = new ConfigGroup();
    public boolean leechAlwaysHeal = false;

    @ValidatedFloat.Restrict(min = 0, max = 10)
    public float leechBaseHeal = 0.0f;

    @ConfigGroup.Pop
    @ValidatedFloat.Restrict(min = 0.5f, max = 10)
    public float leechHealPerLevel = 0.5f;

    // momentum
    ConfigGroup momentumSettings = new ConfigGroup();
    @ValidatedInt.Restrict(min = 0, max = 50)
    public int momentumBaseTimer = 15;

    @Desc("Controls the secondary timer for the momentum effect. The player has `base + (secondary / attack speed)` ticks (excluding level extras) to land 2 or more hits.")
    @ValidatedInt.Restrict(min = 10, max = 100)
    public int momentumSecondaryTimer = 40;

    @ConfigGroup.Pop
    @ValidatedInt.Restrict(min = 0, max = 100)
    @Desc("Controls how many ticks per extra level the player gets to land 2 or more hits.")
    public int momentumPerLevelTimer = 10;
}
