package dev.rats159.abyssopelagic.datagen;

import dev.rats159.abyssopelagic.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ModelGenerator extends FabricModelProvider {
   public ModelGenerator(FabricDataOutput output) {
      super(output);
      
   }

   @Override
   public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
      blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ABYSSAL_ROCK);
      registerTendrils(blockStateModelGenerator);
   }
   
   private void registerTendrils(BlockStateModelGenerator generator) {
      Identifier regular = generator.createSubModel(ModBlocks.GLOW_TENDRILS, "", Models.CROSS, TextureMap::cross);
      Identifier lit = generator.createSubModel(ModBlocks.GLOW_TENDRILS, "_lit", Models.CROSS, TextureMap::cross);
      generator.blockStateCollector
          .accept(VariantsBlockStateSupplier.create(ModBlocks.GLOW_TENDRILS).coordinate(
              BlockStateModelGenerator.createBooleanModelMap(Properties.BERRIES, lit, regular)));
      Identifier plantRegular = generator.createSubModel(ModBlocks.GLOW_TENDRILS_PLANT, "", Models.CROSS, TextureMap::cross);
      Identifier plantLit = generator.createSubModel(ModBlocks.GLOW_TENDRILS_PLANT, "_lit", Models.CROSS, TextureMap::cross);
      generator.blockStateCollector
          .accept(VariantsBlockStateSupplier.create(ModBlocks.GLOW_TENDRILS_PLANT).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.BERRIES, plantLit, plantRegular)));
   }
   
   @Override
   public void generateItemModels(ItemModelGenerator itemModelGenerator) {

   }
}
