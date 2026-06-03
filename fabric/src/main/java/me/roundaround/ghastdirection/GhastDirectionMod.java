package me.roundaround.ghastdirection;

import me.roundaround.allay.api.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint(Entrypoint.MAIN)
public final class GhastDirectionMod implements ModInitializer {
  @Override
  public void onInitialize() {
    // Mixin-only mod: nothing to register; Trove's Fabric core self-bootstraps.
  }
}
