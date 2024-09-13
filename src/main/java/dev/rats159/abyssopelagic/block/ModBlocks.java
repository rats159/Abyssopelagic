package dev.rats159.abyssopelagic.block;

import dev.rats159.abyssopelagic.Abyssopelagic;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
   public static final Block ABYSSAL_ROCK = createBlock("abyssal_rock", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));
   public static final Block GLOW_SHROOM = createBlock("glow_shroom", new GlowShroomBlock(settings().mapColor(MapColor.BRIGHT_TEAL).noCollision().sounds(BlockSoundGroup.HONEY).pistonBehavior(PistonBehavior.DESTROY).luminance((state) -> state.get(ModProperties.MUSHROOM_COUNT) * 2 + 2)));


   private static Block createBlock(String name, Block block) {
      return createBlock(name, block, true);
   }

   private static Block createBlock(String name, Block block, boolean shouldMakeItem) {
      Identifier id = Identifier.of(Abyssopelagic.MOD_ID, name);
      if (shouldMakeItem) {
         Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
      }
      return Registry.register(Registries.BLOCK, id, block);
   }

   private static AbstractBlock.Settings settings() {
      return AbstractBlock.Settings.create();
   }

   public static void initialize() {
   }
}
