package dev.rats159.abyssopelagic;

import dev.rats159.abyssopelagic.datagen.ModWorldGenerator;
import dev.rats159.abyssopelagic.datagen.ModelGenerator;
import dev.rats159.abyssopelagic.worldgen.biomes.ModBiomes;
import dev.rats159.abyssopelagic.worldgen.biomes.features.ModConfiguredFeatures;
import dev.rats159.abyssopelagic.worldgen.biomes.features.ModFeatures;
import dev.rats159.abyssopelagic.worldgen.biomes.features.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class AbyssopelagicDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelGenerator::new);
		pack.addProvider(ModWorldGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
