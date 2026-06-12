package gg.pyrod3v.more_enchanting;

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
    public ConfigGroup doubleJumpSettings = new ConfigGroup("Double Jump Settings", true);

    public boolean showDoubleJumpEffect = true;

    @ConfigGroup.Pop
    @ValidatedDouble.Restrict(min = 0.1, max = 5)
    public double doubleJumpVelocity = 0.42;

    // soul anchor
    public ConfigGroup soulAnchorSettings = new ConfigGroup("Soul Anchor Settings", true);

    @ConfigGroup.Pop
    @ValidatedDouble.Restrict(min = 1, max = 19)
    public double soulAnchorHealthThreshold = 4;

    // desperation
    public ConfigGroup desperationSettings = new ConfigGroup("Desperation Settings", true);

    @ConfigGroup.Pop
    @ValidatedDouble.Restrict(min = 1, max = 19)
    public double desperationHealthThreshold = 5;

    // leech
    public ConfigGroup leechSettings = new ConfigGroup("Leech Settings", true);

    @ValidatedFloat.Restrict(min = 0, max = 1)
    public float leechHealChance = 0.5f;

    @ValidatedFloat.Restrict(min = 0, max = 10)
    public float leechBaseHeal = 0.0f;

    @ConfigGroup.Pop
    @ValidatedFloat.Restrict(min = 0.5f, max = 10)
    public float leechHealPerLevel = 0.5f;

    // momentum
    public ConfigGroup momentumSettings = new ConfigGroup("Momentum Settings", true);

    @ValidatedInt.Restrict(min = 0, max = 50)
    public int momentumBaseTimer = 15;

    @Desc("Controls the secondary timer for the momentum effect. The player has `base + (secondary / attack speed)` ticks (excluding level extras) to land 2 or more hits.")
    @ValidatedInt.Restrict(min = 10, max = 100)
    public int momentumSecondaryTimer = 40;

    @ConfigGroup.Pop
    @ValidatedInt.Restrict(min = 0, max = 100)
    @Desc("Controls how many ticks per extra level the player gets to land 2 or more hits.")
    public int momentumPerLevelTimer = 10;

    // frost aspect
    public ConfigGroup frostAspectSettings = new ConfigGroup("Frost aspect settings", true);

    @ValidatedInt.Restrict(min = 0, max = 50)
    public int frostAspectBaseFrozenTicks = 20;

    @ConfigGroup.Pop
    @ValidatedInt.Restrict(min = 5, max = 50)
    public int frostAspectFrozenTicksPerLevel = 10;

    // curse of the undead
    public ConfigGroup decompositionSettings = new ConfigGroup("Decomposition Settings", true);

    @ValidatedInt.Restrict(min = 0, max = 100)
    @Desc("Chance (%) of the decomposition effect to damage the player (0 disables it).")
    public int decompositionDamageChance = 30;

    @ConfigGroup.Pop
    @ValidatedInt.Restrict(min = 1, max = 10)
    @Desc("Damage dealt by the Decomposition effect.")
    public int decompositionDamage = 1;
}
