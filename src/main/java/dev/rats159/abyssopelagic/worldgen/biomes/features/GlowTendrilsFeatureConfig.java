package dev.rats159.abyssopelagic.worldgen.biomes.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record GlowTendrilsFeatureConfig(int spreadWidth, int maxHeight) implements FeatureConfig {
    public static final Codec<GlowTendrilsFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codecs.POSITIVE_INT.fieldOf("spread_width").forGetter(GlowTendrilsFeatureConfig::spreadWidth),
                            Codecs.POSITIVE_INT.fieldOf("max_height").forGetter(GlowTendrilsFeatureConfig::maxHeight)
                    )
                    .apply(instance, GlowTendrilsFeatureConfig::new)
    );
}
