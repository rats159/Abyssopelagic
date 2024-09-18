package dev.rats159.abyssopelagic.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

import static net.minecraft.state.property.Properties.AGE_25;

public class GlowTendrilsBlock extends AbstractPlantStemBlock implements CaveVines, Fertilizable {
    public static final MapCodec<GlowTendrilsBlock> CODEC = createCodec(GlowTendrilsBlock::new);
    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    @Override
    public MapCodec<GlowTendrilsBlock> getCodec() {
        return CODEC;
    }

    public GlowTendrilsBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.9);
        this.setDefaultState(this.stateManager.getDefaultState().with(BERRIES,false).with(AGE_25,0));
    }
    
    @Override
    protected int getGrowthLength(Random random) {
        return 1;
    }
    
    @Override
    protected boolean chooseStemState(BlockState state) {
        return state.isAir();
    }
    
    @Override
    protected Block getPlant() {
        return ModBlocks.GLOW_TENDRILS_PLANT;
    }
    
    @Override
    protected BlockState copyState(BlockState from, BlockState to) {
        return to.with(BERRIES, from.get(BERRIES));
    }
    
    @Override
    protected BlockState age(BlockState state, Random random) {
        return super.age(state, random).with(BERRIES, random.nextFloat() < 0.11F);
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
        super.appendProperties(builder);
        builder.add(BERRIES);
    }
    
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !state.get(BERRIES);
    }
    
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
    
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(BERRIES, true), Block.NOTIFY_LISTENERS);
    }
}
