package dev.rats159.abyssopelagic.worldgen.biomes.surface;

import dev.rats159.abyssopelagic.block.ModBlocks;
import dev.rats159.abyssopelagic.worldgen.biomes.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModSurfaceRules {
   private static final MaterialRules.MaterialRule ABYSSAL_ROCK = makeStateRule(ModBlocks.ABYSSAL_ROCK);

   public static MaterialRules.MaterialRule makeRules(){
      return MaterialRules.condition(MaterialRules.biome(ModBiomes.ABYSSAL_CAVES), ABYSSAL_ROCK);
   }

   public static MaterialRules.MaterialRule makeStateRule(Block block){
      return MaterialRules.block(block.getDefaultState());
   }
}
