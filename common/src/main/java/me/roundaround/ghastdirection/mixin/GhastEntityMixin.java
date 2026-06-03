package me.roundaround.ghastdirection.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Ghast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ghast.class)
public abstract class GhastEntityMixin {
  @Inject(
      method = "faceMovementDirection",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;"),
      cancellable = true
  )
  private static void beforeUpdatingYaw(Mob ghast, CallbackInfo ci) {
    if (ghast.getDeltaMovement().lengthSqr() < 0.01) {
      ci.cancel();
    }
  }
}
