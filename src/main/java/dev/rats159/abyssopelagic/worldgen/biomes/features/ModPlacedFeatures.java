package dev.rats159.abyssopelagic.worldgen.biomes.features;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import static dev.rats159.abyssopelagic.Abyssopelagic.MOD_ID;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> GLOW_SHROOM = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            Identifier.of(MOD_ID, "glow_shroom"));

    public static final RegistryKey<PlacedFeature> GLOW_TENDRIL = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            Identifier.of(MOD_ID, "glow_tendril"));

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(
                RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> configuredGlowShroom = registryEntryLookup.getOrThrow(
                ModConfiguredFeatures.GLOW_SHROOM);
        RegistryEntry<ConfiguredFeature<?, ?>> configuredGlowTendril = registryEntryLookup.getOrThrow(
                ModConfiguredFeatures.GLOW_TENDRIL);


        PlacedFeatures.register(featureRegisterable,
                GLOW_SHROOM,
                configuredGlowShroom,

                CountPlacementModifier.of(UniformIntProvider.create(204, 250)),
                SquarePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_120_RANGE,
                BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable,
                GLOW_TENDRIL,
                configuredGlowTendril,

                CountPlacementModifier.of(UniformIntProvider.create(204, 250)),
                SquarePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_120_RANGE,
                BiomePlacementModifier.of());
    }
}
