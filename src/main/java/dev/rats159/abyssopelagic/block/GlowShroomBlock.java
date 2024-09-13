package dev.rats159.abyssopelagic.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.function.BiFunction;
import java.util.function.Function;

public class GlowShroomBlock extends Block implements Fertilizable {
   public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
   public static final IntProperty MUSHROOM_COUNT = ModProperties.MUSHROOM_COUNT;

   public GlowShroomBlock(Settings settings) {
      super(settings);
   }
private static final BiFunction<Direction, Integer, VoxelShape> FACING_AND_AMOUNT_TO_SHAPE = Util.memoize(
  (facing, flowerAmount) -> {
     VoxelShape[] voxelShapes = new VoxelShape[]{
       Block.createCuboidShape(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
       Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
       Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
       Block.createCuboidShape(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)
     };
     VoxelShape voxelShape = VoxelShapes.empty();

     for (int i = 0; i < flowerAmount; i++) {
        int j = Math.floorMod(i - facing.getHorizontal(), 4);
        voxelShape = VoxelShapes.union(voxelShape, voxelShapes[j]);
     }

     return voxelShape.asCuboid();
  }
   );

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(MUSHROOM_COUNT) < 4 || super.canReplace(state, context);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return FACING_AND_AMOUNT_TO_SHAPE.apply(state.get(FACING), state.get(MUSHROOM_COUNT));
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
      return blockState.isOf(this)
        ? blockState.with(MUSHROOM_COUNT, Math.min(4, blockState.get(MUSHROOM_COUNT) + 1))
        : this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, MUSHROOM_COUNT);
   }

   @Override
   public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      int i = state.get(MUSHROOM_COUNT);
      if (i < 4) {
         world.setBlockState(pos, state.with(MUSHROOM_COUNT, i + 1), Block.NOTIFY_LISTENERS);
      } else {
         dropStack(world, pos, new ItemStack(this));
      }
   }
}
