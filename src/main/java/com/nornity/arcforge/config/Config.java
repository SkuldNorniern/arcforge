package com.nornity.arcforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final Server SERVER;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        var builder = new ModConfigSpec.Builder();
        SERVER = new Server(builder);
        SERVER_SPEC = builder.build();
    }

    public static class Server {
        public final ModConfigSpec.DoubleValue arcanaGenerationRate;
        public final ModConfigSpec.DoubleValue arcanaTransferRate;
        public final ModConfigSpec.BooleanValue overloadEnabled;
        public final ModConfigSpec.BooleanValue machineExplosionEnabled;
        public final ModConfigSpec.IntValue constructLimit;
        public final ModConfigSpec.BooleanValue quarryEnabled;
        public final ModConfigSpec.BooleanValue turretEnabled;
        public final ModConfigSpec.BooleanValue worldgenEnabled;
        public final ModConfigSpec.BooleanValue ritualInstabilityEnabled;
        public final ModConfigSpec.BooleanValue bloodBranchEnabled;

        public final ModConfigSpec.BooleanValue dawnboundCompatibilityEnabled;
        public final ModConfigSpec.BooleanValue respectPrimitiveProgression;
        public final ModConfigSpec.BooleanValue enableDawnboundRecipes;
        public final ModConfigSpec.BooleanValue arcaneQuarryRequiresHarvestTier;
        public final ModConfigSpec.BooleanValue transmutationRespectsProgressionLocks;
        public final ModConfigSpec.BooleanValue ae2CompatEnabled;
        public final ModConfigSpec.BooleanValue rfCompatEnabled;
        public final ModConfigSpec.DoubleValue energyConversionRatio;
        public final ModConfigSpec.IntValue bridgeTransferLimit;

        Server(ModConfigSpec.Builder builder) {
            builder.push("arcana");
            arcanaGenerationRate = builder
                .comment("Base Arcana generation rate per tick")
                .defineInRange("arcanaGenerationRate", 1.0, 0.0, 1000.0);
            arcanaTransferRate = builder
                .comment("Base Arcana transfer rate per tick")
                .defineInRange("arcanaTransferRate", 10.0, 0.0, 10000.0);
            overloadEnabled = builder
                .comment("Whether Arcana overload/stability mechanics are enabled")
                .define("overloadEnabled", false);
            machineExplosionEnabled = builder
                .comment("Whether overloaded machines can explode")
                .define("machineExplosionEnabled", false);
            builder.pop();

            builder.push("limits");
            constructLimit = builder
                .comment("Maximum number of magic constructs per player")
                .defineInRange("constructLimit", 8, 0, 256);
            quarryEnabled = builder
                .comment("Whether the Arcane Quarry is enabled")
                .define("quarryEnabled", true);
            turretEnabled = builder
                .comment("Whether the Spell Turret is enabled")
                .define("turretEnabled", true);
            worldgenEnabled = builder
                .comment("Whether Arcforge worldgen (ores, etc.) is enabled")
                .define("worldgenEnabled", true);
            builder.pop();

            builder.push("blood");
            ritualInstabilityEnabled = builder
                .comment("Whether blood ritual instability mechanics are enabled")
                .define("ritualInstabilityEnabled", true);
            bloodBranchEnabled = builder
                .comment("Whether the blood/forbidden branch is enabled")
                .define("bloodBranchEnabled", true);
            builder.pop();

            builder.push("compatDawnbound");
            dawnboundCompatibilityEnabled = builder
                .comment("Whether Dawnbound compatibility is enabled when Dawnbound is installed")
                .define("dawnboundCompatibilityEnabled", true);
            respectPrimitiveProgression = builder
                .define("respectPrimitiveProgression", true);
            enableDawnboundRecipes = builder
                .define("enableDawnboundRecipes", true);
            arcaneQuarryRequiresHarvestTier = builder
                .define("arcaneQuarryRequiresHarvestTier", true);
            transmutationRespectsProgressionLocks = builder
                .define("transmutationRespectsProgressionLocks", true);
            builder.pop();

            builder.push("compatTech");
            ae2CompatEnabled = builder
                .define("ae2CompatEnabled", true);
            rfCompatEnabled = builder
                .define("rfCompatEnabled", true);
            energyConversionRatio = builder
                .comment("Arcana-to-RF/FE conversion ratio (lossy by default)")
                .defineInRange("energyConversionRatio", 0.5, 0.0, 1.0);
            bridgeTransferLimit = builder
                .comment("Maximum energy transfer per tick through compat bridges")
                .defineInRange("bridgeTransferLimit", 1000, 0, 1_000_000);
            builder.pop();
        }
    }
}
