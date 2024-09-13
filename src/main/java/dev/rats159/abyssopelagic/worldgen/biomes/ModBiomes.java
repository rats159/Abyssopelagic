package dev.rats159.abyssopelagic.worldgen.biomes;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.jetbrains.annotations.Nullable;

import static dev.rats159.abyssopelagic.Abyssopelagic.MOD_ID;

public class ModBiomes {
   public static final RegistryKey<Biome> ABYSSAL_CAVES = register("abyssal_caves");

   public static RegistryKey<Biome> register(String name) {
      return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(MOD_ID, name));
   }

   public static void bootstrap(Registerable<Biome> context) {
      context.register(ABYSSAL_CAVES, abyssalCaves(context));
   }

   public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
      DefaultBiomeFeatures.addLandCarvers(builder);
      DefaultBiomeFeatures.addMelons(builder);
   }

   public static Biome abyssalCaves(Registerable<Biome> context) {
      SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
      GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(
        context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
      );

      globalOverworldGeneration(biomeBuilder);

      DefaultBiomeFeatures.addAncientDebris(biomeBuilder);

      return createBiome(true, 0.8F, 0.4F, spawnBuilder, biomeBuilder, null);
   }

   private static Biome createBiome(
     boolean precipitation,
     float temperature,
     float downfall,
     int waterColor,
     int waterFogColor,
     @Nullable Integer grassColor,
     @Nullable Integer foliageColor,
     SpawnSettings.Builder spawnSettings,
     GenerationSettings.LookupBackedBuilder generationSettings,
     @Nullable MusicSound music
   ) {
      BiomeEffects.Builder builder = new BiomeEffects.Builder()
        .waterColor(waterColor)
        .waterFogColor(waterFogColor)
        .fogColor(12638463)
        .skyColor(OverworldBiomeCreator.getSkyColor(temperature))
        .moodSound(BiomeMoodSound.CAVE)
        .music(music);
      if (grassColor != null) {
         builder.grassColor(grassColor);
      }

      if (foliageColor != null) {
         builder.foliageColor(foliageColor);
      }

      return new Biome.Builder()
        .precipitation(precipitation)
        .temperature(temperature)
        .downfall(downfall)
        .effects(builder.build())
        .spawnSettings(spawnSettings.build())
        .generationSettings(generationSettings.build())
        .build();
   }

   private static Biome createBiome(
     boolean precipitation,
     float temperature,
     float downfall,
     SpawnSettings.Builder spawnSettings,
     GenerationSettings.LookupBackedBuilder generationSettings,
     @Nullable MusicSound music
   ) {
      return createBiome(precipitation, temperature, downfall, 4159204, 329011, null, null, spawnSettings, generationSettings, music);
   }

}
