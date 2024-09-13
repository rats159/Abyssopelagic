package dev.rats159.abyssopelagic.worldgen.biomes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class ModRegion extends Region {
   public ModRegion(Identifier name, int weight) {
      super(name, RegionType.OVERWORLD, weight);
   }

   @Override
   public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
      VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
      new ParameterPointListBuilder().depth(Depth.UNDERGROUND)
                                     .build()
                                     .forEach(point -> builder.add(point, ModBiomes.ABYSSAL_CAVES));

      builder.build().forEach(mapper);
   }
}
