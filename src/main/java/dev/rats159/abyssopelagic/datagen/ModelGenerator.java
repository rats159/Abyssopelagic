package dev.rats159.abyssopelagic.datagen;

import dev.rats159.abyssopelagic.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModelGenerator extends FabricModelProvider {
   public ModelGenerator(FabricDataOutput output) {
      super(output);
   }

   @Override
   public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
      blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ABYSSAL_ROCK);
   }

   @Override
   public void generateItemModels(ItemModelGenerator itemModelGenerator) {

   }
}
