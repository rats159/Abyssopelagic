package dev.rats159.abyssopelagic.worldgen.biomes.features;

import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

import static dev.rats159.abyssopelagic.Abyssopelagic.MOD_ID;

public class ModFeatures {
    public static final Feature<GlowTendrilsFeatureConfig> GLOW_TENDRILS = register("glow_tendrils",
            new GlowTendrilsFeature(GlowTendrilsFeatureConfig.CODEC));


    public static void addGlowMushrooms(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.GLOW_SHROOM);
    }

    public static void addGlowTendrils(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.GLOW_TENDRIL);
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, Identifier.of(MOD_ID,name), feature);
    }

    public static void initialize(){}
}
