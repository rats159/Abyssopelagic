package dev.rats159.abyssopelagic;

import dev.rats159.abyssopelagic.block.ModBlocks;
import dev.rats159.abyssopelagic.worldgen.biomes.features.ModFeatures;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Abyssopelagic implements ModInitializer {
    public static final String MOD_ID = "abyssopelagic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModFeatures.initialize();
    }



}