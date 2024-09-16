package dev.rats159.abyssopelagic.worldgen.biomes;

import dev.rats159.abyssopelagic.block.GlowShroomBlock;
import dev.rats159.abyssopelagic.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import static dev.rats159.abyssopelagic.Abyssopelagic.MOD_ID;

public class ModFeatures {
    private static final RegistryKey<PlacedFeature> PLACED_GLOW_SHROOM = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID,"glow_shroom"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> CONFIGURED_GLOW_SHROOM = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MOD_ID,"glow_shroom"));

    public static void addGlowMushrooms(GenerationSettings.LookupBackedBuilder builder){
        builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModFeatures.PLACED_GLOW_SHROOM);
    }

    public static void bootstrapPlaced(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> configuredGlowShroom = registryEntryLookup.getOrThrow(CONFIGURED_GLOW_SHROOM);

        PlacedFeatures.register(
                featureRegisterable,
                PLACED_GLOW_SHROOM,
                configuredGlowShroom,

                CountPlacementModifier.of(UniformIntProvider.create(204, 250)),
                SquarePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_120_RANGE,
                BiomePlacementModifier.of()
        );
    }

    public static void bootstrapConfigured(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        DataPool.Builder<BlockState> builder = DataPool.builder();

        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                builder.add(ModBlocks.GLOW_SHROOM.getDefaultState().with(GlowShroomBlock.MUSHROOM_COUNT, i).with(GlowShroomBlock.FACING, direction), 1);
            }
        }

        ConfiguredFeatures.register(
                featureRegisterable,
                CONFIGURED_GLOW_SHROOM,
                Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(
                        2, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(builder)))
                )
        );
    }
}
