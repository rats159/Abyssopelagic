package dev.rats159.abyssopelagic.worldgen.biomes.features;

import com.mojang.serialization.Codec;
import dev.rats159.abyssopelagic.block.ModBlocks;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class GlowTendrilsFeature extends Feature<GlowTendrilsFeatureConfig> {
    public GlowTendrilsFeature(Codec<GlowTendrilsFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<GlowTendrilsFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        if (!isSuitable(structureWorldAccess, blockPos)) {
            return false;
        } else {
            Random random = context.getRandom();
            GlowTendrilsFeatureConfig glowTendrilsFeatureConfig = context.getConfig();
            int spreadWidth = glowTendrilsFeatureConfig.spreadWidth();
            int maxHeight = glowTendrilsFeatureConfig.maxHeight();
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int l = 0; l < spreadWidth * spreadWidth; l++) {
                mutable.set(blockPos);
                if (canGenerate(structureWorldAccess, mutable) && isSuitable(structureWorldAccess, mutable)) {
                    int height = MathHelper.nextInt(random, 1, maxHeight);
                    if (random.nextInt(6) == 0) {
                        height *= 2; // Extra tall
                    }

                    if (random.nextInt(5) == 0) {
                        height = 1; // Extra short
                    }

                    generateVineColumn(structureWorldAccess, random, mutable, height, 17, 25);
                }
            }

            return true;
        }
    }

    private static boolean canGenerate(WorldAccess world, BlockPos.Mutable pos) {
        do {
            pos.move(0, -1, 0);
            if (world.isOutOfHeightLimit(pos)) {
                return false;
            }
        } while (world.getBlockState(pos).isAir());

        pos.move(0, 1, 0);
        return true;
    }

    public static void generateVineColumn(WorldAccess world, Random random, BlockPos.Mutable pos, int maxLength, int minAge, int maxAge) {
        for (int i = 1; i <= maxLength; i++) {
            if (world.isAir(pos)) {
                if (i == maxLength || !world.isAir(pos.up())) {
                    world.setBlockState(pos,
                            ModBlocks.GLOW_TENDRILS.getDefaultState().with(AbstractPlantStemBlock.AGE,
                                    MathHelper.nextInt(random, minAge, maxAge)),
                            Block.NOTIFY_LISTENERS);
                    break;
                }

                world.setBlockState(pos, ModBlocks.GLOW_TENDRILS_PLANT.getDefaultState(), Block.NOTIFY_LISTENERS);
            }

            pos.move(Direction.UP);
        }
    }

    private static boolean isSuitable(WorldAccess world, BlockPos pos) {
        return world.isAir(pos) && world.getBlockState(pos.down()).isOf(ModBlocks.ABYSSAL_ROCK);
    }
}
