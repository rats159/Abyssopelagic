package dev.rats159.abyssopelagic.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class GlowTendrilsPlantBlock extends AbstractPlantBlock implements Fertilizable,CaveVines{
    public static final MapCodec<GlowTendrilsPlantBlock> CODEC = createCodec(GlowTendrilsPlantBlock::new);
    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    @Override
    public MapCodec<GlowTendrilsPlantBlock> getCodec() {
        return CODEC;
    }

    public GlowTendrilsPlantBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false);
        this.setDefaultState(this.stateManager.getDefaultState().with(BERRIES,false));
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.GLOW_TENDRILS;
    }

    @Override
    protected BlockState copyState(BlockState from, BlockState to) {
        return to.with(BERRIES, from.get(BERRIES));
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.GLOW_TENDRILS.asItem());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(BERRIES)) {
            Block.dropStack(world, pos, new ItemStack(ModBlocks.GLOW_TENDRILS.asItem(), 1));
            BlockState blockState = state.with(BERRIES, Boolean.FALSE);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BERRIES);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !(Boolean)state.get(BERRIES);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(BERRIES, Boolean.TRUE), Block.NOTIFY_LISTENERS);
    }


}
