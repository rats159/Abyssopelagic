package dev.rats159.abyssopelagic.worldgen.biomes.features;

import dev.rats159.abyssopelagic.block.GlowShroomBlock;
import dev.rats159.abyssopelagic.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CaveVines;
import net.minecraft.block.CaveVinesHeadBlock;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

import static dev.rats159.abyssopelagic.Abyssopelagic.MOD_ID;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> GLOW_SHROOM = RegistryKey.of(
        RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MOD_ID, "glow_shroom"));
    
    
    public static final RegistryKey<ConfiguredFeature<?, ?>> GLOW_TENDRIL = RegistryKey.of(
        RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MOD_ID, "glow_tendril"));
    
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        DataPool.Builder<BlockState> builder = DataPool.builder();
        
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                builder.add(
                    ModBlocks.GLOW_SHROOM.getDefaultState()
                        .with(GlowShroomBlock.MUSHROOM_COUNT, i)
                        .with(GlowShroomBlock.FACING, direction),
                    1
                );
            }
        }
        
        ConfiguredFeatures.register(
            featureRegisterable,
            GLOW_SHROOM,
            Feature.RANDOM_PATCH,
            new RandomPatchFeatureConfig(2, 6, 2,
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(builder))
                )
            )
        );
        
        WeightedBlockStateProvider tendrilBaseProvider = new WeightedBlockStateProvider(
            DataPool.<BlockState>builder()
                .add(ModBlocks.GLOW_TENDRILS.getDefaultState(), 4)
                .add(ModBlocks.GLOW_TENDRILS.getDefaultState().with(CaveVines.BERRIES, true), 1)
        );
        
        RandomizedIntBlockStateProvider tendrilHeadProvider = new RandomizedIntBlockStateProvider(
            new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                                               .add(ModBlocks.GLOW_TENDRILS.getDefaultState(), 4)
                                               .add(ModBlocks.GLOW_TENDRILS.getDefaultState()
                                                        .with(CaveVines.BERRIES, Boolean.TRUE), 1)),
            CaveVinesHeadBlock.AGE, UniformIntProvider.create(18, 25));
        
        ConfiguredFeatures.register(
            featureRegisterable,
            GLOW_TENDRIL,
            Feature.BLOCK_COLUMN,
            new BlockColumnFeatureConfig(
                List.of(
                    BlockColumnFeatureConfig.createLayer(UniformIntProvider.create(0, 6), tendrilBaseProvider),
                    BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), tendrilHeadProvider)
                ),
                Direction.UP,
                BlockPredicate.IS_AIR,
                true
            )
        );
        
    }
}
