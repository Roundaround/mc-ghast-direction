package me.roundaround.ghastdirection.forge;

import me.roundaround.trove.forge.TroveForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("ghastdirection")
public final class GhastDirectionForgeMod {
  public GhastDirectionForgeMod(FMLJavaModLoadingContext context) {
    TroveForge.bootstrap(context);
    // Mixin-only mod: nothing else to register.
  }
}
