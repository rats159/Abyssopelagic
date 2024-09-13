package dev.rats159.abyssopelagic.worldgen.biomes;

import dev.rats159.abyssopelagic.worldgen.biomes.surface.ModSurfaceRules;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;
import static dev.rats159.abyssopelagic.Abyssopelagic.MOD_ID;

public class AbyssopelagicTerrablender implements TerraBlenderApi {
   @Override
   public void onTerraBlenderInitialized() {
      Regions.register(new ModRegion(Identifier.of(MOD_ID, "overworld"), 2));

      SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
   }
}
